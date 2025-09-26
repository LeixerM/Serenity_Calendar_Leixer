#Leixer Molina
@CalendarFeature
Feature: Validating the jQuery calendar

  As a calendar user
  I want to validate the functionality when selecting  the date in the jQuery calendar
  To check that the calendar works according to the users selection

  @DataPicker15
  Scenario: Booking an appointment by selecting a date on the current months calendar
    Given the user is on the jQuery calendar page
    When select a specific date in the current month that corresponds to the 15
    Then validates that the text of the specific date determined by the user

  @MonthDifferent
  Scenario: Booking an appointment by selecting a specific date in a different month
    Given the user is on the jQuery calendar page
    When select a specific date in the current month that corresponds to the 10
    Then validates that the text of the specific date determined by the user

  @CampBlocked
  Scenario: Blocked field validation in the jQuery calendar datepicker
    Given the user is on the jQuery calendar page
    When the user enters characters in the calendar field
    Then it is validated that only the date can be selected from the calendar

