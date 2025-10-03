package com.backend.todolist.tdd;

import com.backend.todolist.entity.Task;
import com.backend.todolist.repository.TaskRepository;
import com.backend.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//--------------------------------------------------------------
//                  Red Phase


//@Test
//void addTask_increasesTaskCount() {
//    TaskService service = new TaskService();
//    service.addTask("Test Task");
//    assertEquals(1, service.getTaskCount()); // This will fail (TaskService not implemented yet)
//}


//--------------------------------------------------------------
//              Green Phase


//class TaskServiceTest {
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

@ExtendWith(MockitoExtension.class)
class
TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addTask_increasesTaskCount() {
        // Arrange
        Task task = new Task("Test Task");
        when(taskRepository.count()).thenReturn(1L);
        taskService.addTask(task);
        assertEquals(1, taskService.getTaskCount());
        verify(taskRepository).save(task);
    }
}
