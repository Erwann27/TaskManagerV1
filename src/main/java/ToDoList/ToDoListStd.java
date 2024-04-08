package ToDoList;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class ToDoListStd implements ToDoList {

    private final List<Task> tasks;

    public ToDoListStd() {
        tasks = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        if (task == null) {
            throw new InvalidParameterException("task must not be null");
        }
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        if (task == null) {
            throw new InvalidParameterException("task must not be null");
        }
        tasks.remove(task);
    }
}
