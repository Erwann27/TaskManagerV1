package Visitor;

import ToDoList.*;

public class EditVisitor implements TaskVisitor {

    public final static String PROPERTY_DESC = "description";

    private final String propertyName;
    private final Object value;

    public EditVisitor(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    public void visit(Task task) {
        task.accept(this);
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        if (propertyName.equals(PROPERTY_DESC)) {
            booleanTask.setDescription((String)value);
        }
    }

    @Override
    public void visitProgressiveTask(ProgressiveTask progressiveTask) {
        if (propertyName.equals(PROPERTY_DESC)) {
            progressiveTask.setDescription((String)value);
        }
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        if (propertyName.equals(PROPERTY_DESC)) {
            complexTask.setDescription((String)value);
        }
    }

    @Override
    public void visitToDoList(ToDoList toDoList) {

    }
}
