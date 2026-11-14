import java.util.List;

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

    /** The default area of dietetics used when none is supplied. */
    private static final String DEFAULT_SPECIALITY = "General dietetics";

    /** The default consulting room used when none is supplied. */
    private static final String DEFAULT_ROOM = "D-01";

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
     */
    public Dietitian(String id, String name, String speciality, List<String> availableDays, String consultingRoom) {
        super(id, name, availableDays);
        this.speciality = isBlank(speciality) ? DEFAULT_SPECIALITY : speciality.trim();
        this.consultingRoom = isBlank(consultingRoom) ? DEFAULT_ROOM : consultingRoom.trim();
    }

    /**
     * Creates a dietitian who works Monday to Friday in the general dietetics
     * area, using consulting room D-01.
     *
     * @param id   the identifier of the dietitian, numbers only
     * @param name the full name of the dietitian
     */
    public Dietitian(String id, String name) {
        this(id, name, DEFAULT_SPECIALITY, Day.weekdays(), DEFAULT_ROOM);
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
     * @return a one line record of the plan, or a warning when a detail is missing
     */
    public String createMealPlan(String patientName, String goal) {
        if (isBlank(patientName)) {
            return WARNING_PREFIX + "a meal plan needs a patient name.";
        }
        if (isBlank(goal)) {
            return WARNING_PREFIX + "a meal plan needs a health goal.";
        }
        return getName() + " created a meal plan for " + patientName.trim() + " with the goal: " + goal.trim() + ".";
    }

    /**
     * Returns the shared details followed by the dietitian's own details.
     *
     * @return the full details of the dietitian, over several lines
     */
    @Override
    public String toString() {
        return super.toString() + "\n    Room: " + consultingRoom;
    }
}
