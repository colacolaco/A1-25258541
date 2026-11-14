/**
 * A patient of the health service: a name and a mobile phone number.
 *
 * <p>Patient is a class of its own instead of two fields on an appointment,
 * because the same patient may make several appointments. Keeping the patient
 * in one object means the details are validated once, and the appointment only
 * has to hold a reference to the patient.</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class Patient {

    /** The number of digits in an Australian mobile phone number. */
    private static final int MOBILE_DIGITS = 10;

    /** The prefix of an Australian mobile phone number. */
    private static final String MOBILE_PREFIX = "04";

    /** The name used when the supplied name is missing. */
    private static final String UNKNOWN_NAME = "Unnamed patient";

    /** The number used when the supplied number is not valid. */
    private static final String UNKNOWN_MOBILE = "0400000000";

    /** The full name of the patient. */
    private final String name;

    /** The mobile phone number, stored as ten digits with no spaces. */
    private final String mobileNumber;

    /**
     * Creates a patient with a name and an Australian mobile phone number.
     *
     * <p>Invalid details are replaced by a safe default instead of causing an
     * error, because exceptions are not part of Modules 1 to 3. Call
     * {@link #validateName(String)} and {@link #validateMobile(String)} before
     * creating the object when the problem has to be reported to a user.</p>
     *
     * @param name         the full name of the patient
     * @param mobileNumber the mobile number as ten digits, for example "0412345678"
     */
    public Patient(String name, String mobileNumber) {
        this.name = isBlank(name) ? UNKNOWN_NAME : name.trim();
        this.mobileNumber = isValidMobile(mobileNumber) ? mobileNumber : UNKNOWN_MOBILE;
    }

    /**
     * Returns the full name of the patient.
     *
     * @return the name of the patient
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the mobile number as ten digits with no spaces, which is the
     * form used when searching for a patient's appointments.
     *
     * @return the mobile number, for example "0412345678"
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Returns the full details of the patient, with the mobile number spaced
     * so that it is easy to read aloud.
     *
     * @return the details of the patient
     */
    @Override
    public String toString() {
        return name + " (" + mobileNumber.substring(0, 4) + " " + mobileNumber.substring(4, 7)
                + " " + mobileNumber.substring(7) + ")";
    }

    /**
     * Two patients are considered equal when they have the same name and the
     * same mobile number. The mobile number alone is not enough, because a
     * parent and a child may share a contact number.
     *
     * @param other the object to compare with
     * @return true if other is a patient with the same name and number
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Patient)) {
            return false;
        }
        Patient that = (Patient) other;
        return name.equals(that.name) && mobileNumber.equals(that.mobileNumber);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return a hash code for this patient
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + mobileNumber.hashCode();
        return result;
    }

    /**
     * Checks a patient name.
     *
     * @param name the name to check, which may be null or empty
     * @return an empty text when the name is valid, or a warning explaining why it is not
     */
    public static String validateName(String name) {
        if (isBlank(name)) {
            return "WARNING: a patient must have a name.";
        }
        return "";
    }

    /**
     * Checks an Australian mobile phone number: ten digits that start with
     * "04".
     *
     * @param number the number to check, which may be null
     * @return an empty text when the number is valid, or a warning explaining why it is not
     */
    public static String validateMobile(String number) {
        if (!isValidMobile(number)) {
            return "WARNING: a mobile number must be " + MOBILE_DIGITS
                    + " digits starting with " + MOBILE_PREFIX + ", but was \"" + number + "\".";
        }
        return "";
    }

    /**
     * Returns true when the text is a valid Australian mobile number.
     *
     * @param number the text to check, which may be null
     * @return true if the text is a valid mobile number
     */
    private static boolean isValidMobile(String number) {
        if (number == null || number.length() != MOBILE_DIGITS || !number.startsWith(MOBILE_PREFIX)) {
            return false;
        }
        for (int index = 0; index < number.length(); index++) {
            char digit = number.charAt(index);
            if (digit < '0' || digit > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true when the text is null, empty, or contains only spaces.
     *
     * @param text the text to check
     * @return true if there is no usable text
     */
    private static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
