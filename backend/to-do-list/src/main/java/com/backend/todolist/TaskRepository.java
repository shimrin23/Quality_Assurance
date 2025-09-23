package com.backend.todolist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Task} entity.
 * Provides CRUD (Create, Read, Update, Delete) operations for tasks.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}