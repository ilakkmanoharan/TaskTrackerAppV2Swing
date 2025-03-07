package main.java.com.tasktracker.service;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.java.com.tasktracker.model.Task;

public class TaskManager {
    private List<Task> tasks;
    private static final String FILE_NAME = "tasks.dat";

    public TaskManager() {
        this.tasks = loadTasks();
    }

    public void addTask(String title, String description, String priority, LocalDate dueDate) {
        tasks.add(new Task(title, description, priority, dueDate));
        saveTasks();
    }

    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markCompleted();
            saveTasks();
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
        }
    }

    public List<Task> getTasksSorted() {
        tasks.sort(Comparator.comparing(Task::getDueDate).thenComparing(Task::getPriority));
        return tasks;
    }

    private void saveTasks() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private List<Task> loadTasks() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Task>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}