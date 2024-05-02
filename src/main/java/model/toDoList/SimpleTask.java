package model.toDoList;

import java.util.Date;

public interface SimpleTask extends Task{

    /**
     *
     * @param deadline
     * set deadline of the task
     */
    void setDeadline(Date deadline);

    /**
     * @param days
     * set estimated time in days
     */
    void setEstimatedTimeInDays(int days);
}
