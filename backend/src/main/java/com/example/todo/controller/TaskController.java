package com.example.todo.controller;

import com.example.todo.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    private Map<String, List<Task>> userTasks = new HashMap<>();
    private AtomicLong counter = new AtomicLong(1);

    @GetMapping("/{user}")
    public List<Task> getTasks(@PathVariable String user) {
        System.out.println("GET tasks for user: " + user);
        return userTasks.getOrDefault(user, new ArrayList<>());
    }

    @PostMapping("/{user}")
    public List<Task> addTask(@PathVariable String user,
    @RequestBody Task task) {
        System.out.println("ADD TASK: " + task.getTitle());
        task.setId(counter.getAndIncrement());
        userTasks.computeIfAbsent(user, k -> new ArrayList<>()).add(task);
        return userTasks.get(user);
    }

    @DeleteMapping("/{user}/{id}")
    public void deleteTask(@PathVariable String user, @PathVariable int id) {
        List<Task> list = userTasks.get(user);
        if (list != null) {
            list.removeIf(task -> task.getId() == id);
        }
    }

}
