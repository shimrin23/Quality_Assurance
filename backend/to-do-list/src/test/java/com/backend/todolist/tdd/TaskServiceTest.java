package com.backend.todolist.tdd;

import com.backend.todolist.Task;
import com.backend.todolist.TaskRepository;
import com.backend.todolist.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        // Mockito initializes the mocks and injects them before each test.
    }

    @Test
    void addTask_increasesTaskCount() {
        Task task = new Task("Test Task");
        when(taskRepository.count()).thenReturn(1L);
        taskService.addTask(task);
        assertEquals(1, taskService.getTaskCount());
    }
}
