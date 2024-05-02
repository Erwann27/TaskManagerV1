package view;

import model.toDoList.Priority;
import model.toDoList.Task;
import model.visitor.EditVisitor;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.Date;

/**
 * Initializes a tree table view and its columns corresponding to the properties of the tasks.
 */
public class TreeTableViewInitializer {

    public static final Integer MAX_CHAR = 20;

    private final TreeTableView<Task> treeTableView;

    public TreeTableViewInitializer(TreeTableView<Task> treeTableView) {
        this.treeTableView = treeTableView;
    }

    /**
     * initColumns: creates and initializes all the columns of the tree tables.
     */
    public void initColumns() {
        createDescColumn();
        createEstimatedTimeColumn();
        createPriorityColumn();
        createProgressionColumn();
        createDeadlineColumn();
    }

    /**
     * createDescColumn: creates and adds the description column into the tree table.
     */
    private void createDescColumn() {
        TreeTableColumn<Task, String> column = new TreeTableColumn<>("Description");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        column.setOnEditCommit(newValue -> editValue(
                    newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_DESC)
        );
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new StringConverter<>() {
            @Override
            public String toString(String o) {
                if (o.length() > MAX_CHAR) {
                    return o.substring(0, MAX_CHAR);
                }
                return o;
            }

            @Override
            public String fromString(String s) {
                if (s.length() > MAX_CHAR) {
                    return s.substring(0, MAX_CHAR);
                }
                return s;
            }
        }));
        treeTableView.getColumns().add(column);
    }

    /**
     * createEstimatedTimeColumn: creates and adds the estimated time column into the tree table.
     */
    private void createEstimatedTimeColumn() {
        TreeTableColumn<Task, Integer> column = new TreeTableColumn<>("Estimated Time (d.)");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("estimatedTimeInDays"));
        column.setOnEditCommit(newValue -> editValue(
                newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_TIME)
        );
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new CustomIntegerStringConverter()));
        treeTableView.getColumns().add(column);
    }


    /**
     * createPriorityColumn: creates and adds the priority column into the tree table.
     */
    private void createPriorityColumn() {
        TreeTableColumn<Task, Priority> column = new TreeTableColumn<>("Priority");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("priority"));
        column.setOnEditCommit(newValue -> editValue(
                newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_PRIORITY)
        );
        column.setCellFactory(col -> new ComboBoxTreeTableCell<>(new StringConverter<>() {
            @Override
            public String toString(Priority priority) {
                return priority == null ? "" : priority.toString();
            }

            @Override
            public Priority fromString(String s) {
                return Priority.valueOf(s);
            }
        }, FXCollections.observableArrayList(Priority.values())));
        treeTableView.getColumns().add(column);
    }

    /**
     * createProgressionColumn: creates and adds the progression column into the tree table.
     */
    private void createProgressionColumn() {
        TreeTableColumn<Task, Double> column = new TreeTableColumn<>("Progression");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("progress"));
        column.setOnEditCommit(newValue -> editValue(
                newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_PROGRESS)
        );
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new CustomDoubleStringConverter()));
        treeTableView.getColumns().add(column);
    }


    /**
     * createDeadlineColumn: creates and adds the deadline column into the tree table.
     */
    private void createDeadlineColumn() {
        TreeTableColumn<Task, Date> column = new TreeTableColumn<>("Deadline");
        column.setCellValueFactory(new TreeItemPropertyValueFactory<>("deadline"));
        column.setOnEditCommit(newValue -> editValue(
                newValue.getRowValue().getValue(), newValue.getNewValue(), EditVisitor.PROPERTY_DEADLINE)
        );
        column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new CustomDateStringConverter()));
        treeTableView.getColumns().add(column);
    }

    /**
     * editValue: edits the value of the cell.
     *
     * @param value the task modified
     * @param newValue the new value of the cell
     * @param property the property modified corresponding to the column edited
     */
    private void editValue(Task value, Object newValue, String property) {
        EditVisitor taskVisitor = new EditVisitor(property, newValue);
        taskVisitor.visit(value);
        treeTableView.refresh();
    }


    /**
     * An internal class to control user's input
     */
    private class CustomIntegerStringConverter extends IntegerStringConverter {
        private final IntegerStringConverter converter = new IntegerStringConverter();
        @Override
        public String toString(Integer object) {
            try {
                return converter.toString(object);
            } catch (NumberFormatException e) {
                showAlert(e, "Not a valid Integer");
            }
            return null;
        }

        @Override
        public Integer fromString(String string) {
            try {
                Integer result = converter.fromString(string);
                return result < 0 ? 0 : result;
            } catch (NumberFormatException e) {
                showAlert(e, "Not a valid Integer");
            }
            return 0;
        }
    }

    /**
     * An internal class to control user's input
     */
    private class CustomDoubleStringConverter extends DoubleStringConverter {
        private final DoubleStringConverter converter = new DoubleStringConverter();
        @Override
        public String toString(Double object) {
            try {
                return converter.toString(object);
            } catch (NumberFormatException e) {
                showAlert(e, "Not a valid Number");
            }
            return null;
        }

        @Override
        public Double fromString(String string) {
            try {
                Double result = converter.fromString(string);
                if (result < 0.0) {
                    return 0.0;
                }
                return result;
            } catch (NumberFormatException e) {
                showAlert(e, "Not a valid Number");
            }
            return 0.0;
        }
    }

    /**
     * An internal class to control user's input
     */
    private class CustomDateStringConverter extends DateStringConverter {
        private final DateStringConverter converter = new DateStringConverter();
        @Override
        public String toString(Date object) {
            try {
                return converter.toString(object);
            } catch (NumberFormatException e) {
                showAlert(e, "Not a valid Date");
            }
            return null;
        }

        @Override
        public Date fromString(String string) {
            try {
                return converter.fromString(string);
            } catch (RuntimeException e) {
                showAlert(e, "Not a valid Date");
            }
            return new Date();
        }
    }

    /**
     * showAlert: creates a popup window to alert the user why his action failed
     *
     * @param e the exception caught
     * @param msg the message to print
     */
    private void showAlert(Exception e, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(msg);
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeader(null);
        alert.show();
    }
}
