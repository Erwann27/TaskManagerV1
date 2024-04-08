package ToDoList;

import java.util.Date;

public class ProgressiveTaskStd implements ProgressiveTask{

    private double progress;
    private String description;
    private Date deadline;
    private Priority priority;
    private int estimatedTime;

    public ProgressiveTaskStd(String description, Date deadline, Priority priority, int estimatedTime) {
        progress = 0;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
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
    public void setProgress(double progress) {
        this.progress = progress;
    }
}
