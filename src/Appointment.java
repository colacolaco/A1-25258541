/**
 * A single appointment: one patient, one health professional and one time.
 *
 * <p>An appointment can refer to any kind of health professional, because the
 * field is declared as the general type {@link HealthProfessional}. The
 * clinic's rules about double booking are not checked here; that is the job
 * of the {@link Clinic} which owns the whole collection.</p>
 *
 * <p>The class implements {@link Comparable} so a list of appointments can be
 * sorted from earliest to latest with Collections.sort(...).</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class Appointment implements Comparable<Appointment> {

    /** The width of the health professional column when details are printed. */
    private static final int COLUMN_WIDTH = 52;

    /** The patient who booked the appointment. */
    private final Patient patient;

    /** The health professional the patient will see. */
    private final HealthProfessional professional;

    /** The time the appointment starts. */
    private final TimeSlot timeSlot;

    /**
     * Creates an appointment. All details are required, so an appointment can
     * never be created while it is missing a patient, a professional or a time.
     *
     * @param patient      the patient who booked the appointment, must not be null
     * @param professional the health professional the patient will see, must not be null
     * @param timeSlot     the time the appointment starts, must not be null
     */
    public Appointment(Patient patient, HealthProfessional professional, TimeSlot timeSlot) {
        this.patient = patient;
        this.professional = professional;
        this.timeSlot = timeSlot;
    }

    /**
     * Returns the patient of this appointment.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the health professional of this appointment.
     *
     * @return the health professional
     */
    public HealthProfessional getProfessional() {
        return professional;
    }

    /**
     * Returns the time of this appointment.
     *
     * @return the time slot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Orders two appointments by time.
     *
     * <p>When two appointments are at the same time but for different health
     * professionals, the brief allows either order. The professional ID is
     * used as a tie break so that a printed list is always in the same order.</p>
     *
     * @param other the appointment to compare against, which must not be null
     * @return a negative number if this appointment is earlier, zero if the
     *         two appointments have the same time and professional, or a
     *         positive number if this appointment is later
     */
    @Override
    public int compareTo(Appointment other) {
        int byTime = timeSlot.compareTo(other.timeSlot);
        if (byTime != 0) {
            return byTime;
        }
        return professional.getId().compareTo(other.professional.getId());
    }

    /**
     * Returns the details of the appointment on one line, so that a list of
     * appointments lines up when it is printed.
     *
     * @return a one line summary of the appointment
     */
    @Override
    public String toString() {
        return timeSlot + "  " + padRight(professional.getSummary(), COLUMN_WIDTH) + "  " + patient;
    }

    /**
     * Pads text with spaces on the right until it reaches the given width, so
     * the printed columns stay aligned.
     *
     * @param text  the text to pad
     * @param width the minimum width of the result
     * @return the text followed by enough spaces to reach the width
     */
    private static String padRight(String text, int width) {
        String padded = text;
        while (padded.length() < width) {
            padded = padded + " ";
        }
        return padded;
    }
}
