# PROG2004 Assessment 1 - Health Service Appointment System

Student: 25258541
Unit: PROG2004 Object Oriented Programming, Assessment 1 (Modules 1-3)
Project name: `A1-25258541`

## What this is

A small Java backbone for a health service appointment system. There is no
graphical interface and no saved data, as the brief requires: the program
creates objects and exercises their behaviour in `main`, printing a scripted
simulation to the console.

## How to compile and run

Requires a JDK (developed and tested with JDK 17).

```
javac -d bin src/*.java
java -cp bin AssignmentOne
```

`console-output.txt` is the complete output of one run, kept as evidence of the
demonstration.

## Project layout

```
A1-25258541/
  src/                 all source files (default package, no package declaration)
    AssignmentOne.java the demonstration program (main)
    Day.java           the seven days of the week
    TimeSlot.java      a time of day, ordered so appointments can be sorted
    ClinicSchedule.java the fixed 08:00-16:00, 30 minute daily schedule
    HealthProfessional.java abstract superclass for every health professional
    GeneralPractitioner.java
    Specialist.java
    Nurse.java
    Surgeon.java
    Dietitian.java
    Patient.java       patient name and validated mobile number
    Appointment.java   patient + health professional + time, ordered by time
    Clinic.java        manages the appointment book
  docs/                video plan and submission checklist
  README.md
  .gitignore
  console-output.txt
```

## Design and the reasoning behind it

**`HealthProfessional` (Part 1).** The brief asks for a single general class, but
a health service always has more than one kind, so the class is `abstract`: it
holds what every professional has (identifier, name, the days they work) and
declares `getProfessionalType()` and `describeServices()` without defining
them. An abstract class cannot be instantiated, which guarantees the clinic
never holds a professional whose type is unknown.

All state is `private final`, so an object can never reach an invalid state once
it is built; validation happens in the constructor, which is the only place it
needs to run. The identifier is checked with a character loop rather than a
regular expression, and the working days are stored as an `EnumSet`, which
keeps them unique and sorted and makes `worksOn(Day)` a single lookup. Getters
return a fresh copy, so callers can read the days but cannot change them.
Two constructors are supplied: one taking every detail and a shorter one that
uses the Monday to Friday week, so the common case stays short.

**The concrete types (Part 2).** `GeneralPractitioner`, `Specialist`, `Nurse`,
`Surgeon` and `Dietitian` each add only their own fields (consulting room,
speciality, ward, theatre, qualification) and override `toString()` to print
the shared details first and then their own, so no class repeats the shared
formatting. Each also has one behaviour that only it has, which is deliberately
not declared on the superclass: `prescribeMedication`, `orderDiagnosticTest`,
`takeBloodSample`, `performProcedure`, `createMealPlan`. The superclass therefore
does not depend on details of its subtypes. `getProfessionalType()` is where
the differences that belong on the common type are expressed, so a single
`System.out.println(professional)` prints the right details for any type, and
`describeServices()` gives different text for the same call.

**Working with the objects together (Part 3).** `main` stores every professional
in one `List<HealthProfessional>` and loops over it. Adding a sixth type later
means adding one class and one `staff.add(...)` line; no existing loop changes.

**`Appointment` (Part 4).** The patient is its own small class holding just the
name and the mobile number, validated to ten digits starting with `04`, which
keeps the validation in one place and lets one patient book several
appointments. The three parts of an appointment are `final` and null-checked
in the constructor, so an incomplete appointment cannot exist. `TimeSlot`
implements `Comparable` and is what makes appointment ordering work: comparing
two `Appointment` objects compares their time slots first, and only when the
times are equal compares the professional identifiers, which gives a stable
total order and satisfies the brief's allowance that same-time appointments may
be in any order.

**`Clinic` (Part 5).** The appointment book is an `ArrayList<Appointment>`. The
schedule is the separate `ClinicSchedule` class so the clinic's opening hours are
configured in one place, and `addAppointment` validates in a deliberate order:
the appointment exists, the time is a bookable slot, the professional works that
day, the professional is free at that time, and the patient is free at that
time. Every check returns a message starting with `WARNING:` instead of throwing,
so the demonstration can show a rejected booking and carry on. Professional
equality is by identifier (`equals`/`hashCode`), which is how a double booking is
detected even if a different object for the same professional is passed in.
Sorting is `Collections.sort` on whatever list it is given, so the same method
sorts the whole book or a filtered subset.

**The clinic day.** The brief limits appointments to the current day. The clinic
holds an explicit `operatingDay` instead of reading the system clock, so the
demonstration produces the same output every time it is run and the marking does
not depend on which day it is opened.

## Mapping to the six parts

| Part | Where |
| --- | --- |
| 1. Model a health professional | `HealthProfessional.java` |
| 2. Specialise into types | `GeneralPractitioner.java`, `Specialist.java`, `Nurse.java`, `Surgeon.java`, `Dietitian.java` |
| 3. Create and work with objects | `AssignmentOne.main`, first section |
| 4. Appointments | `Patient.java`, `TimeSlot.java`, `Appointment.java` |
| 5. Manage appointments | `Clinic.java`, `ClinicSchedule.java` |
| 6. Demonstrate end to end | `AssignmentOne.main`, second section |
