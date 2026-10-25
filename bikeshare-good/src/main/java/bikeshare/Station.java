package bikeshare;

import java.util.Locale;

public enum Station {
    LIBRARY, CANTEEN, DORM;

    public static Station from(String text) {
        try {
            return valueOf(text.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("station must be Library, Canteen or Dorm", exception);
        }
    }

    @Override
    public String toString() {
        String lower = name().toLowerCase(Locale.ROOT);
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
