module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires fontawesomefx;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}