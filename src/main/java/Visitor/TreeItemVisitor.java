package Visitor;

import ToDoList.*;
import ToDoList.Task;
import javafx.scene.control.TreeItem;

import java.util.Stack;

public class TreeItemVisitor implements TaskVisitor {

    private final Stack<TreeItem<Task>> stack = new Stack<>();

    public TreeItemVisitor(TreeItem<Task> root) {
        stack.push(root);
    }

    @Override
    public void visitBooleanTask(BooleanTask booleanTask) {
        stack.peek().getChildren().add(new TreeItem<>(booleanTask));
    }

    @Override
    public void visitProgressiveTask(ProgressiveTask progressiveTask) {
        stack.peek().getChildren().add(new TreeItem<>(progressiveTask));
    }

    @Override
    public void visitComplexTask(ComplexTask complexTask) {
        TreeItem<Task> item = new TreeItem<>(complexTask);
        stack.peek().getChildren().add(item);
        stack.push(item);
        for (Task task : complexTask.getSubTasks()) {
            task.accept(this);
        }
        stack.pop();
    }

    @Override
    public void visitToDoList(ToDoList toDoList)  {
        for (Task task : toDoList.getTasks()) {
            task.accept(this);
        }
    }
}
