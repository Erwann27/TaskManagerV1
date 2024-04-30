package Visitor;

import ToDoList.*;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

public class SubTaskVisitor implements TaskVisitor {

    private final ToDoList toDoList;

    private final Task task;

    private final TreeTableView<Task> treeTable;

    public SubTaskVisitor(ToDoList toDoList, Task task, TreeTableView<Task> tableView) {
        this.task = task;
        this.toDoList = toDoList;
        treeTable = tableView;
    }

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

    @Override
    public void visitToDoList(ToDoList toDoList) {

    }
}
