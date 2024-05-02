package model.toDoList;

import java.util.Date;

/**
 * A factory to instantiate tasks.
 */
public interface TaskFactory {

    /**
     * createBooleanTask: instantiates a boolean task.
     *
     * @param isFinished the state of the task
     * @param description the description of the task
     * @param deadline the end date of the task
     * @param priority the priority level of the task
     * @param estimatedTime the amount of days the task is going to take
     * @return BooleanTask
     */
    BooleanTask createBooleanTask(Boolean isFinished, String description, Date deadline,
                                  Priority priority, int estimatedTime);

    /**
     * createProgressiveTask: instantiates a progressive task.
     *
     * @param progress the percentage completed of the task
     * @param description the description of the task
     * @param deadline the end date of the task
     * @param priority the priority level of the task
     * @param estimatedTime the amount of days the task is going to take
     * @return ProgressiveTask
     */
    ProgressiveTask createProgressiveTask(Double progress, String description, Date deadline,
                                          Priority priority, int estimatedTime);

    /**
     * createComplexTask: instantiates a complex task.
     *
     * @param description the description of the task
     * @param priority the priority level of the task
     * @return ComplexTask
     */
    ComplexTask createComplexTask(String description, Priority priority);
}
