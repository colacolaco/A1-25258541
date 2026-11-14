import java.util.ArrayList;
import java.util.List;

/**
 * The names of the days of the week on which a health professional may work.
 *
 * <p>The days are held as text constants instead of an enumeration. The course
 * restricts this assessment to the material taught in Modules 1 to 3, and
 * enumerations are not part of those modules. The constants plus the methods
 * in this class give the same protection: the names are written once, they are
 * checked before they are stored, and they are always printed in calendar
 * order.</p>
 *
 * <p>The class cannot be instantiated or extended, because it only groups
 * constants and helper methods.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public final class Day {

    /** Monday. */
    public static final String MONDAY = "Monday";

    /** Tuesday. */
    public static final String TUESDAY = "Tuesday";

    /** Wednesday. */
    public static final String WEDNESDAY = "Wednesday";

    /** Thursday. */
    public static final String THURSDAY = "Thursday";

    /** Friday. */
    public static final String FRIDAY = "Friday";

    /** Saturday. */
    public static final String SATURDAY = "Saturday";

    /** Sunday. */
    public static final String SUNDAY = "Sunday";

    /** Every day of the week, in calendar order. */
    private static final String[] ALL_DAYS = {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    };

    /** The short form of each day, in the same order as ALL_DAYS. */
    private static final String[] SHORT_NAMES = {
        "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
    };

    /**
     * No objects of this class are needed, so the constructor is private.
     */
    private Day() {
    }

    /**
     * Returns a copy of every day of the week, in calendar order.
     *
     * @return a new array holding Monday to Sunday
     */
    public static String[] allDays() {
        String[] copy = new String[ALL_DAYS.length];
        for (int index = 0; index < ALL_DAYS.length; index++) {
            copy[index] = ALL_DAYS[index];
        }
        return copy;
    }

    /**
     * Returns the five working days of the week, Monday to Friday. This is the
     * default roster used when a caller does not supply the days a
     * professional works.
     *
     * @return a new list holding Monday to Friday
     */
    public static List<String> weekdays() {
        List<String> days = new ArrayList<>();
        days.add(MONDAY);
        days.add(TUESDAY);
        days.add(WEDNESDAY);
        days.add(THURSDAY);
        days.add(FRIDAY);
        return days;
    }

    /**
     * Returns true when the text is one of the seven day names.
     *
     * @param day the text to check, which may be null
     * @return true if the text names a day of the week
     */
    public static boolean isValid(String day) {
        return indexOf(day) >= 0;
    }

    /**
     * Returns the position of a day in the week, Monday being zero, or -1 when
     * the text is not a day name. The position is what keeps a list of days in
     * calendar order however it is built.
     *
     * @param day the text to look for, which may be null
     * @return the position from 0 to 6, or -1 when the text is not a day
     */
    public static int indexOf(String day) {
        if (day == null) {
            return -1;
        }
        for (int index = 0; index < ALL_DAYS.length; index++) {
            if (ALL_DAYS[index].equals(day)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Returns the short name of a day, for example "Mon" for "Monday".
     *
     * @param day the day to shorten
     * @return the short name, or "?" when the text is not a day
     */
    public static String shortNameOf(String day) {
        int index = indexOf(day);
        if (index < 0) {
            return "?";
        }
        return SHORT_NAMES[index];
    }

    /**
     * Formats a list of days for display, always in calendar order, so the
     * result does not depend on the order the list was built in. A list of
     * Monday, Wednesday and Friday is formatted as "Mon, Wed, Fri".
     *
     * @param days the days to format, which may be null or empty
     * @return a readable list of short day names, or "not available"
     */
    public static String format(List<String> days) {
        if (days == null || days.isEmpty()) {
            return "not available";
        }
        String text = "";
        for (int index = 0; index < ALL_DAYS.length; index++) {
            if (!days.contains(ALL_DAYS[index])) {
                continue;
            }
            if (text.length() > 0) {
                text = text + ", ";
            }
            text = text + SHORT_NAMES[index];
        }
        return text;
    }
}
