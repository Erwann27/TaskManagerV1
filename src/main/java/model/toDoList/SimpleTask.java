package model.toDoList;

import java.util.Date;

/**
 * A simple task is a task which has deadline and estimated time properties editable.
 */
public interface SimpleTask extends Task {

    /**
     * setDeadline: sets the new deadline of the task
     *
     * @param deadline the new deadline of the task
     */
    void setDeadline(Date deadline);

    /**
     * setEstimatedTimeInDays: sets the new estimated time of the task
     *
     * @param days the new amount of days for the task
     */
    void setEstimatedTimeInDays(int days);
}
