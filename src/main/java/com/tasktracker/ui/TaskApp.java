package main.java.com.tasktracker.ui;
import javax.swing.*;

import main.java.com.tasktracker.model.Task;
import main.java.com.tasktracker.service.TaskManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class TaskApp {
    private TaskManager taskManager;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;

    public TaskApp() {
        taskManager = new TaskManager();
        taskListModel = new DefaultListModel<>();
        updateTaskList();

        JFrame frame = new JFrame("Task Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        taskList = new JList<>(taskListModel);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JTextField titleField = new JTextField();
        JTextField descField = new JTextField();
        JTextField priorityField = new JTextField();
        JTextField dueDateField = new JTextField();
        JButton addButton = new JButton("Add Task");
        JButton completeButton = new JButton("Mark Completed");
        JButton deleteButton = new JButton("Delete Task");

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Priority (High/Medium/Low):"));
        panel.add(priorityField);
        panel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        panel.add(dueDateField);
        panel.add(addButton);
        panel.add(completeButton);
        panel.add(deleteButton);

        frame.add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String desc = descField.getText();
            String priority = priorityField.getText();
            LocalDate dueDate = LocalDate.parse(dueDateField.getText());
            taskManager.addTask(title, desc, priority, dueDate);
            updateTaskList();
        });

        completeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskManager.markTaskCompleted(selectedIndex);
                updateTaskList();
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskManager.deleteTask(selectedIndex);
                updateTaskList();
            }
        });

        frame.setVisible(true);
    }

    private void updateTaskList() {
        taskListModel.clear();
        List<Task> tasks = taskManager.getTasksSorted();
        for (Task task : tasks) {
            taskListModel.addElement(task.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskApp::new);
    }
}