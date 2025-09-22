Feature: Add a new task
  As a user
  I want to add a new task
  So that I can track my work

  Scenario: User adds a valid task
    Given the task list is empty
    When I add a task named "Write report"
    Then the task list should contain 1 task
