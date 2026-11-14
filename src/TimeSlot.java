/**
 * A time of day at which an appointment may start, for example 09:30.
 *
 * <p>The time is stored as two whole numbers (hour and minute) rather than as
 * a string. Storing text such as "9:30" would sort incorrectly, because text
 * is compared character by character; two whole numbers can be compared
 * correctly. The class implements {@link Comparable} so that a collection of
 * appointments can be ordered by time with a single call to
 * Collections.sort(...).</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class TimeSlot implements Comparable<TimeSlot> {

    /** Number of minutes in one hour. */
    public static final int MINUTES_PER_HOUR = 60;

    /** Number of hours in one day. */
    public static final int HOURS_PER_DAY = 24;

    /** The hour of the day, from 0 to 23. */
    private final int hour;

    /** The minute of the hour, from 0 to 59. */
    private final int minute;

    /** True when the hour and minute supplied to the constructor were valid. */
    private final boolean valid;

    /**
     * Creates a time slot at the given hour and minute.
     *
     * <p>A value outside the range of a clock is pulled back into the range
     * instead of causing an error, because exceptions are not part of the
     * material allowed in this assessment. Call {@link #validate(int, int)}
     * first when an invalid time has to be reported to a user.</p>
     *
     * @param hour   the hour of the day; values outside 0 to 23 are corrected
     * @param minute the minute of the hour; values outside 0 to 59 are corrected
     */
    public TimeSlot(int hour, int minute) {
        this.valid = validate(hour, minute).isEmpty();
        this.hour = normalise(hour, HOURS_PER_DAY - 1);
        this.minute = normalise(minute, MINUTES_PER_HOUR - 1);
    }

    /**
     * Convenience factory method so that a slot can be created in one short
     * expression, for example {@code TimeSlot.of(9, 30)}.
     *
     * @param hour   the hour of the day, 0 to 23
     * @param minute the minute of the hour, 0 to 59
     * @return a new time slot
     */
    public static TimeSlot of(int hour, int minute) {
        return new TimeSlot(hour, minute);
    }

    /**
     * Checks an hour and a minute before a time slot is created.
     *
     * @param hour   the hour to check
     * @param minute the minute to check
     * @return an empty text when the values are valid, or a warning explaining why they are not
     */
    public static String validate(int hour, int minute) {
        if (hour < 0 || hour > HOURS_PER_DAY - 1) {
            return "WARNING: the hour must be between 0 and " + (HOURS_PER_DAY - 1) + ", but was " + hour + ".";
        }
        if (minute < 0 || minute > MINUTES_PER_HOUR - 1) {
            return "WARNING: the minute must be between 0 and " + (MINUTES_PER_HOUR - 1) + ", but was " + minute + ".";
        }
        return "";
    }

    /**
     * Returns true when the hour and minute given to the constructor were
     * inside the range of a clock.
     *
     * <p>A slot built from, for example, hour 9 and minute 75 is corrected to
     * 10:15 so that the object is still usable, but the correction is
     * remembered here. The clinic checks this flag, so a time that had to be
     * corrected is reported to the user instead of being booked silently.</p>
     *
     * @return true if no correction was needed
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Returns the hour of this slot.
     *
     * @return the hour, 0 to 23
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns the minute of this slot.
     *
     * @return the minute, 0 to 59
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Converts this slot to a single value: the number of minutes since
     * midnight. Comparing these values is the same as comparing the times.
     *
     * @return the number of minutes since midnight
     */
    public int toMinutesSinceMidnight() {
        return hour * MINUTES_PER_HOUR + minute;
    }

    /**
     * Orders two time slots from earliest to latest.
     *
     * @param other the slot to compare against, which must not be null
     * @return a negative number if this slot is earlier, zero if the two slots
     *         are the same time, or a positive number if this slot is later
     */
    @Override
    public int compareTo(TimeSlot other) {
        int thisMinutes = toMinutesSinceMidnight();
        int otherMinutes = other.toMinutesSinceMidnight();
        if (thisMinutes < otherMinutes) {
            return -1;
        }
        if (thisMinutes > otherMinutes) {
            return 1;
        }
        return 0;
    }

    /**
     * Returns true when the two slots are at the same time of day.
     *
     * @param other the object to compare with
     * @return true if other is a time slot with the same hour and minute
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TimeSlot)) {
            return false;
        }
        TimeSlot that = (TimeSlot) other;
        return hour == that.hour && minute == that.minute;
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return a hash code for this slot
     */
    @Override
    public int hashCode() {
        return 31 * hour + minute;
    }

    /**
     * Formats the time as a 24-hour clock value with two digits, such as
     * "08:00" or "14:30", so that every time is the same width when printed.
     *
     * @return the time in "HH:mm" form
     */
    @Override
    public String toString() {
        return twoDigits(hour) + ":" + twoDigits(minute);
    }

    /**
     * Returns a whole number of the clock padded with a leading zero when it
     * is smaller than ten, for example "08".
     *
     * @param value the number to pad
     * @return the number as two characters
     */
    public static String twoDigits(int value) {
        if (value < 10) {
            return "0" + value;
        }
        return "" + value;
    }

    /**
     * Pulls a clock value back into its allowed range.
     *
     * @param value the value supplied by the caller
     * @param maximum the largest allowed value
     * @return the value limited to the range 0 to maximum
     */
    private static int normalise(int value, int maximum) {
        if (value < 0) {
            return 0;
        }
        if (value > maximum) {
            return maximum;
        }
        return value;
    }
}
