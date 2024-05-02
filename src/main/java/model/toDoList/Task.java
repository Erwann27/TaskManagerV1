package model.toDoList;

import model.visitor.TaskVisitor;

import java.security.InvalidParameterException;
import java.util.Date;


/**
 * A task is represented by its description, a deadline, a priority, an estimated time and progress.
 * Each property is editable except deadline and estimated time.
 */
public interface Task {

    /**
     * getDescription: returns the description of the task.
     *
     * @return String
     */
    String getDescription();

    /**
     * getDeadline: returns the end date of the task.
     *
     * @return Date
     */
    Date getDeadline();

    /**
     * getPriority: returns the priority level of the task.
     *
     * @return Priority
     */
    Priority getPriority();

    /**
     * getEstimatedTimeInDays: returns estimated time of the task.
     *
     * @return int
     */
    int getEstimatedTimeInDays();

    /**
     * getProgress: returns the progress of the task.
     *
     * @return double
     */
    double getProgress();

    /**
     * getParent: returns the parent of the task.
     *
     * @return ComplexTask
     */
    ComplexTask getParent();

    /**
     * setDescription: sets the new description of the task.
     *
     * @pre description != null && description.length <= 20
     * @throws InvalidParameterException if the precondition is not respected
     * @param description the new description
     */
    void setDescription(String description) throws InvalidParameterException;

    /**
     * setPriority: sets a new priority level for the task.
     *
     * @param priority the new priority level
     */
    void setPriority(Priority priority);


    /**
     * setParent: sets the new parent of the task
     *
     * @param task the new parent of the task
     */
    void setParent(ComplexTask task);

    /**
     * accept: call the correct method of the visitor
     *
     * @param taskVisitor the visitor
     */
    void accept(TaskVisitor taskVisitor);
}
