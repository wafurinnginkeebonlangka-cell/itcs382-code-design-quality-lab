package bikeshare;

import java.nio.file.Path;
import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        RideRepository repository = new CsvRideRepository(Path.of("rides.csv"));
        BikeShare bikeShare = new BikeShare(repository);
        try (Scanner input = new Scanner(System.in)) {
            new CommandLineMenu(bikeShare, input, System.out).run();
        }
    }
}
