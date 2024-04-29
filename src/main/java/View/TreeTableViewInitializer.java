package View;

import ToDoList.Task;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
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
        treeTableView.getColumns().add(column);
    }

    private void createEstimatedTimeColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Estimated Time (d.)");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("estimatedTimeInDays"));
        treeTableView.getColumns().add(column);
    }

    private void createPriorityColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Priority");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("priority"));
        treeTableView.getColumns().add(column);
    }

    private void createProgressionColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Progression");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("progress"));
        treeTableView.getColumns().add(column);
    }

    private void createDeadlineColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Deadline");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("deadline"));
        treeTableView.getColumns().add(column);
    }

}
