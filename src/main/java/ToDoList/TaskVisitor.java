package ToDoList;

import java.io.IOException;

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

    /**
     *
     * @param fileName the fileName in which content will be saved
     * @param toDoList the list of tasks saved
     * do action
     */
    void saveFile(String fileName, ToDoList toDoList) throws IOException;
}
