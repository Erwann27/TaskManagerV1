package model.toDoList;

import java.util.List;

/**
 * An abstraction representing a ToDoList.
 * A ToDoList is a list of tasks.
 */
public interface ToDoList {

    /**
     * getTasks: returns a list of every task contained in the ToDoList
     * returns List<Task>
     */
    List<Task> getTasks();

    /**
     * addTask: adds a task to the TodoList
     *
     * @param task the new Task
     */
    void addTask(Task task);

    /**
     * deleteTask: deletes a task from the toDoList
     *
     * @param task the task to be removed
     */
    void deleteTask(Task task);
}
