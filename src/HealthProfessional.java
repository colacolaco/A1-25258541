import java.util.EnumSet;
import java.util.Set;

/**
 * The information and behaviour shared by every health professional working
 * at the health service.
 *
 * <p>This class is abstract. A "plain" health professional has no specialty
 * and nothing useful to do, so the class cannot be instantiated directly:
 * {@code new HealthProfessional(...)} does not compile. Every concrete type
 * (general practitioner, specialist, nurse, surgeon and dietitian) must
 * provide the type specific behaviour declared here.</p>
 *
 * <p>The data is private and is only changed through the constructor. There
 * are therefore no setters, which means an object can never be put into an
 * invalid state after it has been created. External code can read the data
 * through the public getters.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public abstract class HealthProfessional {

    /** The unique identifier of the professional. Numbers only. */
    private final String id;

    /** The full name of the professional, for example "Dr Alice Nguyen". */
    private final String name;

    /** The days of the week on which this professional works. */
    private final Set<Day> availableDays;

    /**
     * Creates a health professional who works on the given days.
     *
     * <p>Constructors are protected rather than public because the class is
     * abstract: only a subclass can call them, using super(...).</p>
     *
     * @param id            the identifier of the professional, numbers only
     * @param name          the full name of the professional
     * @param availableDays the days of the week the professional works
     * @throws IllegalArgumentException if any argument is missing or invalid
     */
    protected HealthProfessional(String id, String name, Set<Day> availableDays) {
        if (!isDigitsOnly(id)) {
            throw new IllegalArgumentException("A health professional ID must contain numbers only, but was \"" + id + "\".");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("A health professional must have a name.");
        }
        if (availableDays == null || availableDays.isEmpty()) {
            throw new IllegalArgumentException("A health professional must work on at least one day of the week.");
        }
        this.id = id;
        this.name = name.trim();
        // A copy is stored so that later changes to the caller's set cannot
        // change this object, and EnumSet keeps the days in calendar order.
        this.availableDays = EnumSet.copyOf(availableDays);
    }

    /**
     * Creates a health professional who works the standard Monday to Friday
     * week. This is the second way of constructing an object: the caller does
     * not have to supply a set of days when the common working week applies.
     *
     * @param id   the identifier of the professional, numbers only
     * @param name the full name of the professional
     * @throws IllegalArgumentException if the id or name is invalid
     */
    protected HealthProfessional(String id, String name) {
        this(id, name, Day.weekdays());
    }

    /**
     * Returns the identifier of this professional.
     *
     * @return the identifier, which contains numbers only
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the full name of this professional.
     *
     * @return the name of the professional
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the days this professional works. The returned set is a copy, so
     * a caller may change it without changing the professional's roster.
     *
     * @return a copy of the working days
     */
    public Set<Day> getAvailableDays() {
        return EnumSet.copyOf(availableDays);
    }

    /**
     * Returns true when this professional works on the given day.
     *
     * @param day the day to check
     * @return true if the professional is available on that day
     */
    public boolean worksOn(Day day) {
        return day != null && availableDays.contains(day);
    }

    /**
     * Returns the name of the kind of professional this is, for example
     * "General Practitioner". Each concrete type supplies its own answer.
     *
     * @return the type of health professional
     */
    public abstract String getProfessionalType();

    /**
     * Describes the services this kind of professional provides. Each
     * concrete type supplies its own answer, which is how the different types
     * behave in their own way when they are treated as a HealthProfessional.
     *
     * @return a description of what this professional can do
     */
    public abstract String describeServices();

    /**
     * Returns a short one line summary of the professional, suitable for use
     * inside an appointment's details.
     *
     * @return a summary such as "Dr Alice Nguyen (General Practitioner, ID 1001)"
     */
    public String getSummary() {
        return String.format("%s (%s, ID %s)", name, getProfessionalType(), id);
    }

    /**
     * Returns the full details of the professional in a readable form.
     * A subclass extends this output by calling super.toString().
     *
     * @return the details of the professional, over several lines
     */
    @Override
    public String toString() {
        return String.format("[%s] ID %s, %s%n    Available: %s",
                getProfessionalType(), id, name, Day.format(availableDays));
    }

    /**
     * Two health professionals are considered equal when they have the same
     * identifier. The identifier is unique inside the clinic, so two objects
     * that describe the same person compare as equal even when they are
     * different objects. This is used when the clinic looks for an existing
     * booking for a professional.
     *
     * @param other the object to compare with
     * @return true if other is a health professional with the same id
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional that = (HealthProfessional) other;
        return id.equals(that.id);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}, based on
     * the identifier.
     *
     * @return a hash code for this professional
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Formats a number of years so that a single year is not printed as
     * "1 years". Subclasses use this when they print their own details.
     *
     * @param years the number of years
     * @return the number of years with the correct plural form
     */
    protected static String formatYears(int years) {
        return years == 1 ? "1 year" : years + " years";
    }

    /**
     * Returns true when the text contains at least one character and every
     * character is a digit from 0 to 9.
     *
     * @param text the text to check, which may be null
     * @return true if the text is numbers only
     */
    private static boolean isDigitsOnly(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);
            if (character < '0' || character > '9') {
                return false;
            }
        }
        return true;
    }
}
