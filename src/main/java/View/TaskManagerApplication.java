package View;

import ToDoList.ToDoListBuilder;
import ToDoList.Task;
import ToDoList.BooleanTaskStd;
import ToDoList.ToDoListBuilderStd;
import ToDoList.Priority;
import Director.XMLToDoListLoader;
import ToDoList.ToDoList;
import ToDoList.ToDoListStd;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

public class TaskManagerApplication extends Application implements Initializable {

    private ToDoList toDoList;

    @FXML
    private VBox vbox;

    @FXML
    private TreeTableView<Task> treeTable;

    @FXML
    public void initialize(){

    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("task-manager-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Hello!");
        Task task = new BooleanTaskStd(false, "bool", null, Priority.MINOR, 12);
        TreeItem<Task> taskItem = new TreeItem<>();
        taskItem.setValue(task);
        treeTable.setRoot(taskItem);
        treeTable.setShowRoot(true);
        System.out.println(treeTable.getColumns().getFirst().getText());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
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
        TableColumn<Task, String> descColumn = new TableColumn<>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(treeTable);
    }
}
