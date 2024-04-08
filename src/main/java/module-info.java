module org.example.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens org.example.taskmanager to javafx.fxml;
    exports org.example.taskmanager;
}