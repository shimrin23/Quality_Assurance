package com.backend.todolist.service;

import com.backend.todolist.entity.Task;
import com.backend.todolist.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the TaskService class.
 * This class uses Mockito to mock the TaskRepository dependency and ensure that the
 * service layer logic is tested in isolation.
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    /**
     * Tests the addTask() method.
     * It verifies that when a task is added through the service, the repository's save() method is called.
     */
    @Test
    void addTask_shouldSaveTask() {
        // Given
        Task task = new Task("Test Task");

        // When
        taskService.addTask(task);

        // Then
        // Verify that the save method was called on the repository with the correct task
        verify(taskRepository).save(task);
    }

    /**
     * Tests the getTaskCount() method.
     * It ensures that the service correctly returns the number of tasks
     * reported by the repository.
     */
    @Test
    void getTaskCount_shouldReturnTaskCount() {
        // Given
        long expectedCount = 5L;
        // Mock the repository's count() method to return a specific value
        when(taskRepository.count()).thenReturn(expectedCount);

        // When
        long actualCount = taskService.getTaskCount();

        // Then
        // Assert that the count returned by the service matches the expected count
        assertEquals(expectedCount, actualCount);
    }

    /**
     * Tests the clearTasks() method.
     * It verifies that calling clearTasks() on the service results in a call to the
     * repository's deleteAll() method.
     */
    @Test
    void clearTasks_shouldDeleteAllTasks() {
        // When
        taskService.clearTasks();

        // Then
        // Verify that the deleteAll method was called on the repository
        verify(taskRepository).deleteAll();
    }
}
