package model.visitor;

import model.toDoList.*;

import java.util.Date;

/**
 * A visitor which permits to edit tasks.
 */
public class EditVisitor implements TaskVisitor {

    public final static String PROPERTY_DESC = "description";
    public final static String PROPERTY_TIME = "time";
    public final static String PROPERTY_PRIORITY = "priority";
    public final static String PROPERTY_DEADLINE = "deadline";
    public final static String PROPERTY_PROGRESS = "progress";

    private final String propertyName;
    private final Object value;

    public EditVisitor(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    /**
     * visit: edits the task.
     * @param task the task visited
     */
    public void visit(Task task) {
        task.accept(this);
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        switch (propertyName) {
            case PROPERTY_DESC -> booleanTask.setDescription((String) value);
            case PROPERTY_TIME -> booleanTask.setEstimatedTimeInDays((Integer) value);
            case PROPERTY_PROGRESS -> booleanTask.setFinished((Double) value != 0);
            case PROPERTY_PRIORITY -> booleanTask.setPriority((Priority) value);
            case PROPERTY_DEADLINE -> booleanTask.setDeadline((Date) value);
        }
     }

    @Override
    public void visitProgressiveTask(ProgressiveTask progressiveTask) {
        switch (propertyName) {
            case PROPERTY_DESC -> progressiveTask.setDescription((String) value);
            case PROPERTY_TIME -> progressiveTask.setEstimatedTimeInDays((Integer) value);
            case PROPERTY_PROGRESS -> progressiveTask.setProgress((Double) value);
            case PROPERTY_PRIORITY -> progressiveTask.setPriority((Priority) value);
            case PROPERTY_DEADLINE -> progressiveTask.setDeadline((Date) value);
        }
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        switch (propertyName) {
            case PROPERTY_DESC -> complexTask.setDescription((String) value);
            case PROPERTY_PRIORITY -> complexTask.setPriority((Priority) value);
        }
    }
}
