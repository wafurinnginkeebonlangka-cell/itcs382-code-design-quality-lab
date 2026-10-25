package bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static final int STUDENT = 1;
    public static final int STAFF = 2;
    public static final Path RIDE_FILE = Path.of("rides.csv");
    public static final Map<String, String> BIKES = new LinkedHashMap<>();
    public static final Map<String, String[]> RENTALS = new LinkedHashMap<>();

    public static void main(String[] args) {
        addBikes("Library", "L");
        addBikes("Canteen", "C");
        addBikes("Dorm", "D");
        System.out.println("Commands: rent <member-id> <1=student|2=staff> <station>, "
                + "return <bike> <station> <minutes>, list, staff, quit");

        try (Scanner input = new Scanner(System.in)) {
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (!line.isEmpty() && !run(line)) {
                    break;
                }
            }
        } catch (IllegalStateException exception) {
            System.err.println("Input error: " + exception.getMessage());
        }
    }

    public static boolean run(String line) {
        String[] words = line.split("\\s+");
        try {
            switch (words[0].toLowerCase(Locale.ROOT)) {
                case "rent" -> rent(words);
                case "return" -> returnBike(words);
                case "list" -> printBikes(false);
                case "staff" -> printBikes(true);
                case "quit" -> { return false; }
                default -> System.out.println("Unknown command");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return true;
    }

    public static void rent(String[] words) {
        requireLength(words, 4, "rent <member-id> <1|2> <station>");
        int type = number(words[2], "member type");
        String station = station(words[3]);
        if (type != STUDENT && type != STAFF) {
            throw new IllegalArgumentException("member type must be 1 or 2");
        }
        String bike = BIKES.entrySet().stream()
                .filter(entry -> entry.getValue().equals(station))
                .map(Map.Entry::getKey).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("no bike available at " + station));
        BIKES.remove(bike);
        RENTALS.put(bike, new String[]{words[1], words[2], station});
        System.out.println("Rented " + bike);
    }

    public static void returnBike(String[] words) {
        requireLength(words, 4, "return <bike> <station> <minutes>");
        String bike = words[1].toUpperCase(Locale.ROOT);
        String[] rental = RENTALS.get(bike);
        if (rental == null) {
            throw new IllegalArgumentException("bike is not rented");
        }
        String destination = station(words[2]);
        int minutes = number(words[3], "minutes");
        if (minutes < 0) {
            throw new IllegalArgumentException("minutes cannot be negative");
        }
        int fee = minutes * (Integer.parseInt(rental[1]) == STUDENT ? 2 : 3);
        try {
            saveRide(bike, rental, destination, minutes, fee);
        } catch (IOException exception) {
            System.err.println("Could not save ride: " + exception.getMessage());
            return;
        }
        RENTALS.remove(bike);
        BIKES.put(bike, destination);
        System.out.printf("Receipt: bike %s, minutes %d, fee %d baht%n", bike, minutes, fee);
    }

    public static void printBikes(boolean includeRentals) {
        BIKES.forEach((bike, place) -> System.out.println(bike + " available at " + place));
        if (includeRentals) {
            RENTALS.forEach((bike, rental) ->
                    System.out.println(bike + " rented by " + rental[0] + " from " + rental[2]));
        }
    }

    public static void saveRide(String bike, String[] rental, String destination,
                                int minutes, int fee) throws IOException {
        boolean newFile = Files.notExists(RIDE_FILE) || Files.size(RIDE_FILE) == 0;
        try (BufferedWriter writer = Files.newBufferedWriter(RIDE_FILE,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (newFile) {
                writer.write("bike,memberId,memberType,from,to,minutes,fee");
                writer.newLine();
            }
            writer.write(String.join(",", bike, rental[0], rental[1], rental[2], destination,
                    Integer.toString(minutes), Integer.toString(fee)));
            writer.newLine();
        }
    }

    public static void addBikes(String station, String prefix) {
        BIKES.put(prefix + "1", station);
        BIKES.put(prefix + "2", station);
    }

    public static String station(String text) {
        return switch (text.toLowerCase(Locale.ROOT)) {
            case "library" -> "Library";
            case "canteen" -> "Canteen";
            case "dorm" -> "Dorm";
            default -> throw new IllegalArgumentException("station must be Library, Canteen or Dorm");
        };
    }

    public static int number(String text, String label) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(label + " must be a number", exception);
        }
    }

    public static void requireLength(String[] words, int length, String usage) {
        if (words.length != length) {
            throw new IllegalArgumentException("usage: " + usage);
        }
    }
}
