package ToDoList;

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
     * do action
     */
    void saveFile();
}
