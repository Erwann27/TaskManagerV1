package ToDoList;

import java.security.InvalidParameterException;
import java.util.Date;


public interface Task {

    /**
     *
     * @return the description of the task
     */
    String getDescription();

    /**
     *
     * @return the deadline of the task
     */
    Date getDeadline();

    /**
     *
     * @return the priority of the task
     */
    Priority getPriority();

    /**
     * @return the estimated time in days of the task
     */
    int getEstimatedTimeInDays();

    /**
     * @return progression state of the task
     */
    double getProgress();

    /**
     * @pre description != null && description.length <= 20
     * @throw InvalidArgumentException
     * @param description
     * set description of the task
     */
    void setDescription(String description) throws InvalidParameterException;

    /**
     *
     * @param priority
     * set task priority
     */
    void setPriority(Priority priority);

    /**
     *
     * @param taskVisitor
     * apply taskVisitor operation
     */
    void accept(TaskVisitor taskVisitor);
}
