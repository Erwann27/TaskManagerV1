package View;

import ToDoList.Task;
import Visitor.EditVisitor;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class TreeTableViewInitializer {

    private final TreeTableView<Task> treeTableView;

    public TreeTableViewInitializer(TreeTableView<Task> treeTableView) {
        this.treeTableView = treeTableView;
    }

    public void initColumns() {
        createDescColumn();
        createEstimatedTimeColumn();
        createPriorityColumn();
        createProgressionColumn();
        createDeadlineColumn();
    }

    private void createDescColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Description");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        column.setOnEditCommit(newValue -> editValue(
                    newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_DESC)
        );
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        treeTableView.getColumns().add(column);
    }

    private void createEstimatedTimeColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Estimated Time (d.)");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("estimatedTimeInDays"));
        column.setEditable(true);
        treeTableView.getColumns().add(column);
    }

    private void createPriorityColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Priority");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("priority"));
        column.setEditable(true);
        treeTableView.getColumns().add(column);
    }

    private void createProgressionColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Progression");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("progress"));
        column.setEditable(true);
        treeTableView.getColumns().add(column);
    }

    private void createDeadlineColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Deadline");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("deadline"));
        column.setEditable(true);
        treeTableView.getColumns().add(column);
    }

    private void editValue(Task value, String newValue, String property) {
        EditVisitor taskVisitor = new EditVisitor(property, newValue);
        taskVisitor.visit(value);
        treeTableView.refresh();
    }
}
