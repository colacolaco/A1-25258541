import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The appointment book of the health service: it owns every appointment and
 * enforces the rules for booking and cancelling them.
 *
 * <p>The appointments are held in an {@link ArrayList}. An array list was
 * chosen because the clinic needs an ordered collection that grows as bookings
 * are made, that can be searched from start to end for filtering, and that can
 * be sorted by time. A fixed size array would have to be resized by hand, and
 * a two dimensional array would make searching by patient much harder.</p>
 *
 * <p>Every operation returns a message. When an operation succeeds the message
 * starts with "BOOKED", "CANCELLED" or "VIEW"; when it cannot be performed the
 * message starts with "WARNING:" and explains why. No operation throws an
 * exception: the brief asks every failure to be reported as a message.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class Clinic {

    /** The prefix used by every message that reports a failed operation. */
    public static final String WARNING_PREFIX = "WARNING: ";

    /** The name used when no clinic name is supplied. */
    private static final String UNKNOWN_NAME = "Unnamed clinic";

    /** The name of the clinic. */
    private final String name;

    /** The day the clinic is operating on. Bookings are only made for this day. */
    private final String operatingDay;

    /** The fixed set of time slots the clinic can book. */
    private final ClinicSchedule schedule;

    /** Every appointment in the clinic, in the order they were added. */
    private final List<Appointment> appointments;

    /**
     * Creates a clinic that uses the standard daily schedule.
     *
     * @param name         the name of the clinic
     * @param operatingDay the day the clinic is operating on
     */
    public Clinic(String name, String operatingDay) {
        this(name, operatingDay, new ClinicSchedule());
    }

    /**
     * Creates a clinic with its own daily schedule.
     *
     * @param name         the name of the clinic
     * @param operatingDay the day the clinic is operating on
     * @param schedule     the fixed set of bookable time slots
     */
    public Clinic(String name, String operatingDay, ClinicSchedule schedule) {
        this.name = name == null || name.trim().isEmpty() ? UNKNOWN_NAME : name.trim();
        this.operatingDay = Day.isValid(operatingDay) ? operatingDay : Day.MONDAY;
        this.schedule = schedule == null ? new ClinicSchedule() : schedule;
        this.appointments = new ArrayList<>();
    }

    /**
     * Returns the name of the clinic.
     *
     * @return the name of the clinic
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the day the clinic is operating on.
     *
     * @return the operating day, for example "Monday"
     */
    public String getOperatingDay() {
        return operatingDay;
    }

    /**
     * Returns the daily schedule of the clinic.
     *
     * @return the daily schedule
     */
    public ClinicSchedule getSchedule() {
        return schedule;
    }

    /**
     * Adds an appointment to the clinic, if every rule allows it.
     *
     * <p>The booking is rejected when the appointment is missing, when one of
     * its details was never supplied, when a detail had to be corrected
     * because it was invalid, when the time is not one of the clinic's slots,
     * when the professional does not work on the day, when the professional is
     * already booked at that time, or when the patient is already booked at
     * that time.</p>
     *
     * @param appointment the appointment to add
     * @return a message describing the booking, or a warning explaining why
     *         the booking could not be made
     */
    public String addAppointment(Appointment appointment) {
        if (appointment == null) {
            return warning("No appointment was supplied.");
        }
        // 0. Every detail must be present, and none may have been corrected.
        if (!appointment.isComplete()) {
            return warning("An appointment is only complete with a patient, a health professional "
                    + "and a time, so it was not booked.");
        }
        TimeSlot time = appointment.getTimeSlot();
        HealthProfessional professional = appointment.getProfessional();
        Patient patient = appointment.getPatient();
        if (!professional.hasValidDetails()) {
            return warning("The health professional has invalid details (ID " + professional.getId()
                    + ", name " + professional.getName() + "), so the appointment was not booked.");
        }
        if (!patient.hasValidDetails()) {
            return warning("The patient has invalid details (name " + patient.getName() + ", mobile "
                    + patient.getMobileNumber() + "), so the appointment was not booked.");
        }
        if (!time.isValid()) {
            return warning("The requested time was not a valid time of day, so the appointment was not booked.");
        }

        // 1. The time must be one of the clinic's fixed slots.
        if (!schedule.isBookable(time)) {
            return warning(time + " is not a bookable time slot. Bookable schedule: " + schedule + ".");
        }
        // 2. The professional must work on the day the clinic is operating.
        if (!professional.worksOn(operatingDay)) {
            return warning(professional.getSummary() + " does not work on " + operatingDay + "s.");
        }
        // 3. The same professional must never be booked into the same slot twice.
        Appointment professionalClash = findAppointmentFor(professional, time);
        if (professionalClash != null) {
            return warning(professional.getSummary() + " already has an appointment at " + time + ".");
        }
        // 4. A patient cannot be in two places at the same time either.
        Appointment patientClash = findAppointmentFor(patient, time);
        if (patientClash != null) {
            return warning(patient.getName() + " already has an appointment at " + time
                    + " with " + patientClash.getProfessional().getSummary() + ".");
        }

        appointments.add(appointment);
        return "BOOKED      " + time + "  " + professional.getSummary()
                + " for " + patient.getName();
    }

    /**
     * Returns every appointment in the clinic, in the order they were added.
     * The returned list is a copy, so callers cannot change the appointment
     * book by accident.
     *
     * @return a copy of the clinic's appointments
     */
    public List<Appointment> viewAllAppointments() {
        return new ArrayList<>(appointments);
    }

    /**
     * Returns every appointment for one health professional.
     *
     * @param professionalId the ID of the health professional, digits only
     * @return the matching appointments, in the order they were added
     */
    public List<Appointment> viewAppointmentsForProfessional(String professionalId) {
        List<Appointment> matching = new ArrayList<>();
        if (professionalId == null) {
            return matching;
        }
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getId().equals(professionalId.trim())) {
                matching.add(appointment);
            }
        }
        return matching;
    }

    /**
     * Returns every appointment for one patient, found by mobile number.
     *
     * @param mobileNumber the patient's mobile number, ten digits
     * @return the matching appointments, in the order they were added
     */
    public List<Appointment> viewAppointmentsForPatient(String mobileNumber) {
        List<Appointment> matching = new ArrayList<>();
        if (mobileNumber == null) {
            return matching;
        }
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getMobileNumber().equals(mobileNumber.trim())) {
                matching.add(appointment);
            }
        }
        return matching;
    }

    /**
     * Returns a new list of the given appointments, sorted from earliest to
     * latest. The given list is not changed.
     *
     * <p>This works for the whole appointment book or for a filtered subset,
     * because the sorting is done on whatever list it is given.</p>
     *
     * @param appointmentsToSort the appointments to sort, which may be null
     * @return a sorted copy of the list, or an empty list when nothing was given
     */
    public List<Appointment> sortByAppointmentTime(List<Appointment> appointmentsToSort) {
        if (appointmentsToSort == null) {
            return new ArrayList<>();
        }
        List<Appointment> sorted = new ArrayList<>(appointmentsToSort);
        // Appointment implements Comparable, so no comparator has to be written.
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Returns the whole appointment book sorted from earliest to latest.
     *
     * @return a sorted copy of every appointment
     */
    public List<Appointment> viewAllAppointmentsSortedByTime() {
        return sortByAppointmentTime(appointments);
    }

    /**
     * Cancels the appointment for one health professional at one time.
     *
     * @param professionalId the ID of the health professional, digits only
     * @param time           the time of the appointment to cancel
     * @return a message describing the cancellation, or a warning when no such
     *         appointment exists
     */
    public String cancelAppointment(String professionalId, TimeSlot time) {
        if (professionalId == null || time == null) {
            return warning("A cancellation needs both a health professional ID and a time.");
        }
        Appointment found = null;
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getId().equals(professionalId.trim())
                    && appointment.getTimeSlot().equals(time)) {
                found = appointment;
                break;
            }
        }
        if (found == null) {
            return warning("No appointment was found for health professional " + professionalId.trim()
                    + " at " + time + ".");
        }
        appointments.remove(found);
        return "CANCELLED   " + found.getTimeSlot() + "  " + found.getProfessional().getSummary()
                + " for " + found.getPatient().getName();
    }

    /**
     * Cancels a patient's earliest appointment, found by mobile number.
     *
     * <p>If the patient has more than one appointment, only the earliest is
     * cancelled, because cancelling everything the patient has booked on their
     * behalf would be a surprising outcome. The message reports how many
     * appointments the patient still has.</p>
     *
     * @param mobileNumber the patient's mobile number, ten digits
     * @return a message describing the cancellation, or a warning when the
     *         patient has no appointments
     */
    public String cancelAppointment(String mobileNumber) {
        if (mobileNumber == null) {
            return warning("A cancellation needs a mobile number.");
        }
        List<Appointment> patientAppointments = viewAppointmentsForPatient(mobileNumber);
        if (patientAppointments.isEmpty()) {
            return warning("No appointment was found for mobile number " + mobileNumber.trim() + ".");
        }
        Appointment earliest = patientAppointments.get(0);
        for (Appointment appointment : patientAppointments) {
            if (appointment.compareTo(earliest) < 0) {
                earliest = appointment;
            }
        }
        appointments.remove(earliest);
        String message = "CANCELLED   " + earliest.getTimeSlot() + "  " + earliest.getProfessional().getSummary()
                + " for " + earliest.getPatient().getName();
        int remaining = patientAppointments.size() - 1;
        if (remaining > 0) {
            message = message + " (" + remaining + " other appointment(s) for this patient remain.)";
        }
        return message;
    }

    /**
     * Returns how many appointments the clinic currently holds.
     *
     * @return the number of appointments
     */
    public int getNumberOfAppointments() {
        return appointments.size();
    }

    /**
     * Finds the appointment for a health professional at a given time, using
     * the professional's own equality rule (two professionals are the same
     * when their IDs are the same).
     *
     * @param professional the health professional
     * @param time         the time
     * @return the matching appointment, or null when there is none
     */
    private Appointment findAppointmentFor(HealthProfessional professional, TimeSlot time) {
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().equals(professional) && appointment.getTimeSlot().equals(time)) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Finds the appointment for a patient at a given time.
     *
     * @param patient the patient
     * @param time    the time
     * @return the matching appointment, or null when there is none
     */
    private Appointment findAppointmentFor(Patient patient, TimeSlot time) {
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().equals(patient) && appointment.getTimeSlot().equals(time)) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Builds a warning message, so that every failed operation is reported in
     * the same way.
     *
     * @param reason why the operation could not be performed
     * @return the warning message
     */
    private static String warning(String reason) {
        return WARNING_PREFIX + reason;
    }

    /**
     * Describes the clinic and how many appointments it holds.
     *
     * @return a one line description of the clinic
     */
    @Override
    public String toString() {
        return name + " - " + operatingDay + ", " + schedule + ", "
                + appointments.size() + " appointment(s)";
    }
}
