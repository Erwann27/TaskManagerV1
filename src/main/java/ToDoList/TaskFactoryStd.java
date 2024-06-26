package ToDoList;

import java.util.Date;

public class TaskFactoryStd implements TaskFactory{
    @Override
    public BooleanTask createBooleanTask(Boolean isFinished, String description, Date deadline, Priority priority, int estimatedTime) {
        return new BooleanTaskStd(isFinished, description, deadline, priority, estimatedTime);
    }

    @Override
    public ProgressiveTask createProgressiveTask(Double progress, String description, Date deadline, Priority priority, int estimatedTime) {
        return new ProgressiveTaskStd(progress, description, deadline, priority, estimatedTime);
    }

    @Override
    public ComplexTask createComplexTask(String description, Priority priority) {
        return new ComplexTask(description, priority);
    }
}
