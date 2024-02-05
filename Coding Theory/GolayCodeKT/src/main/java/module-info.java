module kt.golaycodekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires javafx.swing;

    opens kt.golaycodekt to javafx.fxml;
    exports kt.golaycodekt;
}