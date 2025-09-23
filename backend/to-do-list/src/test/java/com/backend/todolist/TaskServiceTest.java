package com.backend.todolist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

//--------------------------------------------------------------
//                  Red Phase


//@Test
//void addTask_increasesTaskCount() {
//    TaskService service = new TaskService();
//    service.addTask("Test Task");
//    assertEquals(1, service.getTaskCount()); // This will fail (TaskService not implemented yet)
//}
//

//--------------------------------------------------------------
//              Green Phase


//public class TaskServiceTest {
//
//    @Test
//    void addTask_increasesTaskCount() {
//        TaskService service = new TaskService();
//        service.addTask("Test Task");
//        assertEquals(1, service.getTaskCount());
//    }
//}

//--------------------------------------------------------------
//                  Refactor Phase


import org.junit.jupiter.api.BeforeEach;
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
