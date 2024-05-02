package model.toDoList;

/**
 * A progressive task is a simple task which has a completion percentage editable.
 */
public interface ProgressiveTask extends SimpleTask {

    /**
     * setProgress: sets a new percentage for the task
     * @param progress the new percentage of progress
     */
    void setProgress(double progress);
}
