package bikeshare;

public record Rental(String bike, String memberId, MemberType memberType, Station origin) {
}
