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
import View.TreeTableViewInitializer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private final ToDoList toDoList;

    public TaskController() {
        toDoList = new ToDoListStd();
        taskFactory = new TaskFactoryStd();
    }

    @FXML
    protected void closeApp() { System.exit(0); }

    @FXML
    protected void loadFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        /*if(returnVal == JFileChooser.APPROVE_OPTION) {
            openXML(chooser.getSelectedFile().getAbsolutePath());
        }*/
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeTableViewInitializer treeTableViewInitializer = new TreeTableViewInitializer(treeTable);
        treeTableViewInitializer.initColumns();
        /*TreeItem<Task> root = new TreeItem<>(
                taskFactory.createProgressiveTask(0.0, "root", null, Priority.MINOR, 12)
        );
        root.setExpanded(true);
        treeTable.setRoot(root);*/
        treeTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

   /* private void openXML(String fileName) {
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
        TableColumn<Task, String> descColumn = new TableColumn<>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }*/

}
