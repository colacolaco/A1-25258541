import java.util.Set;

/**
 * A dietitian: a health professional who plans what a patient eats.
 *
 * <p>This is the fourth concrete type required by the brief. Like the other
 * types it extends {@link HealthProfessional}, so it reuses the identifier,
 * the name and the working days, and it adds only the details that matter to
 * a dietitian.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class Dietitian extends HealthProfessional {

    /** The area of dietetics, for example "Sports nutrition". */
    private final String speciality;

    /** The consulting room the dietitian uses. */
    private final String consultingRoom;

    /**
     * Creates a dietitian with a full set of details.
     *
     * @param id             the identifier of the dietitian, numbers only
     * @param name           the full name of the dietitian
     * @param speciality     the area of dietetics
     * @param availableDays  the days of the week the dietitian works
     * @param consultingRoom the consulting room the dietitian uses
     * @throws IllegalArgumentException if any detail is missing or invalid
     */
    public Dietitian(String id, String name, String speciality, Set<Day> availableDays, String consultingRoom) {
        super(id, name, availableDays);
        if (speciality == null || speciality.trim().isEmpty()) {
            throw new IllegalArgumentException("A dietitian must have an area of specialisation.");
        }
        if (consultingRoom == null || consultingRoom.trim().isEmpty()) {
            throw new IllegalArgumentException("A dietitian must have a consulting room.");
        }
        this.speciality = speciality.trim();
        this.consultingRoom = consultingRoom.trim();
    }

    /**
     * Creates a dietitian who works Monday to Friday in the general dietetics
     * area, using consulting room D-01.
     *
     * @param id   the identifier of the dietitian, numbers only
     * @param name the full name of the dietitian
     * @throws IllegalArgumentException if the id or name is invalid
     */
    public Dietitian(String id, String name) {
        this(id, name, "General dietetics", Day.weekdays(), "D-01");
    }

    /**
     * Returns the area of dietetics this dietitian works in.
     *
     * @return the area of dietetics
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Returns the consulting room the dietitian uses.
     *
     * @return the consulting room name
     */
    public String getConsultingRoom() {
        return consultingRoom;
    }

    /**
     * Returns the type of this health professional, with the area of
     * dietetics, so the details clearly show which kind of dietitian it is.
     *
     * @return text such as "Dietitian - Sports nutrition"
     */
    @Override
    public String getProfessionalType() {
        return "Dietitian - " + speciality;
    }

    /**
     * Describes the services a dietitian provides.
     *
     * @return a description of the dietitian's services
     */
    @Override
    public String describeServices() {
        return "assesses nutritional needs and designs meal plans for " + speciality.toLowerCase();
    }

    /**
     * Creates a meal plan for a patient and returns a record of it.
     *
     * @param patientName the name of the patient
     * @param goal        the health goal of the plan, for example "Lower cholesterol"
     * @return a one line record of the plan
     * @throws IllegalArgumentException if the patient name or goal is missing
     */
    public String createMealPlan(String patientName, String goal) {
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("A meal plan needs a patient name.");
        }
        if (goal == null || goal.trim().isEmpty()) {
            throw new IllegalArgumentException("A meal plan needs a health goal.");
        }
        return String.format("%s created a meal plan for %s with the goal: %s.",
                getName(), patientName.trim(), goal.trim());
    }

    /**
     * Returns the shared details followed by the dietitian's own details.
     *
     * @return the full details of the dietitian, over several lines
     */
    @Override
    public String toString() {
        return super.toString() + String.format("%n    Room: %s", consultingRoom);
    }
}
