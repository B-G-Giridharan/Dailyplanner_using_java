package com.example.todo.controller;

import com.example.todo.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TaskController Tests")
class TaskControllerTest {

    private TaskController taskController;

    @BeforeEach
    void setUp() {
        taskController = new TaskController();
    }

    @Test
    @DisplayName("Should return empty list for user with no tasks")
    void testGetTasksNoTasks() {
        List<Task> tasks = taskController.getTasks("newuser");

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    @DisplayName("Should return tasks for user with tasks")
    void testGetTasksWithTasks() {
        String user = "testuser";
        Task task = new Task();
        task.setTitle("Task 1");
        task.setTime("10:00");

        taskController.addTask(user, task);
        List<Task> tasks = taskController.getTasks(user);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
    }

    @Test
    @DisplayName("Should successfully add a task")
    void testAddTaskSuccess() {
        String user = "adduser";
        Task task = new Task();
        task.setTitle("New Task");
        task.setTime("14:30");

        List<Task> result = taskController.addTask(user, task);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("New Task", result.get(0).getTitle());
        assertEquals("14:30", result.get(0).getTime());
        assertNotNull(result.get(0).getId());
    }

    @Test
    @DisplayName("Should add multiple tasks for same user")
    void testAddMultipleTasks() {
        String user = "multiuser";
        
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setTime("09:00");
        
        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setTime("15:00");

        taskController.addTask(user, task1);
        List<Task> result = taskController.addTask(user, task2);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should successfully delete a task")
    void testDeleteTaskSuccess() {
        String user = "deleteuser";
        Task task = new Task();
        task.setTitle("Task to Delete");
        task.setTime("10:00");

        List<Task> addedTasks = taskController.addTask(user, task);
        long taskId = addedTasks.get(0).getId();

        taskController.deleteTask(user, (int) taskId);
        List<Task> remainingTasks = taskController.getTasks(user);

        assertTrue(remainingTasks.isEmpty());
    }

    @Test
    @DisplayName("Should handle delete task for non-existent user")
    void testDeleteTaskNonExistentUser() {
        // Should not throw exception
        assertDoesNotThrow(() -> taskController.deleteTask("nonexistent", 1));
    }

    @Test
    @DisplayName("Should not throw exception when deleting non-existent task")
    void testDeleteNonExistentTask() {
        String user = "userwithtask";
        Task task = new Task();
        task.setTitle("Task");
        task.setTime("10:00");

        taskController.addTask(user, task);

        // Should not throw exception
        assertDoesNotThrow(() -> taskController.deleteTask(user, 999));
        
        // Original task should still exist
        List<Task> tasks = taskController.getTasks(user);
        assertEquals(1, tasks.size());
    }

    @Test
    @DisplayName("Should correctly assign IDs to tasks in sequence")
    void testTaskIdSequence() {
        String user = "iduser";
        Task task1 = new Task();
        task1.setTitle("Task 1");
        
        Task task2 = new Task();
        task2.setTitle("Task 2");

        List<Task> after1 = taskController.addTask(user, task1);
        List<Task> after2 = taskController.addTask(user, task2);

        long id1 = after1.get(0).getId();
        long id2 = after2.get(1).getId();

        assertEquals(id1 + 1, id2);
    }
}
