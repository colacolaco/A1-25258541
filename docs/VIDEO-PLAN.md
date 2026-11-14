# Video plan (maximum 5 minutes)

The video has to be recorded in your own words. This is the list of points to
cover and the code to have open on screen, in the order that makes the four
pillars easiest to follow. Do not read this out - talk through the code.

Before recording: open the project in the IDE, run the program once so the
console output is on screen, and have `console-output.txt` beside it.

## 0:00 - 0:25 The system in one sentence

- A health service appointment system: health professionals, patients,
  appointments on one day, and a clinic that manages the bookings.
- Run the program, scroll the console output once: professionals, then the
  bookings, one rejected double booking, one cancellation.

## 0:25 - 1:20 Abstraction

Open `HealthProfessional.java`.

- `public abstract class HealthProfessional` - the shared idea of a health
  professional, not a thing that exists on its own.
- `public abstract String getProfessionalType();` and
  `public abstract String describeServices();` - the superclass says *what* every
  professional must be able to answer, and leaves *how* to the subclasses.
- You cannot write `new HealthProfessional(...)`, so the clinic can never hold a
  professional of no particular kind.
- Second example, `Clinic.addAppointment`: the caller does not need to know the
  list or the five validation checks; it asks for an appointment and gets a
  message back.

## 1:20 - 2:10 Encapsulation

Still in `HealthProfessional.java`.

- All fields `private final`; `id`, `name`, `availableDays` cannot be reassigned.
- Validation: `validateId`, `validateName` and `validateDays` return either an
  empty string or a message starting with `WARNING:`, and the constructors
  fall back to safe defaults instead of building an object in a half-finished
  state. Show the manual digit loop in `isDigitsOnly`.
- `getAvailableDays()` returns `new ArrayList<>(availableDays)` - the caller
  gets their own copy, so they can read the days but cannot edit the
  professional's week. The same "return a copy" idea is used by
  `ClinicSchedule.getSlots()`.
- No silent repair: `hasValidDetails()` on a professional or a patient, and
  `isValid()` on a `TimeSlot`, remember whether a default had to be used, and
  `Clinic.addAppointment` refuses anything that was corrected. Show the four
  warnings printed by the "protects the appointment book" block.
- `Patient.java`: the mobile number is validated to ten digits starting with
  `04`, and `toString()` formats it for display while the field keeps the plain
  digits.
- `equals`/`hashCode` on `HealthProfessional` use the identifier only, which is
  what makes a double booking detectable in `Clinic.addAppointment`.

## 2:10 - 3:00 Inheritance

- The five subclasses: `GeneralPractitioner`, `Specialist`, `Nurse`, `Surgeon`,
  `Dietitian`, each `extends HealthProfessional`.
- `super(id, name, availableDays)` in each constructor: the shared fields are
  set once, in one place.
- `toString()` in a subclass calls `super.toString()` and adds its own line, so
  the shared format is written once (in `HealthProfessional`), not five times.
- `formatYears` in the superclass is reused by the GP and the Nurse.
- Two constructors: the full one and the short one, e.g.
  `public Nurse(String id, String name)` which fills in the default week.

## 3:00 - 3:50 Polymorphism

Open `AssignmentOne.java`, the `staff` list.

- `List<HealthProfessional> staff` holds all five types.
- `System.out.println(professional)` calls `toString()`; the version that runs
  depends on the object's real type - point at the different output lines.
- `professional.describeServices()` in the loop: one call, nine different
  answers.
- The other direction: `drNguyen.prescribeMedication(...)` only compiles on the
  concrete type, which is why the subclasses keep their own behaviour.
- Sorting: `Collections.sort(sorted)` in `Clinic.sortByAppointmentTime` works
  because `Appointment.compareTo` compares time slots - polymorphism through the
  `Comparable` interface, so no comparator has to be written.

## 3:50 - 4:30 The demonstration, briefly

- Show the double booking being rejected with a `WARNING:` message.
- Show a filtered list (`viewAppointmentsForProfessional("1001")`).
- Show a cancellation and the shorter appointment book afterwards.

## 4:30 - 5:00 Close

- One or two sentences on design decisions you made yourself: why the
  appointment book is an `ArrayList`, why the clinic has an explicit
  `operatingDay`, why `ClinicSchedule` is a separate class.

## Recording checklist

- [ ] Under 5 minutes.
- [ ] Your face or your voice, and the code on screen.
- [ ] Every pillar named out loud, with at least one file and method shown.
- [ ] No reading from a written script.
- [ ] Upload unlisted to YouTube (or OneDrive/SCU Panopto) and check the link
      opens in a private browsing window.
