package model.toDoList;

import java.util.Date;

/**
 * The builder responsible for creating tasks.
 */
public interface ToDoListBuilder {

    /**
     * startBooleanTask: starts to create a boolean task.
     */
    void startBooleanTask();

    /**
     * startProgressiveTask: starts to create a progressive task.
     */
    void startProgressiveTask();

    /**
     * startComplexTask: starts to create a complex task.
     */
    void startComplexTask();

    /**
     * setFinished: sets a new state to the task currently creating.
     * @param finished the state of the task
     */
    void setFinished(Boolean finished);

    /**
     * setDescription: sets a new description for the task currently creating.
     * @pre description != null && description.length <= 20
     * @throw InvalidArgumentException
     * @param description the description of the task.
     */
    void setDescription(String description);

    /**
     * setEstimatedTimeInDays: sets a new amount of days to the task currently creating.
     * @param days the amount of days of the task
     */
    void setEstimatedTime(Integer days);

    /**
     * setDeadline: sets a new deadline to the task currently creating.
     * @param deadline the end date of the task
     */
    void setDeadline(Date deadline);

    /**
     * setProgress: sets a new percentage completion to the task currently creating.
     * @param progress the percentage of completion of the task
     */
    void setProgress(Double progress);

    /**
     * setPriority: sets a new priority level to the task currently creating.
     * @param priority the priority level of the task
     */
    void setPriority(Priority priority);

    /**
     * createComplexTask: creates a complex task with every information processed before.
     */
    void createComplexTask();

    /**
     * createProgressiveTask: creates a progressive task with every information processed before.
     */
    void createProgressiveTask();

    /**
     * createBooleanTask: creates a boolean task with every information processed before.
     */
    void createBooleanTask();

    /**
     * createToDoList: creates a ToDoList with every task created.
     */
    ToDoList createToDoList();
}
