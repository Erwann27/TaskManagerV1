package model.toDoList;


/**
 * A boolean task is a simple task with no progression state.
 * It is finished or not.
 */
public interface BooleanTask extends SimpleTask {

    /**
     * isFinished: indicates if the task is finished or not
     * @return boolean
     */
    boolean isFinished();

    /**
     * setFinished: changes the state of the task
     * @param finished the new state of the task
     */
    void setFinished(boolean finished);
}
