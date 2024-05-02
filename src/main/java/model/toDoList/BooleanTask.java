package model.toDoList;


public interface BooleanTask extends SimpleTask{

    /**
     * @return the boolean value to indicate if task is finished
     */
    boolean isFinished();

    /**
     *
     * @param finished
     * updates finished parameter of the task
     */
    void setFinished(boolean finished);
}
