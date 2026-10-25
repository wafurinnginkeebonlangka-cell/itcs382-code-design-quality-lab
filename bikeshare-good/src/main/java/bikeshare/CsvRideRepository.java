package bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class CsvRideRepository implements RideRepository {
    private final Path path;

    public CsvRideRepository(Path path) {
        this.path = path;
    }

    @Override
    public void save(Ride ride) throws IOException {
        boolean needsHeader = Files.notExists(path) || Files.size(path) == 0;
        try (BufferedWriter writer = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (needsHeader) {
                writer.write("bike,memberId,memberType,from,to,minutes,fee");
                writer.newLine();
            }
            Rental rental = ride.rental();
            writer.write(String.join(",", rental.bike(), csv(rental.memberId()),
                    rental.memberType().name().toLowerCase(), rental.origin().toString(),
                    ride.destination().toString(), Integer.toString(ride.minutes()),
                    Integer.toString(ride.fee())));
            writer.newLine();
        }
    }

    private String csv(String value) {
        return '"' + value.replace("\"", "\"\"") + '"';
    }
}
