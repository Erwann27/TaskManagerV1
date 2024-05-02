package controller;

import model.director.XMLToDoListLoader;
import model.toDoList.Task;
import model.toDoList.TaskFactory;
import model.toDoList.ToDoListStd;
import model.toDoList.*;
import view.TreeTableViewInitializer;
import model.visitor.SaveXMLVisitor;
import model.visitor.SubTaskCreationVisitor;
import model.visitor.TreeItemVisitor;
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

/**
 * The controller of the application.
 * Manages all the actions from the user
 */
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


    /**
     * initialize: initializes the tree table, its columns and the tasks creation menu.
     */
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

    /**
     * closeApp: closes the graphical window.
     */
    @FXML
    protected void closeApp() { System.exit(0); }


    /**
     * createTask: creates a new task and adds it the ToDoList.
     * If the selected task is a complex task then the new task is added as a child of the complex one.
     * Else the new task is added as a child of the ToDoList.
     */
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
            SubTaskCreationVisitor subTaskCreationVisitor = new SubTaskCreationVisitor(toDoList, task, treeTable);
            subTaskCreationVisitor.visit(selectedTask);
        } else {
            toDoList.addTask(task);
            TreeItem<Task> item = new TreeItem<>(task);
            treeTable.getRoot().getChildren().add(item);
        }
        treeTable.refresh();
    }

    /**
     * deleteTask: if a task is selected then it is deleted from the ToDoList.
     */
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
                parent.removeSubTask(selectedTask);
                taskTreeItem.getParent().getChildren().remove(taskTreeItem);
            }
        }
    }

    /**
     * saveFile: saves the current ToDoList as an XML file into user's selected destination.
     */
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

    /**
     * loadFile: tries to load the user's selected file and replace current ToDoList's content.
     */
    @FXML
    protected void loadFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            openXML(chooser.getSelectedFile().getAbsolutePath());
        }
    }


    /**
     * openXML: tries to read the selected XML file. On success, the current ToDoList is
     *  replaced by the content of the file.
     *
     * @param filePath the absolute path of the XML file chosen.
     * @throws RuntimeException if an error occurred or if a task's description exceeds 20 characters
     */
    private void openXML(String filePath) {
        ToDoListBuilder builder = new ToDoListBuilderStd();
        try {
            XMLToDoListLoader.load(filePath, builder);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            System.out.println("Error: All tasks must have a description length of 20 max!");
            throw new RuntimeException(e);
        }
        treeTable.getRoot().getChildren().clear();
        toDoList = builder.createToDoList();
        TreeItemVisitor treeItemVisitor = new TreeItemVisitor(treeTable.getRoot());
        treeItemVisitor.visitToDoList(toDoList);
        treeTable.refresh();
    }

}
