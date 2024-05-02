package model.visitor;

import model.toDoList.BooleanTask;
import model.toDoList.ComplexTask;
import model.toDoList.ProgressiveTask;

public interface TaskVisitor {

    /**
     * @param booleanTask
     * do action on boolean task
     */
    void visitBooleanTask(BooleanTask booleanTask);

    /**
     * @param progressiveTask
     * do action on progressive task
     */
    void visitProgressiveTask(ProgressiveTask progressiveTask);

    /**
     * @param complexTask
     * do action on complex task
     */
    void visitComplexTask(ComplexTask complexTask);
}
