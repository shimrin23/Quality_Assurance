package com.backend.todolist;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final List<String> tasks = new ArrayList<>();

    public void addTask(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be empty.");
        }
        tasks.add(name); // Minimal implementation
    }

    public int getTaskCount() {
        return tasks.size();
    }
    
    /**
     * Helper method for tests to clear the task list.
     */
    public void clearTasks() {
        tasks.clear();
    }
}
