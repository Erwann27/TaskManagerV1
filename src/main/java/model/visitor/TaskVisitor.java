package model.visitor;

import model.toDoList.BooleanTask;
import model.toDoList.ComplexTask;
import model.toDoList.ProgressiveTask;

/**
 * TaskVisitor permits its implementors to do operations on all tasks.
 */
public interface TaskVisitor {

    /**
     * visitBooleanTask: the implementor visits a boolean task.
     *
     * @param booleanTask the visited task
     */
    void visitBooleanTask(BooleanTask booleanTask);

    /**
     * visitProgressiveTask: the implementor visits a progressive task.
     *
     * @param progressiveTask the visited task
     */
    void visitProgressiveTask(ProgressiveTask progressiveTask);

    /**
     * visitComplexTask: the implementor visits a complex task.
     *
     * @param complexTask the visited task
     */
    void visitComplexTask(ComplexTask complexTask);
}
