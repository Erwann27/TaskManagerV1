package model.toDoList;

import model.visitor.TaskVisitor;

import java.security.InvalidParameterException;
import java.util.Date;

/**
 * The implementation of BooleanTask.
 */
public class BooleanTaskStd implements BooleanTask {

    private boolean finished;
    private String description;
    private Date deadline;
    private Priority priority;
    private int estimatedTime;

    private ComplexTask parent;

    public BooleanTaskStd(Boolean finished, String description, Date deadline,
                          Priority priority, int estimatedTime) {
        if (description == null || description.length() > 20) {
            throw new InvalidParameterException("Invalid description");
        }
        this.finished = finished;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
    }

    public ComplexTask getParent() {
        return parent;
    }

    @Override
    public boolean isFinished() {
        return finished;
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
    public double getProgress() {
        return finished ? 100 : 0;
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
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

    public void setParent(ComplexTask parent) {
        this.parent = parent;
    }

    @Override
    public void accept(TaskVisitor taskVisitor) {
        taskVisitor.visitBooleanTask(this);
    }

}
