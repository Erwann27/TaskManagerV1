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
        //createDescColumn();
    }

    private void createDescColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Description");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        treeTableView.getColumns().add(column);
    }
}
