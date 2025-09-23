package com.backend.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing tasks.
 * This class contains the business logic for task operations and interacts with the repository.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task) {
        // The @NotBlank validation on the entity handles the check for an empty name
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public long getTaskCount() {
        return taskRepository.count();
    }

    /**
     * Helper method for tests to clear the task list.
     */
    public void clearTasks() {
        taskRepository.deleteAll();
    }
}
