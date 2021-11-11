package sample.model;

import java.sql.Timestamp;

public class Task {
    private int userId;
    private int taskId;
    private Timestamp datecreated;
    private String description;
    private int priority;
    private String task;

    public Task() {

    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Task(Timestamp datecreated, String description, int priority, String task) {
        this.datecreated = datecreated;
        this.description = description;
        this.priority = priority;
        this.task = task;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Timestamp datecreated) {
        this.datecreated = datecreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
