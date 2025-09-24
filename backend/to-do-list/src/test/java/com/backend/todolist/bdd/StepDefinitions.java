package com.backend.todolist.bdd;

import com.backend.todolist.Task;
import com.backend.todolist.TaskService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        service.addTask(new Task(name));
    }

    @Then("the task list should contain {int} task")
    public void the_task_list_should_contain_task(Integer count) {
        assertEquals(count.longValue(), service.getTaskCount());
    }
}
