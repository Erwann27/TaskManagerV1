package ToDoList;

import ToDoList.BooleanTask;
import ToDoList.ComplexTask;
import ToDoList.Priority;
import ToDoList.ProgressiveTask;

import java.util.Date;

public interface TaskFactory {
    BooleanTask createBooleanTask(String description, Date deadline, Priority priority, int estimatedTime);
    ProgressiveTask createProgressiveTask(String description, Date deadline, Priority priority, int estimatedTime);
    ComplexTask createComplexTask(String description, Priority priority);
}
