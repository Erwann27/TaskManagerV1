package ToDoList;

import ToDoList.BooleanTask;
import ToDoList.ComplexTask;
import ToDoList.Priority;
import ToDoList.ProgressiveTask;

import java.util.Date;

public interface TaskFactory {
    BooleanTask createBooleanTask(Boolean isFinished, String description, Date deadline, Priority priority, int estimatedTime);
    ProgressiveTask createProgressiveTask(Double progress, String description, Date deadline, Priority priority, int estimatedTime);
    ComplexTask createComplexTask(String description, Priority priority);
}
