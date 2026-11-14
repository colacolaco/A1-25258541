import java.util.Set;

/**
 * A surgeon: a doctor who performs operations.
 *
 * <p>The class shows that the shared type can be specialised in a different
 * way from a GP or a specialist: a surgeon records the operating theatre it
 * uses and how many operations it performs each week, and its own behaviour
 * is to perform a procedure.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class Surgeon extends HealthProfessional {

    /** The area of surgery, for example "Orthopaedics". */
    private final String speciality;

    /** The operating theatre the surgeon uses. */
    private final String operatingTheatre;

    /** The number of operations the surgeon performs in a week. */
    private final int operationsPerWeek;

    /**
     * Creates a surgeon with a full set of details.
     *
     * @param id              the identifier of the surgeon, numbers only
     * @param name            the full name of the surgeon
     * @param speciality      the area of surgery
     * @param availableDays   the days of the week the surgeon works
     * @param operatingTheatre the operating theatre the surgeon uses
     * @param operationsPerWeek the number of operations per week, zero or more
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Surgeon(String id, String name, String speciality, Set<Day> availableDays,
            String operatingTheatre, int operationsPerWeek) {
        super(id, name, availableDays);
        if (speciality == null || speciality.trim().isEmpty()) {
            throw new IllegalArgumentException("A surgeon must have an area of surgery.");
        }
        if (operatingTheatre == null || operatingTheatre.trim().isEmpty()) {
            throw new IllegalArgumentException("A surgeon must have an operating theatre.");
        }
        if (operationsPerWeek < 0) {
            throw new IllegalArgumentException("The number of operations cannot be negative.");
        }
        this.speciality = speciality.trim();
        this.operatingTheatre = operatingTheatre.trim();
        this.operationsPerWeek = operationsPerWeek;
    }

    /**
     * Creates an orthopaedic surgeon who works Monday to Friday in Theatre 1
     * and performs four operations each week.
     *
     * @param id   the identifier of the surgeon, numbers only
     * @param name the full name of the surgeon
     * @throws IllegalArgumentException if the id or name is invalid
     */
    public Surgeon(String id, String name) {
        this(id, name, "Orthopaedics", Day.weekdays(), "Theatre 1", 4);
    }

    /**
     * Returns the area of surgery this surgeon works in.
     *
     * @return the area of surgery, for example "Orthopaedics"
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Returns the operating theatre the surgeon uses.
     *
     * @return the operating theatre name
     */
    public String getOperatingTheatre() {
        return operatingTheatre;
    }

    /**
     * Returns how many operations the surgeon performs in a week.
     *
     * @return the number of operations per week
     */
    public int getOperationsPerWeek() {
        return operationsPerWeek;
    }

    /**
     * Returns the type of this health professional, with the area of surgery,
     * so the details clearly show which kind of surgeon it is.
     *
     * @return text such as "Surgeon - Orthopaedics"
     */
    @Override
    public String getProfessionalType() {
        return "Surgeon - " + speciality;
    }

    /**
     * Describes the services a surgeon provides.
     *
     * @return a description of the surgeon's services
     */
    @Override
    public String describeServices() {
        return "performs " + speciality + " operations and provides post operative care";
    }

    /**
     * Records that the surgeon has performed a procedure on a patient.
     *
     * @param patientName the name of the patient
     * @param procedure   the name of the procedure
     * @return a one line record of the operation
     * @throws IllegalArgumentException if the patient name or procedure is missing
     */
    public String performProcedure(String patientName, String procedure) {
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("An operation needs a patient name.");
        }
        if (procedure == null || procedure.trim().isEmpty()) {
            throw new IllegalArgumentException("An operation needs the name of a procedure.");
        }
        return String.format("%s performed the %s procedure on %s in %s.",
                getName(), procedure.trim(), patientName.trim(), operatingTheatre);
    }

    /**
     * Returns the shared details followed by the surgeon's own details.
     *
     * @return the full details of the surgeon, over several lines
     */
    @Override
    public String toString() {
        return super.toString()
                + String.format("%n    Theatre: %s | Operations per week: %d", operatingTheatre, operationsPerWeek);
    }
}
