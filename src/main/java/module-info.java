module com.example.musicsorter {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.musicsorter.vista to javafx.fxml;
    exports com.example.musicsorter.vista;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.musicsorter to javafx.fxml;
    exports com.example.musicsorter;
}