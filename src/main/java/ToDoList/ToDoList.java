package ToDoList;

import java.util.List;

public interface ToDoList {

    /**
     * returns every task contained in the list
     */
    List<Task> getTasks();

    /**
     * @param task
     * adds a task to the TodoList
     */
    void addTask(Task task);

    /**
     * @param task
     * deletes a task from the ToDoList
     */
    void deleteTask(Task task);
}
