module org.example.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens ToDoList to javafx.base;
    opens View to javafx.fxml;
    opens Controller to javafx.fxml;
    exports ToDoList;
    exports Controller;
    exports View;
    exports main;
}