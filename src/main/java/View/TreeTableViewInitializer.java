package View;

import ToDoList.Priority;
import ToDoList.Task;
import Visitor.EditVisitor;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.Date;

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
        TreeTableColumn<Task, Integer> column = new TreeTableColumn<>("Estimated Time (d.)");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("estimatedTimeInDays"));
        column.setOnEditCommit(newValue -> editValue(
                newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_TIME)
        );
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
        treeTableView.getColumns().add(column);
    }

    private void createPriorityColumn() {
        TreeTableColumn<Task, Priority> column = new TreeTableColumn<>("Priority");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("priority"));
        column.setOnEditCommit(newValue -> editValue(
                newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_PRIORITY)
        );
        column.setCellFactory(col -> new ComboBoxTreeTableCell<>());
        treeTableView.getColumns().add(column);
    }

    private void createProgressionColumn() {
        TreeTableColumn<Task, Double> column = new TreeTableColumn<>("Progression");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("progress"));
        column.setOnEditCommit(newValue -> editValue(
                newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_PROGRESS)
        );
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new DoubleStringConverter()));
        treeTableView.getColumns().add(column);
    }

    private void createDeadlineColumn() {
        TreeTableColumn<Task, Date> column = new TreeTableColumn<>("Deadline");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("deadline"));
        column.setOnEditCommit(newValue -> editValue(
                newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_DEADLINE)
        );
        //column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        treeTableView.getColumns().add(column);
    }

    private void editValue(Task value, Object newValue, String property) {
        EditVisitor taskVisitor = new EditVisitor(property, newValue);
        taskVisitor.visit(value);
        treeTableView.refresh();
    }
}
