module gui {
    requires client;
    requires javafx.controls;
    requires javafx.fxml;

    exports org.gui;
    opens org.gui.controller to javafx.fxml;
}
