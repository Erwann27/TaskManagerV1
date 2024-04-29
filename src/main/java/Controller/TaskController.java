package Controller;

import Director.XMLToDoListLoader;
import ToDoList.Task;
import ToDoList.ToDoList;
import ToDoList.ToDoListStd;
import ToDoList.TaskFactory;
import ToDoList.TaskFactoryStd;
import ToDoList.Priority;
import ToDoList.ToDoListBuilder;
import ToDoList.ToDoListBuilderStd;
import View.TaskManagerApplication;
import View.TreeTableViewInitializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    @FXML
    private TreeTableView<Task> treeTable;

    private final TaskFactory taskFactory;

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
        treeTable.setRoot(root);
        treeTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    @FXML
    protected void closeApp() { System.exit(0); }

    @FXML
    protected void createTask() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("create-task-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 475, 225);
        Stage stage = new Stage();
        stage.setTitle("Create task");
        stage.setScene(scene);
        stage.show();
        /*Task newTask = taskFactory.createBooleanTask(false, "hello", null, Priority.MINOR, 12);
        TreeItem<Task> newItem = new TreeItem<>(newTask);
        toDoList.addTask(newTask);
        treeTable.getRoot().getChildren().add(newItem);
        treeTable.refresh();*/
    }
    @FXML
    protected void saveFile() {
        //toDoList.
    }

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
        for (Task task : toDoList.getTasks()) {
            TreeItem<Task> item = new TreeItem<>(task);
            treeTable.getRoot().getChildren().add(item);
        }
        treeTable.refresh();
    }

}
