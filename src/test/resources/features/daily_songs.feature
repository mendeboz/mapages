Feature: Daily song recommendations
  The app recommends a stable list of songs for each day.

  Scenario: Show ten songs for a given date
    Given the date is 2026-06-22
    When I ask for the daily songs
    Then I should receive 10 songs
    And the same date should always return the same songs