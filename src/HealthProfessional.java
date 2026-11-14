import java.util.ArrayList;
import java.util.List;

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
 * <p>Because exceptions belong to Module 5 and this assessment may only use
 * Modules 1 to 3, an invalid value is reported by the static validation
 * methods below instead of being thrown. The constructor itself can therefore
 * never fail: it replaces a missing or invalid value with a safe default, so
 * that the object it builds always holds usable data.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public abstract class HealthProfessional {

    /** The ID used when the supplied ID is not valid. */
    private static final String UNKNOWN_ID = "0000";

    /** The name used when the supplied name is missing. */
    private static final String UNKNOWN_NAME = "Unnamed professional";

    /** The prefix used by every validation message that reports a problem. */
    public static final String WARNING_PREFIX = "WARNING: ";

    /** The unique identifier of the professional. Numbers only. */
    private final String id;

    /** The full name of the professional, for example "Dr Alice Nguyen". */
    private final String name;

    /** The days of the week on which this professional works, in calendar order. */
    private final List<String> availableDays;

    /** True when every detail passed to the constructor was valid. */
    private final boolean validDetails;

    /**
     * Creates a health professional who works on the given days.
     *
     * <p>Constructors are protected rather than public because the class is
     * abstract: only a subclass can call them, using super(...).</p>
     *
     * @param id            the identifier of the professional, numbers only
     * @param name          the full name of the professional
     * @param availableDays the days of the week the professional works
     */
    protected HealthProfessional(String id, String name, List<String> availableDays) {
        this.validDetails = validateId(id).isEmpty()
                && validateName(name).isEmpty()
                && validateDays(availableDays).isEmpty();
        this.id = isDigitsOnly(id) ? id : UNKNOWN_ID;
        this.name = isBlank(name) ? UNKNOWN_NAME : name.trim();
        this.availableDays = normaliseDays(availableDays);
    }

    /**
     * Creates a health professional who works the standard Monday to Friday
     * week. This is the second way of constructing an object: the caller does
     * not have to supply the days when the common working week applies.
     *
     * @param id   the identifier of the professional, numbers only
     * @param name the full name of the professional
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
     * Returns the days this professional works. The returned list is a copy,
     * so a caller may change it without changing the professional's roster.
     *
     * @return a copy of the working days
     */
    public List<String> getAvailableDays() {
        return new ArrayList<>(availableDays);
    }

    /**
     * Returns true when the professional was created from valid details.
     *
     * <p>An object built from invalid data still holds safe, usable values
     * (the constructor never fails), but it remembers that the data was not
     * valid. The clinic uses this flag to refuse such a professional instead
     * of quietly booking an appointment against a default ID.</p>
     *
     * @return true if the ID, the name and the working days were all valid
     */
    public boolean hasValidDetails() {
        return validDetails;
    }

    /**
     * Returns true when this professional works on the given day.
     *
     * @param day the day to check, for example "Monday"
     * @return true if the professional is available on that day
     */
    public boolean worksOn(String day) {
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
        return name + " (" + getProfessionalType() + ", ID " + id + ")";
    }

    /**
     * Returns the full details of the professional in a readable form.
     * A subclass extends this output by calling super.toString().
     *
     * @return the details of the professional, over several lines
     */
    @Override
    public String toString() {
        return "[" + getProfessionalType() + "] ID " + id + ", " + name
                + "\n    Available: " + Day.format(availableDays);
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
     * Checks a professional ID. The ID must contain at least one digit and no
     * other character, so that it can be used in a search.
     *
     * @param id the ID to check, which may be null or empty
     * @return an empty text when the ID is valid, or a warning explaining why it is not
     */
    public static String validateId(String id) {
        if (isBlank(id)) {
            return WARNING_PREFIX + "a health professional must have an ID.";
        }
        if (!isDigitsOnly(id)) {
            return WARNING_PREFIX + "a health professional ID must contain numbers only, but was \"" + id + "\".";
        }
        return "";
    }

    /**
     * Checks a professional name.
     *
     * @param name the name to check, which may be null or empty
     * @return an empty text when the name is valid, or a warning explaining why it is not
     */
    public static String validateName(String name) {
        if (isBlank(name)) {
            return WARNING_PREFIX + "a health professional must have a name.";
        }
        return "";
    }

    /**
     * Checks a list of working days. At least one day is required and every
     * entry must be a real day name.
     *
     * @param days the days to check, which may be null
     * @return an empty text when the days are valid, or a warning explaining why they are not
     */
    public static String validateDays(List<String> days) {
        if (days == null || days.isEmpty()) {
            return WARNING_PREFIX + "a health professional must work on at least one day of the week.";
        }
        for (String day : days) {
            if (!Day.isValid(day)) {
                return WARNING_PREFIX + "\"" + day + "\" is not a day of the week.";
            }
        }
        return "";
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
     * Returns true when the text is null, empty, or contains only spaces.
     * Subclasses use this when they check their own fields.
     *
     * @param text the text to check
     * @return true if there is no usable text
     */
    protected static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
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

    /**
     * Builds the list of working days that is stored in the object.
     *
     * <p>Invalid entries are dropped and the days are put into calendar order,
     * so the object always holds a usable roster. When nothing usable was
     * supplied the standard Monday to Friday week is used.</p>
     *
     * @param days the days supplied by the caller, which may be null
     * @return a new list of valid working days in calendar order
     */
    private static List<String> normaliseDays(List<String> days) {
        List<String> ordered = new ArrayList<>();
        if (days == null || days.isEmpty()) {
            return Day.weekdays();
        }
        String[] allDays = Day.allDays();
        for (int index = 0; index < allDays.length; index++) {
            if (days.contains(allDays[index])) {
                ordered.add(allDays[index]);
            }
        }
        if (ordered.isEmpty()) {
            return Day.weekdays();
        }
        return ordered;
    }
}
