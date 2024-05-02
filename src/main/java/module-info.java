module org.example.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens model.toDoList to javafx.base;
    opens view to javafx.fxml;
    opens controller to javafx.fxml;
    exports model.toDoList;
    exports controller;
    exports view;
    exports model.main;
    exports model.visitor;
    exports model.director;
    opens model.visitor to javafx.base;
}