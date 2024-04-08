module org.example.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.taskmanager to javafx.fxml;
    exports org.example.taskmanager;
}