import java.util.Set;

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
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public GeneralPractitioner(String id, String name, Set<Day> availableDays,
            String consultingRoom, int yearsExperience) {
        super(id, name, availableDays);
        if (consultingRoom == null || consultingRoom.trim().isEmpty()) {
            throw new IllegalArgumentException("A general practitioner must have a consulting room.");
        }
        if (yearsExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative.");
        }
        this.consultingRoom = consultingRoom.trim();
        this.yearsExperience = yearsExperience;
    }

    /**
     * Creates a GP who works Monday to Friday, using the first consulting room
     * and one year of experience. The caller only has to supply the identity.
     *
     * @param id   the identifier of the GP, numbers only
     * @param name the full name of the GP
     * @throws IllegalArgumentException if the id or name is invalid
     */
    public GeneralPractitioner(String id, String name) {
        this(id, name, Day.weekdays(), "G-01", 1);
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
     * @return a one line record of the prescription
     * @throws IllegalArgumentException if the patient name or medication is missing
     */
    public String prescribeMedication(String patientName, String medication) {
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("A prescription needs a patient name.");
        }
        if (medication == null || medication.trim().isEmpty()) {
            throw new IllegalArgumentException("A prescription needs a medication.");
        }
        return String.format("%s prescribed %s to %s.", getName(), medication.trim(), patientName.trim());
    }

    /**
     * Returns the shared details followed by the GP's own details.
     *
     * @return the full details of the GP, over several lines
     */
    @Override
    public String toString() {
        return super.toString()
                + String.format("%n    Room: %s | Experience: %s", consultingRoom, formatYears(yearsExperience));
    }
}
