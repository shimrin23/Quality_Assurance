package com.backend.todolist.bdd;

import com.backend.todolist.entity.Task;
import com.backend.todolist.service.TaskService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class StepDefinitions {
    @Autowired
    private TaskService service; // Spring will inject this

    /**
     * This Cucumber hook runs before each scenario to ensure a clean state.
     */
    @Before
    public void cleanDatabase() {
        service.clearTasks();
    }

    @Given("the task list is empty")
    public void the_task_list_is_empty() {
        // The @Before hook should have already cleared the tasks.
        // This step now serves as an explicit verification.
        org.junit.jupiter.api.Assertions.assertEquals(0, service.getTaskCount());
    }

    @When("I add a task named {string}")
    public void i_add_a_task_named(String name) {
        service.addTask(new Task(name));
    }

    @Then("the task list should contain {int} task")
    public void the_task_list_should_contain_task(Integer count) {
        org.junit.jupiter.api.Assertions.assertEquals(count.longValue(), service.getTaskCount());
    }
}
