package model.visitor;

import model.toDoList.*;
import javafx.scene.control.TreeItem;

import java.util.Stack;

/**
 * A visitor which permits adding rows to the tree table and keeping the good hierarchy.
 */
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

    /**
     * visitToDoList: visits every task contained into the ToDoList.
     *
     * @param toDoList to be displayed
     */
    public void visitToDoList(ToDoList toDoList)  {
        for (Task task : toDoList.getTasks()) {
            task.accept(this);
        }
    }
}
