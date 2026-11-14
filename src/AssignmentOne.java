import java.util.ArrayList;
import java.util.List;

/**
 * Assessment 1 demonstration program: a scripted simulation of the health
 * service appointment system.
 *
 * <p>There is no user interface and no saved data, as required by the brief.
 * The main method creates the objects and calls their methods, so running the
 * program shows the whole system working: the health professionals, their
 * shared and specific behaviour, the appointments, and the clinic that
 * manages them (including the bookings that must be rejected).</p>
 *
 * @author 25258541
 * @version 1.0
 */
public class AssignmentOne {

    /** The long line used to separate the sections of the output. */
    private static final String LINE = "==========================================================================================";

    /** The shorter line used to separate the parts of a section. */
    private static final String THIN_LINE = "------------------------------------------------------------------------------------------";

    /**
     * Runs the demonstration. The program takes no command line arguments.
     *
     * @param args command line arguments, which are not used
     */
    public static void main(String[] args) {
        printBanner();

        // ===================================================================
        // PART 3 - creating the health professionals and working with them
        // ===================================================================
        printSection("PARTS 1-3: Health professionals");

        // Part 1 and 2: one object of every concrete type. The first
        // constructor supplies every detail; the short constructor uses the
        // default Monday to Friday week.
        GeneralPractitioner drNguyen = new GeneralPractitioner("1001", "Dr Alice Nguyen",
                daysOf(new String[] {Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY}), "G-01", 12);
        GeneralPractitioner drCarter = new GeneralPractitioner("1002", "Dr Ben Carter",
                Day.weekdays(), "G-02", 5);
        GeneralPractitioner drRossi = new GeneralPractitioner("1003", "Dr Sofia Rossi",
                daysOf(new String[] {Day.TUESDAY, Day.THURSDAY}), "G-03", 20);
        Specialist drZhang = new Specialist("2001", "Dr Wei Zhang", "Cardiology",
                daysOf(new String[] {Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY}), "Ward 3B");
        Specialist drOkafor = new Specialist("2002", "Dr Amara Okafor", "Paediatrics");
        Nurse nurseMiller = new Nurse("3001", "Grace Miller", Day.weekdays(), "Registered Nurse", 8);
        Nurse nursePatel = new Nurse("3002", "Tom Patel");
        Surgeon drKing = new Surgeon("5001", "Dr Marcus King", "Orthopaedics",
                daysOf(new String[] {Day.MONDAY, Day.THURSDAY}), "Theatre 2", 6);
        Dietitian dietitianLee = new Dietitian("4001", "Hannah Lee", "Sports nutrition",
                daysOf(new String[] {Day.MONDAY, Day.WEDNESDAY}), "D-02");

        // One list of the general type holds every kind of professional, so
        // the loop below works without a separate loop for each type. Adding
        // a new type later only means adding one object to this list.
        List<HealthProfessional> staff = new ArrayList<>();
        staff.add(drNguyen);
        staff.add(drCarter);
        staff.add(drRossi);
        staff.add(drZhang);
        staff.add(drOkafor);
        staff.add(nurseMiller);
        staff.add(nursePatel);
        staff.add(drKing);
        staff.add(dietitianLee);

        System.out.println("The clinic employs " + staff.size() + " health professionals:");
        for (HealthProfessional professional : staff) {
            System.out.println(professional);
            System.out.println();
        }

        // The same method prints each object in its own way: toString is
        // overridden in every concrete class, which is polymorphism.
        printSubSection("Type specific behaviour (only available on the concrete type)");
        System.out.println("  " + drNguyen.prescribeMedication("Priya Sharma", "Amoxicillin 500 mg"));
        System.out.println("  " + drZhang.orderDiagnosticTest("Chen Wei", "Echocardiogram"));
        System.out.println("  " + nurseMiller.takeBloodSample("Emily Brown"));
        System.out.println("  " + drKing.performProcedure("Liam O'Brien", "Knee arthroscopy"));
        System.out.println("  " + dietitianLee.createMealPlan("Noah Wilson", "Lower cholesterol"));

        printSubSection("The same call, answered differently by each type (polymorphism)");
        for (HealthProfessional professional : staff) {
            System.out.println("  " + padRight(professional.getName() + ":", 19) + professional.describeServices());
        }

        // ===================================================================
        // PART 4, 5 and 6 - appointments and the clinic that manages them
        // ===================================================================
        printSection("PARTS 4-6: Appointments");

        // The clinic is open on Monday and uses the standard 08:00 to 16:00,
        // 30 minute schedule. In a real system the day would come from today's
        // date; it is supplied here so the demonstration always runs the same.
        Clinic clinic = new Clinic("SCU Health Service Clinic", Day.MONDAY);
        System.out.println("Clinic:          " + clinic.getName());
        System.out.println("Operating day:   " + clinic.getOperatingDay());
        System.out.println("Daily schedule:  " + clinic.getSchedule());

        // Part 4: a patient is its own object, so the same patient can make
        // more than one appointment and is only validated once.
        Patient priya = new Patient("Priya Sharma", "0412345678");
        Patient liam = new Patient("Liam O'Brien", "0422334455");
        Patient chen = new Patient("Chen Wei", "0433445566");
        Patient emily = new Patient("Emily Brown", "0444556677");
        Patient noah = new Patient("Noah Wilson", "0455667788");
        Patient olivia = new Patient("Olivia Martin", "0466778899");
        Patient maya = new Patient("Maya Singh", "0477889900");
        Patient jack = new Patient("Jack Taylor", "0470112233");

        printSubSection("Booking appointments");
        System.out.println("  " + clinic.addAppointment(new Appointment(priya, drNguyen, TimeSlot.of(9, 0))));
        System.out.println("  " + clinic.addAppointment(new Appointment(liam, drNguyen, TimeSlot.of(10, 30))));
        System.out.println("  " + clinic.addAppointment(new Appointment(chen, drZhang, TimeSlot.of(8, 30))));
        System.out.println("  " + clinic.addAppointment(new Appointment(emily, nurseMiller, TimeSlot.of(9, 30))));
        System.out.println("  " + clinic.addAppointment(new Appointment(noah, dietitianLee, TimeSlot.of(11, 0))));
        System.out.println("  " + clinic.addAppointment(new Appointment(olivia, drKing, TimeSlot.of(12, 0))));
        System.out.println("  " + clinic.addAppointment(new Appointment(maya, drOkafor, TimeSlot.of(10, 30))));
        System.out.println("  " + clinic.addAppointment(new Appointment(jack, drCarter, TimeSlot.of(8, 0))));

        printSubSection("Bookings that must be rejected (error cases)");
        // The same professional, the same time: a double booking.
        System.out.println("  " + clinic.addAppointment(new Appointment(maya, drNguyen, TimeSlot.of(9, 0))));
        // A professional who does not work on Monday.
        System.out.println("  " + clinic.addAppointment(new Appointment(jack, drRossi, TimeSlot.of(13, 0))));
        // A time that is not one of the clinic's 30 minute slots.
        System.out.println("  " + clinic.addAppointment(new Appointment(jack, drCarter, TimeSlot.of(7, 15))));
        // The patient already has an appointment at 09:00, with another GP.
        System.out.println("  " + clinic.addAppointment(new Appointment(priya, drCarter, TimeSlot.of(9, 0))));

        printSubSection("All appointments (in the order they were booked)");
        printAppointments(clinic.viewAllAppointments());

        printSubSection("All appointments sorted from earliest to latest");
        printAppointments(clinic.viewAllAppointmentsSortedByTime());

        printSubSection("Filtered by health professional ID 1001 (Dr Alice Nguyen)");
        printAppointments(clinic.viewAppointmentsForProfessional("1001"));

        printSubSection("Filtered by patient mobile number 0422334455 (Liam O'Brien)");
        printAppointments(clinic.viewAppointmentsForPatient("0422334455"));

        printSubSection("Filtered subset of ID 1001, sorted by time");
        printAppointments(clinic.sortByAppointmentTime(clinic.viewAppointmentsForProfessional("1001")));

        printSubSection("Cancelling an appointment by health professional ID and time");
        System.out.println("  " + clinic.cancelAppointment("3001", TimeSlot.of(9, 30)));

        printSubSection("Cancelling an appointment by patient mobile number");
        System.out.println("  " + clinic.cancelAppointment("0466778899"));

        printSubSection("Cancellations that must be rejected (error cases)");
        // No professional with this ID.
        System.out.println("  " + clinic.cancelAppointment("9999", TimeSlot.of(8, 0)));
        // No patient with this number.
        System.out.println("  " + clinic.cancelAppointment("0499999999"));
        // The appointment was already cancelled above.
        System.out.println("  " + clinic.cancelAppointment("3001", TimeSlot.of(9, 30)));

        printSubSection("The updated appointment book, sorted by time");
        printAppointments(clinic.viewAllAppointmentsSortedByTime());
        System.out.println();
        System.out.println("Total appointments now held by the clinic: " + clinic.getNumberOfAppointments());

        // ===================================================================
        // The fixed schedule can be changed without touching the booking code
        // ===================================================================
        printSection("THE FIXED DAILY SCHEDULE IS FLEXIBLE");
        System.out.println("Standard schedule: " + new ClinicSchedule());
        System.out.println("Longer day:        " + new ClinicSchedule(7, 19, 30));
        System.out.println("20 minute slots:   " + new ClinicSchedule(9, 17, 20));
        System.out.println();
        System.out.println("A different schedule object can be passed to the Clinic constructor;");
        System.out.println("addAppointment then validates times against it with no other change.");

        // ===================================================================
        // Part 1: invalid data is reported before an object is created
        // ===================================================================
        printSection("INVALID DATA IS REJECTED BEFORE AN OBJECT IS BUILT");
        System.out.println("  " + HealthProfessional.validateId("12A"));
        System.out.println("  " + Patient.validateMobile("12345"));
        System.out.println("  " + TimeSlot.validate(9, 75));
        System.out.println();
        System.out.println("Each check returns a message instead of causing an error, so the program");
        System.out.println("can show the message to the user and carry on with the valid data.");

        printSection("END OF DEMONSTRATION");
    }

