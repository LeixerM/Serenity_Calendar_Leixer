@CalendarFeature
Feature: Selecting dates in the jQuery UI datepicker

  As a user booking an appointment
  I want to pick a date from the calendar widget
  So that the date field is filled with exactly the day I chose

  Dates are resolved relative to the day the suite runs, so every example is deterministic.

  Background:
    Given the user is on the jQuery UI datepicker demo

  @SelectDate
  Scenario Outline: Selecting <case>
    When the user selects day "<day>" of the "<month>" month
    Then the date field shows the selected date as MM/dd/yyyy

    Examples:
      | case                                            | month        | day   |
      | today's date                                    | current      | today |
      | a mid-month day in the current month            | current      | 15    |
      | a day in the next month                         | next         | 10    |
      | a day in the previous month                     | previous     | 20    |
      | the last day of the current month (month edge)  | current      | last  |
      | the first day of next January (year boundary)   | next January | 1     |

  @TypeDate
  Scenario: Typing a date moves the calendar to that date
    When the user types "02/29/2028" into the date field
    Then the calendar jumps to that month and highlights that day
    And the date field shows the selected date as MM/dd/yyyy
