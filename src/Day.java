/**
 * The days of the week on which a health professional may be available.
 *
 * <p>An enum is used here because the seven days form a fixed, named set of
 * values. Using an enum instead of plain text means the compiler prevents
 * spelling mistakes such as "Wendsday", and a switch or a loop can be written
 * safely over the values.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public enum Day {

    /** Monday. */
    MONDAY("Monday", "Mon"),
    /** Tuesday. */
    TUESDAY("Tuesday", "Tue"),
    /** Wednesday. */
    WEDNESDAY("Wednesday", "Wed"),
    /** Thursday. */
    THURSDAY("Thursday", "Thu"),
    /** Friday. */
    FRIDAY("Friday", "Fri"),
    /** Saturday. */
    SATURDAY("Saturday", "Sat"),
    /** Sunday. */
    SUNDAY("Sunday", "Sun");

    /** The full name of the day, shown to a user. */
    private final String displayName;

    /** The short name of the day, used in compact output such as "Mon, Wed". */
    private final String shortName;

    /**
     * Creates a day constant with the two text forms it is displayed in.
     *
     * @param displayName the full name of the day, for example "Monday"
     * @param shortName   the short name of the day, for example "Mon"
     */
    Day(String displayName, String shortName) {
        this.displayName = displayName;
        this.shortName = shortName;
    }

    /**
     * Returns the full name of this day.
     *
     * @return the full name of the day, for example "Monday"
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the short name of this day.
     *
     * @return the short name of the day, for example "Mon"
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Returns the five working days of the week, Monday to Friday.
     *
     * <p>This is used as the sensible default when a caller does not supply
     * the days a professional works. The returned set is a fresh copy, so a
     * caller may change it without affecting anything else.</p>
     *
     * @return a new set containing Monday to Friday
     */
    public static java.util.EnumSet<Day> weekdays() {
        return java.util.EnumSet.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
    }

    /**
     * Formats a set of days for display, always in calendar order.
     *
     * <p>The order comes from {@link #values()}, so the result does not depend
     * on how the set was created. For example a set of Monday, Wednesday and
     * Friday is formatted as "Mon, Wed, Fri".</p>
     *
     * @param days the days to format; may be null or empty
     * @return a readable list of short day names, or "not available"
     */
    public static String format(java.util.Set<Day> days) {
        if (days == null || days.isEmpty()) {
            return "not available";
        }
        StringBuilder text = new StringBuilder();
        for (Day day : values()) {
            if (!days.contains(day)) {
                continue;
            }
            if (text.length() > 0) {
                text.append(", ");
            }
            text.append(day.shortName);
        }
        return text.toString();
    }

    /**
     * Returns the full name of the day, so that printing a day directly gives
     * a readable result instead of the constant name in capitals.
     *
     * @return the full name of the day
     */
    @Override
    public String toString() {
        return displayName;
    }
}
