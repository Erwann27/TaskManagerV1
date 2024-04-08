package ToDoList;

import java.util.Date;

public class TaskFactoryStd implements TaskFactory{
    @Override
    public BooleanTask createBooleanTask(String description, Date deadline, Priority priority, int estimatedTime) {
        return new BooleanTaskStd(description, deadline, priority, estimatedTime);
    }

    @Override
    public ProgressiveTask createProgressiveTask(String description, Date deadline, Priority priority, int estimatedTime) {
        return new ProgressiveTaskStd(description, deadline, priority, estimatedTime);
    }

    @Override
    public ComplexTask createComplexTask(String description, Priority priority) {
        return new ComplexTask(description, priority);
    }
}
