package com.backend.todolist.service;

import com.backend.todolist.entity.Task;
import com.backend.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    public long getTaskCount() {
        return taskRepository.count();
    }

    public void clearTasks() {
        taskRepository.deleteAll();
    }
}
