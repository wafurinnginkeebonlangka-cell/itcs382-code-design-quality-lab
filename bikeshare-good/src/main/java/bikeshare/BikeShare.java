package bikeshare;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BikeShare {
    private final Map<String, Station> availableBikes = new LinkedHashMap<>();
    private final Map<String, Rental> rentals = new LinkedHashMap<>();
    private final RideRepository rideRepository;

    public BikeShare(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
        addBikes(Station.LIBRARY, "L");
        addBikes(Station.CANTEEN, "C");
        addBikes(Station.DORM, "D");
    }

    public String rent(String memberId, MemberType memberType, Station station) {
        String bike = availableBikes.entrySet().stream()
                .filter(entry -> entry.getValue() == station)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("no bike available at " + station));
        availableBikes.remove(bike);
        rentals.put(bike, new Rental(bike, memberId, memberType, station));
        return bike;
    }

    public Receipt returnBike(String bike, Station destination, int minutes) throws IOException {
        if (minutes < 0) {
            throw new IllegalArgumentException("minutes cannot be negative");
        }
        Rental rental = rentals.get(bike);
        if (rental == null) {
            throw new IllegalArgumentException("bike is not rented");
        }
        int fee = rental.memberType().feeFor(minutes);
        rideRepository.save(new Ride(rental, destination, minutes, fee));
        rentals.remove(bike);
        availableBikes.put(bike, destination);
        return new Receipt(bike, minutes, fee);
    }

    private void addBikes(Station station, String prefix) {
        availableBikes.put(prefix + "1", station);
        availableBikes.put(prefix + "2", station);
    }
}