    /**
     * Builds a list of working days from an array of day names, so that the
     * demonstration can pass several days to a constructor in one line.
     *
     * @param dayNames the days to put in the list
     * @return a new list holding the given days
     */
    private static List<String> daysOf(String[] dayNames) {
        List<String> days = new ArrayList<>();
        for (int index = 0; index < dayNames.length; index++) {
            days.add(dayNames[index]);
        }
        return days;
    }

    /**
     * Prints the title of the program.
     */
    private static void printBanner() {
        System.out.println(LINE);
        System.out.println("PROG2004 Object Oriented Programming - Assessment 1");
        System.out.println("Health Service Appointment System");
        System.out.println("Student: 25258541");
        System.out.println(LINE);
    }

    /**
     * Prints a heading for one of the six parts of the task.
     *
     * @param title the heading text
     */
    private static void printSection(String title) {
        System.out.println();
        System.out.println(LINE);
        System.out.println(title);
        System.out.println(LINE);
    }

    /**
     * Prints a heading for one demonstration inside a section.
     *
     * @param title the heading text
     */
    private static void printSubSection(String title) {
        System.out.println();
        System.out.println(title);
        System.out.println(THIN_LINE);
    }

    /**
     * Pads text with spaces on the right until it reaches the given width, so
     * the columns of the demonstration output stay aligned.
     *
     * @param text  the text to pad
     * @param width the minimum width of the result
     * @return the text followed by enough spaces to reach the width
     */
    private static String padRight(String text, int width) {
        String padded = text;
        while (padded.length() < width) {
            padded = padded + " ";
        }
        return padded;
    }

    /**
     * Prints a list of appointments, one per line, and says when the list is
     * empty.
     *
     * @param appointments the appointments to print
     */
    private static void printAppointments(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            System.out.println("  (no appointments)");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println("  " + appointment);
        }
    }
}
