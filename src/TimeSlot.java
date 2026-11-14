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
    private static final int MINUTES_PER_HOUR = 60;

    /** The hour of the day, from 0 to 23. */
    private final int hour;

    /** The minute of the hour, from 0 to 59. */
    private final int minute;

    /**
     * Creates a time slot at the given hour and minute.
     *
     * @param hour   the hour of the day, 0 to 23
     * @param minute the minute of the hour, 0 to 59
     * @throws IllegalArgumentException if the hour or minute is out of range
     */
    public TimeSlot(int hour, int minute) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("The hour must be between 0 and 23, but was " + hour + ".");
        }
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("The minute must be between 0 and 59, but was " + minute + ".");
        }
        this.hour = hour;
        this.minute = minute;
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
     * @param other the slot to compare against
     * @return a negative number if this slot is earlier, zero if the two slots
     *         are the same time, or a positive number if this slot is later
     * @throws NullPointerException if other is null
     */
    @Override
    public int compareTo(TimeSlot other) {
        if (other == null) {
            throw new NullPointerException("A time slot cannot be compared with null.");
        }
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
        return String.format("%02d:%02d", hour, minute);
    }

}
