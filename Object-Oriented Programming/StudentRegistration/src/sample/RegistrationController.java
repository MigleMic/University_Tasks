package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable, Controllers {

    private final int groups = 5;

    @FXML
    private TextField codeField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField ageField;

    @FXML
    private ChoiceBox<String> groupChoice;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;

    @FXML
    private Label codeError;

    @FXML
    private Label nameError;

    @FXML
    private Label surnameError;

    @FXML
    private Label ageError;

    @FXML
    private Label groupError;

    @FXML
    private Label passwordError;


    public ArrayList<Student> studentArrayList = new ArrayList<>();

    @FXML
    void saveButtonOnAction(ActionEvent actionEvent) throws IOException {
        System.out.println(studentArrayList.size());
        readFromFile();

        Validation validator = new Validation();

        boolean validationSuccessful = validator.validateInput(codeField, nameField, surnameField, ageField,
                groupChoice, passwordField, codeError, nameError,
                surnameError, ageError, groupError, passwordError);

        if (validationSuccessful) {
            boolean specialCode = validCode(codeField.getText());
            if (specialCode) {
                Student student = new Student();
                student.setCode(codeField.getText());
                student.setName(nameField.getText());
                student.setSurname(surnameField.getText());
                student.setAge(Integer.parseInt(ageField.getText()));
                student.setGroup(Integer.parseInt(groupChoice.getValue()));
                student.setPassword(passwordField.getText());
                student.attendanceMap.clear();
                studentArrayList.add(student);
                WorkingWithFile filework = new WorkingWithFile();
                filework.writeToFile(studentArrayList);
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    boolean validCode(String code) {
        Student student = new Student();
        for (Student student1 : studentArrayList) {
            if (code.equals(student1.code.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> groupList = setGroups();
        groupChoice.setItems(groupList);
    }

    ObservableList<String> setGroups() {
        ObservableList<String> listOfGroups = FXCollections.observableArrayList();
        for (int i = 0; i < groups; ++i) {
            listOfGroups.add(new Integer(i + 1).toString());
        }
        return listOfGroups;
    }

    @Override
    public void readFromFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("studentai.csv"));
        String row;
        studentArrayList.clear();

        while ((row = bufferedReader.readLine()) != null) {
            Student student = new Student();
            String[] values = row.split(";");
            student.setCode(values[0]);
            student.setName(values[1]);
            student.setSurname(values[2]);
            student.setAge(Integer.parseInt(values[3]));
            student.setGroup(Integer.parseInt(values[4]));
            student.setPassword(values[5]);
            ArrayList<String> dates = new ArrayList<>();

            for (int i = 6; i < values.length; ++i) {
                String date = values[i];
                student.attendanceMap.put(values[i], values[i + 1]);
                i++;
            }
            studentArrayList.add(student);
        }
        bufferedReader.close();
    }
}