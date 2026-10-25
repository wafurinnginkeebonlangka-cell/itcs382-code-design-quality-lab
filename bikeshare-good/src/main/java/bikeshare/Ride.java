package bikeshare;

public record Ride(Rental rental, Station destination, int minutes, int fee) {
}
