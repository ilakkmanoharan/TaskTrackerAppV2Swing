package main.java.com.tasktracker.model;
import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private String title;
    private String description;
    private String priority;
    private LocalDate dueDate;
    private boolean isCompleted;

    public Task(String title, String description, String priority, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[✓] " : "[ ] ") + title + " (" + priority + ") - Due: " + dueDate;
    }

    public String getTitle() { return title; }
    public String getPriority() { return priority; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isCompleted() { return isCompleted; }
}