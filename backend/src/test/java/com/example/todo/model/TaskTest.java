package com.example.todo.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Task Model Tests")
class TaskTest {

    @Test
    @DisplayName("Should create Task with default constructor")
    void testTaskDefaultConstructor() {
        Task task = new Task();
        
        assertNotNull(task);
    }

    @Test
    @DisplayName("Should set and get task id")
    void testTaskId() {
        Task task = new Task();
        task.setId(1L);
        
        assertEquals(1L, task.getId());
    }

    @Test
    @DisplayName("Should set and get task title")
    void testTaskTitle() {
        Task task = new Task();
        task.setTitle("Buy Groceries");
        
        assertEquals("Buy Groceries", task.getTitle());
    }

    @Test
    @DisplayName("Should set and get task time")
    void testTaskTime() {
        Task task = new Task();
        task.setTime("14:30");
        
        assertEquals("14:30", task.getTime());
    }

    @Test
    @DisplayName("Should set and get all task properties")
    void testTaskAllProperties() {
        Task task = new Task();
        task.setId(42L);
        task.setTitle("Complete Project");
        task.setTime("18:00");
        
        assertEquals(42L, task.getId());
        assertEquals("Complete Project", task.getTitle());
        assertEquals("18:00", task.getTime());
    }

    @Test
    @DisplayName("Should handle null title")
    void testTaskNullTitle() {
        Task task = new Task();
        task.setTitle(null);
        
        assertNull(task.getTitle());
    }

    @Test
    @DisplayName("Should handle null time")
    void testTaskNullTime() {
        Task task = new Task();
        task.setTime(null);
        
        assertNull(task.getTime());
    }

    @Test
    @DisplayName("Should handle large task id")
    void testTaskLargeId() {
        Task task = new Task();
        task.setId(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, task.getId());
    }
}
