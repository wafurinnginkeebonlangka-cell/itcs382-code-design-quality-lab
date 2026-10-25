package bikeshare;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public final class CommandLineMenu {
    private final BikeShare bikeShare;
    private final Scanner input;
    private final PrintStream output;

    public CommandLineMenu(BikeShare bikeShare, Scanner input, PrintStream output) {
        this.bikeShare = bikeShare;
        this.input = input;
        this.output = output;
    }

    public void run() {
        output.println("Commands: rent <member-id> <student|staff> <station>, "
                + "return <bike> <station> <minutes>, quit");
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (!line.isEmpty() && !execute(line)) {
                return;
            }
        }
    }

    private boolean execute(String line) {
        String[] words = line.split("\\s+");
        try {
            return switch (words[0].toLowerCase(Locale.ROOT)) {
                case "rent" -> rent(words);
                case "return" -> returnBike(words);
                case "quit" -> false;
                default -> throw new IllegalArgumentException("unknown command");
            };
        } catch (IllegalArgumentException | IOException exception) {
            output.println("Error: " + exception.getMessage());
            return true;
        }
    }

    private boolean rent(String[] words) {
        requireLength(words, 4, "rent <member-id> <student|staff> <station>");
        String bike = bikeShare.rent(words[1], MemberType.from(words[2]), Station.from(words[3]));
        output.println("Rented " + bike);
        return true;
    }

    private boolean returnBike(String[] words) throws IOException {
        requireLength(words, 4, "return <bike> <station> <minutes>");
        Receipt receipt = bikeShare.returnBike(words[1].toUpperCase(Locale.ROOT),
                Station.from(words[2]), number(words[3]));
        output.printf("Receipt: bike %s, minutes %d, fee %d baht%n",
                receipt.bike(), receipt.minutes(), receipt.fee());
        return true;
    }

    private int number(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("minutes must be a number", exception);
        }
    }

    private void requireLength(String[] words, int length, String usage) {
        if (words.length != length) {
            throw new IllegalArgumentException("usage: " + usage);
        }
    }
}
