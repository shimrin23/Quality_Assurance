package com.backend.todolist;
import org.junit.jupiter.api.Test;
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

class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        // This ensures each test starts with a new, clean TaskService instance.
        service = new TaskService();
    }

    @Test
    void addTask_increasesTaskCount() {
        service.addTask("Test Task");
        assertEquals(1, service.getTaskCount());
    }

    @Test
    void addTask_emptyName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.addTask(""));
    }
}
