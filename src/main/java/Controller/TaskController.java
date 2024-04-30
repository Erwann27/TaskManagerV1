package Controller;

import Director.XMLToDoListLoader;
import ToDoList.*;
import View.TreeTableViewInitializer;
import Visitor.SaveXMLVisitor;
import Visitor.SubTaskVisitor;
import Visitor.TreeItemVisitor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    @FXML
    private TreeTableView<Task> treeTable;

    private final TaskFactory taskFactory;

    @FXML
    private ChoiceBox<String> type;

    @FXML
    private ChoiceBox<String> priority;

    @FXML
    private TextField desc;

    @FXML
    private TextField time;

    @FXML
    private DatePicker date;

    private ToDoList toDoList;

    public TaskController() {
        toDoList = new ToDoListStd();
        taskFactory = new TaskFactoryStd();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeTableViewInitializer treeTableViewInitializer = new TreeTableViewInitializer(treeTable);
        treeTableViewInitializer.initColumns();
        TreeItem<Task> root = new TreeItem<>(
                taskFactory.createProgressiveTask(0.0, "To-Do List"
                        , null, null, 0)
        );
        root.setExpanded(true);
        treeTable.setEditable(true);
        treeTable.setRoot(root);
        treeTable.setShowRoot(false);
        treeTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        date.setValue(LocalDate.now());
        type.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals("Complex Task")) {
                date.setDisable(true);
                time.setDisable(true);
            } else {
                date.setDisable(false);
                time.setDisable(false);
            }
        });
    }

    @FXML
    protected void closeApp() { System.exit(0); }

    @FXML
    protected void createTask() {
        String taskType = type.getSelectionModel().getSelectedItem();
        String description = desc.getText();
        Date date = Date.from(this.date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String timeText = time.getText();
        int estimatedTime = 0;
        if (!timeText.isEmpty()) {
            estimatedTime = Integer.parseInt(time.getText());
        }
        Priority priority = Priority.valueOf(this.priority.getValue().toUpperCase());
        Task task = switch (taskType) {
            case "Progressive Task" ->
                    taskFactory.createProgressiveTask(0.0, description, date, priority, estimatedTime);
            case "Boolean Task" -> taskFactory.createBooleanTask(false, description, date, priority, estimatedTime);
            case "Complex Task" -> taskFactory.createComplexTask(description, priority);
            default -> null;
        };
        TreeItem<Task> taskTreeItem = treeTable.getSelectionModel().getSelectedItem();
        if (taskTreeItem != null) {
            Task selectedTask = taskTreeItem.getValue();
            SubTaskVisitor subTaskVisitor = new SubTaskVisitor(toDoList, task, treeTable);
            subTaskVisitor.visit(selectedTask);
        } else {
            toDoList.addTask(task);
            TreeItem<Task> item = new TreeItem<>(task);
            treeTable.getRoot().getChildren().add(item);
        }
        treeTable.refresh();
    }

    @FXML
    protected void deleteTask() {
        TreeItem<Task> taskTreeItem = treeTable.getSelectionModel().getSelectedItem();
        if (taskTreeItem != null) {
            Task selectedTask = taskTreeItem.getValue();
            ComplexTask parent = selectedTask.getParent();
            if (parent == null) {
                toDoList.deleteTask(selectedTask);
                treeTable.getRoot().getChildren().remove(taskTreeItem);
            } else {
                parent.getSubTasks().remove(selectedTask);
                taskTreeItem.getParent().getChildren().remove(taskTreeItem);
            }
        }
    }
    @FXML
    protected void saveFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                SaveXMLVisitor saveXMLVisitor = new SaveXMLVisitor(chooser.getSelectedFile() + ".xml");
                saveXMLVisitor.visitToDoList(toDoList);
            } catch (Exception ex) {
                throw new RuntimeException();
            }
        }
    }

    @FXML
    protected void loadFile() {
        treeTable.getRoot().getChildren().clear();
        toDoList = new ToDoListStd();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            openXML(chooser.getSelectedFile().getAbsolutePath());
        }
    }


    private void openXML(String fileName) {
        ToDoListBuilder builder = new ToDoListBuilderStd();
        try {
            XMLToDoListLoader.load(fileName, builder);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("Error: All tasks must have a description length of 20 max!");
            throw new RuntimeException(e);
        }
        toDoList = builder.createToDoList();
        TreeItemVisitor treeItemVisitor = new TreeItemVisitor(treeTable.getRoot());
        treeItemVisitor.visitToDoList(toDoList);
        treeTable.refresh();
    }

}
