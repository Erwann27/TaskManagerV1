package model.toDoList;

import model.visitor.TaskVisitor;

import java.security.InvalidParameterException;
import java.util.Date;

/**
 * An implementation for ProgressiveTask
 */
public class ProgressiveTaskStd implements ProgressiveTask {

    private double progress;
    private String description;
    private Date deadline;
    private Priority priority;
    private int estimatedTime;

    private ComplexTask parent;

    public ProgressiveTaskStd(Double progress, String description, Date deadline, Priority priority, int estimatedTime) {
        if (description == null || description.length() > 20) {
            throw new InvalidParameterException("Invalid description");
        }
        this.progress = progress;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
    }

    @Override
    public ComplexTask getParent() {
        return parent;
    }

    @Override
    public double getProgress() {
        return progress;
    }
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getDeadline() {
        return deadline;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int getEstimatedTimeInDays() {
        return estimatedTime;
    }

    @Override
    public void setDescription(String description) {
        if (description == null || description.length() > 20) {
            throw new InvalidParameterException("Invalid description");
        }
        this.description = description;
    }

    @Override
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public void setEstimatedTimeInDays(int days) {
        this.estimatedTime = days;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void setParent(ComplexTask parent) {
        this.parent = parent;
    }

    @Override
    public void accept(TaskVisitor taskVisitor) {
        taskVisitor.visitProgressiveTask(this);
    }

    @Override
    public void setProgress(double progress) {
        this.progress = progress;
    }
}
