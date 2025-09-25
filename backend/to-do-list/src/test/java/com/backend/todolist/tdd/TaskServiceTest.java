package com.backend.todolist.tdd;

import com.backend.todolist.Task;
import com.backend.todolist.TaskRepository;
import com.backend.todolist.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void addTask_shouldSaveTaskToRepository() {
        // Given a new task
        Task task = new Task("Test Task");

        // When the task is added
        taskService.addTask(task);

        // Then the repository's save method should be called with that task
        verify(taskRepository).save(task);
    }

    @Test
    void getTaskCount_shouldReturnCountFromRepository() {
        // Given the repository reports a certain number of tasks
        long expectedCount = 5L;
        when(taskRepository.count()).thenReturn(expectedCount);

        // When the task count is requested
        long actualCount = taskService.getTaskCount();

        // Then the service should return the count from the repository
        assertEquals(expectedCount, actualCount);
    }
}
