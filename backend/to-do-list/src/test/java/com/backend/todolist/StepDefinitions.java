package com.backend.todolist;


import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {
    @Autowired
    private TaskService service; // Spring will inject this

    @Before // This is a Cucumber hook, not JUnit
    @Given("the task list is empty")
    public void the_task_list_is_empty() {
        // Instead of creating a new service, we'll clear the existing one before each scenario
        service.clearTasks();
    }

    @When("I add a task named {string}")
    public void i_add_a_task_named(String name) {
        service.addTask(name);
    }

    @Then("the task list should contain {int} task")
    public void the_task_list_should_contain_task(Integer count) {
        assertEquals(count, service.getTaskCount());
    }
}
