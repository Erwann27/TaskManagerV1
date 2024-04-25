package ToDoList;

import java.security.InvalidParameterException;
import java.util.*;

public class ToDoListBuilderStd implements ToDoListBuilder {

    private final ToDoList toDoList;
    private final TaskFactory taskFactory;
    private final Queue<String> tasksDescriptions;
    private final Queue<Priority> tasksPriorities;
    private final List<List<Task>> tasks;
    private int complexDepth;
    private Boolean finished;
    private Date deadline;
    private Double progress;
    private Integer estimatedTime;

    public ToDoListBuilderStd() {
        toDoList = new ToDoListStd();
        taskFactory = new TaskFactoryStd();
        tasksDescriptions = Collections.asLifoQueue(new ArrayDeque<>());
        tasksPriorities = Collections.asLifoQueue(new ArrayDeque<>());
        tasks = new ArrayList<>();
    }

    public void startBooleanTask() {
        startTask();
    }

    
    public void startProgressiveTask() {
        startTask();
    }

    
    public void startComplexTask() {
        startTask();
        ++complexDepth;
        List<Task> complexDepthList = new ArrayList<>();
        tasks.add(complexDepthList);
    }

    
    public void setFinished(Boolean finished) {
        if (finished == null || this.finished != null) {
            throw new InvalidParameterException("parameter can't be null");
        }
        this.finished = finished;
    }

    
    public void setDescription(String description) {
        if (description == null || description.length() > 20) {
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
        ComplexTask complexTask = taskFactory.createComplexTask(tasksDescriptions.poll(), tasksPriorities.poll());
        List<Task> depthTasks = tasks.get(complexDepth - 1);
        for (Task task : depthTasks) {
            complexTask.addSubTask(task);
        }
        --complexDepth;
        if (complexDepth > 0) {
            depthTasks = tasks.get(complexDepth - 1);
            depthTasks.add(complexTask);
        } else {
            toDoList.addTask(complexTask);
        }
    }


    public void createProgressiveTask() {
        ProgressiveTask progressiveTask = taskFactory.createProgressiveTask(
                progress, tasksDescriptions.poll(), deadline, tasksPriorities.poll(), estimatedTime);
        if (complexDepth > 0) {
            List<Task> depthTasks = tasks.get(complexDepth - 1);
            depthTasks.add(progressiveTask);
        } else {
            toDoList.addTask(progressiveTask);
        }
    }


    public void createBooleanTask() {
        BooleanTask booleanTask = taskFactory.createBooleanTask(
                finished, tasksDescriptions.poll(), deadline, tasksPriorities.poll(), estimatedTime);
        if (complexDepth > 0) {
            List<Task> depthTasks = tasks.get(complexDepth - 1);
            depthTasks.add(booleanTask);
        } else {
            toDoList.addTask(booleanTask);
        }
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
