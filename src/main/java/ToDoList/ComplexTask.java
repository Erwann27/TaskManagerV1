package ToDoList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComplexTask implements Task{

    private Priority priority;
    private String description;
    private final List<Task> subTasks;

    public ComplexTask(String description, Priority priority) {
        this.priority = priority;
        this.description = description;
        subTasks = new ArrayList<>();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getDeadline() {
        if(subTasks.isEmpty()) {
            return null;
        }
        Date deadline = subTasks.getFirst().getDeadline();
        for(Task task : getSubTasks()) {
            if(!(task == this)) {
                Date taskDeadline = task.getDeadline();
                if(deadline.compareTo(taskDeadline) < 0) {
                    deadline = taskDeadline;
                }
            }
        }
        return deadline;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int getEstimatedTimeInDays() {
        int estimatedTime = 0;
        for(Task task : getSubTasks()) {
            if(!(task == this)) {
                estimatedTime += task.getEstimatedTimeInDays();
            }
        }
        return estimatedTime;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    @Override
    public double getProgress() {
        double progressCount = 0;
        int estimatedTimeCount = 0;
        for (Task task : getSubTasks()) {
            if(!(task == this)) {
                int estimatedTime = task.getEstimatedTimeInDays();
                progressCount += task.getProgress() * estimatedTime;
                estimatedTimeCount += estimatedTime;
            }
        }
        if(estimatedTimeCount == 0){
            return 100;
        }
        return progressCount/estimatedTimeCount;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void accept(TaskVisitor taskVisitor) {
        taskVisitor.visitComplexTask(this);
    }

    public void addSubTask(Task task) {
        subTasks.add(task);
    }
}
