import java.util.Set;

/**
 * A specialist: a doctor who focuses on one area of medicine, such as
 * cardiology or paediatrics.
 *
 * <p>The area of specialisation is held in its own field, so one Specialist
 * class can describe a paediatrician, a radiologist or a cardiologist without
 * creating a separate class for each one.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class Specialist extends HealthProfessional {

    /** The area the specialist works in, for example "Cardiology". */
    private final String speciality;

    /** The hospital ward the specialist is based in. */
    private final String ward;

    /**
     * Creates a specialist with a full set of details.
     *
     * @param id            the identifier of the specialist, numbers only
     * @param name          the full name of the specialist
     * @param speciality    the area of medicine, for example "Cardiology"
     * @param availableDays the days of the week the specialist works
     * @param ward          the hospital ward the specialist is based in
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Specialist(String id, String name, String speciality, Set<Day> availableDays, String ward) {
        super(id, name, availableDays);
        if (speciality == null || speciality.trim().isEmpty()) {
            throw new IllegalArgumentException("A specialist must have an area of specialisation.");
        }
        if (ward == null || ward.trim().isEmpty()) {
            throw new IllegalArgumentException("A specialist must be based in a ward.");
        }
        this.speciality = speciality.trim();
        this.ward = ward.trim();
    }

    /**
     * Creates a specialist who works Monday to Friday and is based in Ward 1.
     *
     * @param id         the identifier of the specialist, numbers only
     * @param name       the full name of the specialist
     * @param speciality the area of medicine, for example "Paediatrics"
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Specialist(String id, String name, String speciality) {
        this(id, name, speciality, Day.weekdays(), "Ward 1");
    }

    /**
     * Returns the area of medicine this specialist works in.
     *
     * @return the speciality, for example "Cardiology"
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Returns the ward the specialist is based in.
     *
     * @return the ward name
     */
    public String getWard() {
        return ward;
    }

    /**
     * Returns the type of this health professional, including the area of
     * specialisation, so the details clearly show which kind of doctor it is.
     *
     * @return text such as "Specialist - Cardiology"
     */
    @Override
    public String getProfessionalType() {
        return "Specialist - " + speciality;
    }

    /**
     * Describes the services a specialist provides.
     *
     * @return a description of the specialist's services
     */
    @Override
    public String describeServices() {
        return "provides expert assessment in " + speciality + ", diagnoses complex conditions and orders diagnostic tests";
    }

    /**
     * Orders a diagnostic test for a patient and returns a record of it.
     *
     * @param patientName the name of the patient
     * @param testName    the test being ordered, for example "Echocardiogram"
     * @return a one line record of the test order
     * @throws IllegalArgumentException if the patient name or test name is missing
     */
    public String orderDiagnosticTest(String patientName, String testName) {
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("A test order needs a patient name.");
        }
        if (testName == null || testName.trim().isEmpty()) {
            throw new IllegalArgumentException("A test order needs the name of a test.");
        }
        return String.format("%s ordered a diagnostic test (%s) for %s.", getName(), testName.trim(), patientName.trim());
    }

    /**
     * Returns the shared details followed by the specialist's own details.
     *
     * @return the full details of the specialist, over several lines
     */
    @Override
    public String toString() {
        return super.toString() + String.format("%n    Ward: %s", ward);
    }
}
