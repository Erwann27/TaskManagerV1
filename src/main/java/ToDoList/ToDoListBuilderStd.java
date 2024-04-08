package ToDoList;

import java.security.InvalidParameterException;
import java.util.*;

public class ToDoListBuilderStd implements ToDoListBuilder {

    private final ToDoList toDoList;
    private final TaskFactory taskFactory;
    private final Queue<String> tasksDescriptions;
    private final Queue<Priority> tasksPriorities;
    private Boolean finished;
    private Date deadline;
    private Double progress;
    private Integer estimatedTime;

    public ToDoListBuilderStd() {
        toDoList = new ToDoListStd();
        taskFactory = new TaskFactoryStd();
        tasksDescriptions = Collections.asLifoQueue(new ArrayDeque<>());
        tasksPriorities = Collections.asLifoQueue(new ArrayDeque<>());
    }

    public void startBooleanTask() {
        startTask();
    }

    
    public void startProgressiveTask() {
        startTask();
    }

    
    public void startComplexTask() {
        startTask();
    }

    
    public void setFinished(Boolean finished) {
        if (finished == null || this.finished != null) {
            throw new InvalidParameterException("parameter can't be null");
        }
        this.finished = finished;
    }

    
    public void setDescription(String description) {
        if (description == null) {
            throw new InvalidParameterException("parameter can't be null");
        }
        tasksDescriptions.poll();
        tasksDescriptions.add(description);
    }

    
    public void setEstimatedTime(Integer days) {
        if (days == null || estimatedTime != null) {
            throw new InvalidParameterException("parameter can't be null");
        }
        estimatedTime = days;
    }

    
    public void setDeadline(Date deadline) {
        if (deadline == null || this.deadline != null) {
            throw new InvalidParameterException("parameter can't be null");
        }
        this.deadline = deadline;
    }

    
    public void setProgress(Double progress) {
        if (progress == null || this.progress != null) {
            throw new InvalidParameterException("parameter can't be null");
        }
        this.progress = progress;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            throw new InvalidParameterException("parameter can't be null");
        }
        tasksPriorities.poll();
        tasksPriorities.add(priority);
    }

    public void createComplexTask() {
        toDoList.addTask(taskFactory.createComplexTask(tasksDescriptions.poll(), tasksPriorities.poll()));
    }


    public void createProgressiveTask() {
        toDoList.addTask(taskFactory.createProgressiveTask(
                tasksDescriptions.poll(), deadline, tasksPriorities.poll(), estimatedTime)
        );
    }


    public void createBooleanTask() {
        toDoList.addTask(taskFactory.createBooleanTask(
                tasksDescriptions.poll(), deadline, tasksPriorities.poll(), estimatedTime)
        );
    }

    public ToDoList createToDoList() {
        return toDoList;
    }


    /**
     * initializes a new simple task
     */
    private void startTask() {
        finished = null;
        deadline = null;
        progress = null;
        estimatedTime = null;
        tasksDescriptions.add("");
        tasksPriorities.add(Priority.MINOR);
    }
}
