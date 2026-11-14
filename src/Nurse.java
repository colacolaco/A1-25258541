import java.util.Set;

/**
 * A nurse: a health professional who provides day to day care and can take
 * blood samples.
 *
 * <p>A nurse is not a doctor, so the class does not extend the doctor types.
 * It extends {@link HealthProfessional} directly and adds only the details
 * that belong to a nurse: the nursing qualification and the years registered.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class Nurse extends HealthProfessional {

    /** The nursing qualification, for example "Registered Nurse". */
    private final String qualification;

    /** How many years the nurse has been registered. */
    private final int yearsExperience;

    /**
     * Creates a nurse with a full set of details.
     *
     * @param id            the identifier of the nurse, numbers only
     * @param name          the full name of the nurse
     * @param availableDays the days of the week the nurse works
     * @param qualification the nursing qualification
     * @param yearsExperience the years registered, zero or more
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Nurse(String id, String name, Set<Day> availableDays, String qualification, int yearsExperience) {
        super(id, name, availableDays);
        if (qualification == null || qualification.trim().isEmpty()) {
            throw new IllegalArgumentException("A nurse must have a qualification.");
        }
        if (yearsExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative.");
        }
        this.qualification = qualification.trim();
        this.yearsExperience = yearsExperience;
    }

    /**
     * Creates a registered nurse who works Monday to Friday and has one year
     * of experience.
     *
     * @param id   the identifier of the nurse, numbers only
     * @param name the full name of the nurse
     * @throws IllegalArgumentException if the id or name is invalid
     */
    public Nurse(String id, String name) {
        this(id, name, Day.weekdays(), "Registered Nurse", 1);
    }

    /**
     * Returns the nursing qualification of this nurse.
     *
     * @return the qualification, for example "Registered Nurse"
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Returns how many years the nurse has been registered.
     *
     * @return the years of experience
     */
    public int getYearsExperience() {
        return yearsExperience;
    }

    /**
     * Returns the type of this health professional.
     *
     * @return the text "Nurse"
     */
    @Override
    public String getProfessionalType() {
        return "Nurse";
    }

    /**
     * Describes the services a nurse provides.
     *
     * @return a description of the nurse's services
     */
    @Override
    public String describeServices() {
        return "monitors patients, changes dressings, takes blood samples and gives immunisations";
    }

    /**
     * Takes a blood sample from a patient and returns a record of it.
     *
     * @param patientName the name of the patient
     * @return a one line record of the sample
     * @throws IllegalArgumentException if the patient name is missing
     */
    public String takeBloodSample(String patientName) {
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("A blood sample needs a patient name.");
        }
        return String.format("%s took a blood sample from %s.", getName(), patientName.trim());
    }

    /**
     * Returns the shared details followed by the nurse's own details.
     *
     * @return the full details of the nurse, over several lines
     */
    @Override
    public String toString() {
        return super.toString()
                + String.format("%n    Qualification: %s | Experience: %s", qualification, formatYears(yearsExperience));
    }
}
