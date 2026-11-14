import java.util.List;

/**
 * A general practitioner (GP): the first point of contact for a patient.
 *
 * <p>A GP is a {@link HealthProfessional}, so it inherits the identifier, the
 * name, the working days and the shared behaviour. It adds the consulting
 * room it uses, its years of experience and the ability to prescribe
 * medication.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class GeneralPractitioner extends HealthProfessional {

    /** The default consulting room used when none is supplied. */
    private static final String DEFAULT_ROOM = "G-01";

    /** The consulting room the GP uses, for example "G-01". */
    private final String consultingRoom;

    /** How many years the GP has been practising. */
    private final int yearsExperience;

    /**
     * Creates a GP with a full set of details.
     *
     * @param id              the identifier of the GP, numbers only
     * @param name            the full name of the GP
     * @param availableDays   the days of the week the GP works
     * @param consultingRoom  the consulting room the GP uses
     * @param yearsExperience the years of experience, zero or more
     */
    public GeneralPractitioner(String id, String name, List<String> availableDays,
            String consultingRoom, int yearsExperience) {
        super(id, name, availableDays);
        this.consultingRoom = isBlank(consultingRoom) ? DEFAULT_ROOM : consultingRoom.trim();
        // A negative number of years is replaced by zero, because experience
        // cannot be negative. No exception is thrown: exceptions belong to
        // Module 5, which this assessment may not use.
        this.yearsExperience = yearsExperience < 0 ? 0 : yearsExperience;
    }

    /**
     * Creates a GP who works Monday to Friday, using the first consulting room
     * and one year of experience. The caller only has to supply the identity.
     *
     * @param id   the identifier of the GP, numbers only
     * @param name the full name of the GP
     */
    public GeneralPractitioner(String id, String name) {
        this(id, name, Day.weekdays(), DEFAULT_ROOM, 1);
    }

    /**
     * Returns the consulting room the GP uses.
     *
     * @return the consulting room, for example "G-01"
     */
    public String getConsultingRoom() {
        return consultingRoom;
    }

    /**
     * Returns how many years the GP has been practising.
     *
     * @return the years of experience
     */
    public int getYearsExperience() {
        return yearsExperience;
    }

    /**
     * Returns the type of this health professional.
     *
     * @return the text "General Practitioner"
     */
    @Override
    public String getProfessionalType() {
        return "General Practitioner";
    }

    /**
     * Describes the services a GP provides.
     *
     * @return a description of the GP's services
     */
    @Override
    public String describeServices() {
        return "diagnoses common illnesses, treats ongoing conditions, refers patients to specialists and prescribes medication";
    }

    /**
     * Prescribes a medication for a patient and returns a record of the
     * prescription. This behaviour only exists on a GP, so it is not part of
     * the shared HealthProfessional type.
     *
     * @param patientName the name of the patient
     * @param medication  the medication and dosage, for example "Amoxicillin 500 mg"
     * @return a one line record of the prescription, or a warning when a detail is missing
     */
    public String prescribeMedication(String patientName, String medication) {
        if (isBlank(patientName)) {
            return WARNING_PREFIX + "a prescription needs a patient name.";
        }
        if (isBlank(medication)) {
            return WARNING_PREFIX + "a prescription needs a medication.";
        }
        return getName() + " prescribed " + medication.trim() + " to " + patientName.trim() + ".";
    }

    /**
     * Returns the shared details followed by the GP's own details.
     *
     * @return the full details of the GP, over several lines
     */
    @Override
    public String toString() {
        return super.toString()
                + "\n    Room: " + consultingRoom + " | Experience: " + formatYears(yearsExperience);
    }
}
