package ToDoList;

import java.util.Date;

public interface ToDoListBuilder {

    /**
     * starts a BooleanTask element
     */
    void startBooleanTask();

    /**
     * starts a ProgressiveTask element
     */
    void startProgressiveTask();

    /**
     * starts a ComplexTask element
     */
    void startComplexTask();

    /**
     * @param finished
     * indicates if the task is finished
     */
    void setFinished(Boolean finished);

    /**
     * @pre description != null && description.length <= 20
     * @throw InvalidArgumentException
     * @param description
     * set description of the task
     */
    void setDescription(String description);

    /**
     * @param days
     * sets the estimated time of the task
     */
    void setEstimatedTime(Integer days);

    /**
     * @param deadline
     * sets the deadline of the task
     */
    void setDeadline(Date deadline);

    /**
     * @param progress
     * sets the progress of the task
     */
    void setProgress(Double progress);

    /**
     * @param priority
     * sets the priority of the task
     */
    void setPriority(Priority priority);

    /**
     * creates a complex task
     */
    void createComplexTask();

    /**
     * creates a progressive task
     */
    void createProgressiveTask();

    /**
     * creates a boolean task
     */
    void createBooleanTask();

    /**
     * creates the ToDoList
     */
    ToDoList createToDoList();
}
