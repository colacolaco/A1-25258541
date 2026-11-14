import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The fixed daily schedule of bookable time slots used by the clinic.
 *
 * <p>Keeping the schedule in its own class means the appointment system does
 * not hard-code "08:00 to 16:00 in 30 minute steps" inside the booking code.
 * If the clinic later changes its hours or its appointment length, only the
 * numbers passed to this class change and the rest of the system keeps
 * working unchanged.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class ClinicSchedule {

    /** The default opening hour. */
    public static final int DEFAULT_START_HOUR = 8;

    /** The default closing hour. */
    public static final int DEFAULT_END_HOUR = 16;

    /** The default length of one appointment, in minutes. */
    public static final int DEFAULT_SLOT_MINUTES = 30;

    /** Every slot of the day, held in time order. */
    private final List<TimeSlot> slots;

    /** The length of one appointment, in minutes. */
    private final int slotLengthInMinutes;

    /**
     * Creates the standard clinic schedule: 08:00 to 16:00 in 30 minute slots.
     */
    public ClinicSchedule() {
        this(DEFAULT_START_HOUR, DEFAULT_END_HOUR, DEFAULT_SLOT_MINUTES);
    }

    /**
     * Creates a schedule that opens on the hour and closes on the hour.
     *
     * @param startHour           the hour the clinic opens, 0 to 23
     * @param endHour             the hour the clinic closes, 1 to 24
     * @param slotLengthInMinutes the length of one appointment in minutes
     */
    public ClinicSchedule(int startHour, int endHour, int slotLengthInMinutes) {
        this(startHour, 0, endHour, 0, slotLengthInMinutes);
    }

    /**
     * Creates a schedule from an opening time to a closing time, in steps of
     * a fixed number of minutes.
     *
     * <p>The closing time is not itself bookable: an appointment that starts
     * at 16:00 would be seen after the clinic has closed, so the last slot of
     * the standard schedule is 15:30.</p>
     *
     * @param startHour           the hour the clinic opens
     * @param startMinute         the minute the clinic opens
     * @param endHour             the hour the clinic closes
     * @param endMinute           the minute the clinic closes
     * @param slotLengthInMinutes the length of one appointment in minutes
     * @throws IllegalArgumentException if the values cannot make a schedule
     */
    public ClinicSchedule(int startHour, int startMinute, int endHour, int endMinute, int slotLengthInMinutes) {
        if (slotLengthInMinutes <= 0) {
            throw new IllegalArgumentException("An appointment must last at least one minute.");
        }
        int openingMinute = startHour * 60 + startMinute;
        int closingMinute = endHour * 60 + endMinute;
        if (openingMinute < 0 || openingMinute >= 24 * 60) {
            throw new IllegalArgumentException("The opening time is outside one day.");
        }
        if (closingMinute <= openingMinute || closingMinute > 24 * 60) {
            throw new IllegalArgumentException("The closing time must be after the opening time, within one day.");
        }

        List<TimeSlot> createdSlots = new ArrayList<>();
        // A slot is only bookable when the whole appointment fits before closing time.
        for (int minute = openingMinute; minute + slotLengthInMinutes <= closingMinute; minute += slotLengthInMinutes) {
            createdSlots.add(new TimeSlot(minute / 60, minute % 60));
        }
        if (createdSlots.isEmpty()) {
            throw new IllegalArgumentException("The opening hours are too short for a single appointment.");
        }
        this.slots = createdSlots;
        this.slotLengthInMinutes = slotLengthInMinutes;
    }

    /**
     * Returns every bookable slot of the day, in time order. The returned list
     * is read-only, so callers cannot damage the clinic's schedule.
     *
     * @return an unmodifiable list of the day's slots
     */
    public List<TimeSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    /**
     * Returns the length of one appointment in minutes.
     *
     * @return the appointment length
     */
    public int getSlotLengthInMinutes() {
        return slotLengthInMinutes;
    }

    /**
     * Returns the first slot of the day.
     *
     * @return the opening slot
     */
    public TimeSlot getFirstSlot() {
        return slots.get(0);
    }

    /**
     * Returns the last bookable slot of the day.
     *
     * @return the last slot
     */
    public TimeSlot getLastSlot() {
        return slots.get(slots.size() - 1);
    }

    /**
     * Returns true when the given time is one of the clinic's bookable slots.
     * Being inside opening hours is not enough: the time must line up with a
     * slot, so 09:10 is rejected while 09:30 is accepted.
     *
     * @param time the time to check
     * @return true if the time is a slot of this schedule
     */
    public boolean isBookable(TimeSlot time) {
        if (time == null) {
            return false;
        }
        return slots.contains(time);
    }

    /**
     * Describes the schedule in one line, for example
     * "08:00-16:00, 30 minute appointments, 16 slots per day".
     *
     * @return a one line description of the schedule
     */
    @Override
    public String toString() {
        int closingMinute = getLastSlot().toMinutesSinceMidnight() + slotLengthInMinutes;
        return String.format("%s-%02d:%02d, %d minute appointments, %d slots per day",
                getFirstSlot(),
                closingMinute / 60,
                closingMinute % 60,
                slotLengthInMinutes,
                slots.size());
    }
}
