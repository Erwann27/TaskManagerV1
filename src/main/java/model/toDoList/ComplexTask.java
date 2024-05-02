package model.toDoList;

import model.visitor.TaskVisitor;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A complex task is a task containing other tasks.
 */
public class ComplexTask implements Task {

    private Priority priority;
    private String description;

    private ComplexTask parent;

    private final List<Task> subTasks;

    public ComplexTask(String description, Priority priority) {
        if (description == null || description.length() > 20) {
            throw new InvalidParameterException("Invalid description");
        }
        this.priority = priority;
        this.description = description;
        subTasks = new ArrayList<>();
    }

    @Override
    public ComplexTask getParent() {
        return parent;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Date getDeadline() {
        if(subTasks.isEmpty()) {
            return new Date();
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

    /**
     * getSubTasks: returns every task contained in the complex task
     * @return List<Task>
     */
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
        if (description == null || description.length() > 20) {
            throw new InvalidParameterException("Invalid description");
        }
        this.description = description;
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
        taskVisitor.visitComplexTask(this);
    }

    /**
     * addSubTask: adds a new task to the complex task
     *
     * @param task the new task added
     * @post task.getParent() == this
     * @post this.getSubTasks().contains(task)
     */
    public void addSubTask(Task task) {
        subTasks.add(task);
        task.setParent(this);
    }

    /**
     * removeSubTask: removes the task from the complex task
     *
     * @param task the task removed
     * @post task.getParent() == null
     * @post !this.getSubTasks().contains(task)
     */
    public void removeSubTask(Task task) {
        task.setParent(null);
        subTasks.remove(task);
    }
}
