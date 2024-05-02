package model.visitor;

import model.toDoList.*;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

/**
 * A visitor which permits to add a sub-task to either the ToDoList or a complex task.
 */
public class SubTaskCreationVisitor implements TaskVisitor {

    private final ToDoList toDoList;

    private final Task task;

    private final TreeTableView<Task> treeTable;

    public SubTaskCreationVisitor(ToDoList toDoList, Task task, TreeTableView<Task> tableView) {
        this.task = task;
        this.toDoList = toDoList;
        treeTable = tableView;
    }

    /**
     * visit: if task is a ComplexTask then this.task is added as a sub-task of task
     *  else it is added to this.toDoList.
     *
     * @param task the selected task
     */
    public void visit(Task task) {
        task.accept(this);
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        toDoList.addTask(task);
        TreeItem<Task> item = new TreeItem<>(task);
        treeTable.getRoot().getChildren().add(item);
    }

    @Override
    public void visitProgressiveTask(ProgressiveTask progressiveTask) {
        toDoList.addTask(task);
        TreeItem<Task> item = new TreeItem<>(task);
        treeTable.getRoot().getChildren().add(item);
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        complexTask.addSubTask(task);
        TreeItem<Task> item = new TreeItem<>(task);
        treeTable.getSelectionModel().getSelectedItem().getChildren().add(item);
    }

}
