package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OpeningController implements Initializable {
    @FXML
    public Label titleLabel;

    @FXML
    private Button newUserButton;

    @FXML
    private Button attendanceButton;

    @FXML
    private Button cancelButton;

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void newUserButtonOnAction(ActionEvent e) {
        createAccount();
    }

    public void attendanceButtonOnAction(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("attendance.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void createAccount() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("regsitration.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void monthlyAttendanceButtonOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("filterAttendance.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}