package ToDoList;

import java.util.Date;

public class BooleanTaskStd implements BooleanTask{

    private boolean finished;
    private String description;
    private Date deadline;
    private Priority priority;
    private int estimatedTime;

    public BooleanTaskStd(String description, Date deadline,
                          Priority priority, int estimatedTime) {
        this.finished = false;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
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
}
