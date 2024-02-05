package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AttendanceController implements Initializable, Controllers {
    @FXML
    private ChoiceBox<String> chooseStudentBox;

    @FXML
    private ChoiceBox<String> chooseSubjectBox;

    @FXML
    private DatePicker dateChooser;

    @FXML
    private Button leaveButton;

    @FXML
    private Button addButton;

    public ArrayList<Student> studentArrayList = new ArrayList<>();

    public void addButtonOnAction(ActionEvent actionEvent) throws IOException {
        String date = dateChooser.getValue().toString();
        StudentAttendance(date);
        WorkingWithFile workingWithFile = new WorkingWithFile();
        workingWithFile.writeToFile(studentArrayList);
    }

    void StudentAttendance(String choosenDate) {
        String[] values;
        values = chooseStudentBox.getValue().split(" - ");

        for (Student student : studentArrayList) {
            if (values[0].equals(student.getCode())) {
                boolean date = true;

                if (student.attendanceMap.isEmpty()) {
                    student.attendanceMap.put(chooseSubjectBox.getValue(), choosenDate);
                } else {
                    student.printStudent();
                }

                for (String string : student.attendanceMap.keySet()) {
                    if (string.equals(chooseSubjectBox.getValue())) {
                        List<String> dates = (List<String>) student.attendanceMap.get(string);
                        for (String s : dates) {
                            if (s.equals(choosenDate)) {
                                date = false;
                            }
                        }
                        if (date) {
                            student.attendanceMap.put(chooseSubjectBox.getValue(), choosenDate);
                        }
                    } else {
                        student.attendanceMap.put(chooseSubjectBox.getValue(), choosenDate);
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readFromFile();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        initializeChosenPerson();
        try {
            initializeChosenSubject();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    void initializeChosenPerson() {
        ObservableList<String> students = FXCollections.observableArrayList();

        for (Student student : studentArrayList) {
            String string = student.getCode() + " - " + student.getName() + " " + student.getSurname();
            students.add(string);
        }
        chooseStudentBox.setItems(students);
    }

    void initializeChosenSubject() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("mokslai.csv"));
        String lesson;
        ObservableList<String> lessons = FXCollections.observableArrayList();

        while ((lesson = bufferedReader.readLine()) != null) {
            lessons.add(lesson);
        }
        chooseSubjectBox.setItems(lessons);
    }

    @Override
    public void readFromFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("studentai.csv"));
        String row;
        studentArrayList.clear();

        while ((row = bufferedReader.readLine()) != null) {
            Student student = new Student();
            String[] strings = row.split(";");
            student.setCode(strings[0]);
            student.setName(strings[1]);
            student.setSurname(strings[2]);
            student.setAge(Integer.parseInt(strings[3]));
            student.setGroup(Integer.parseInt(strings[4]));
            student.setPassword(strings[5]);

            ArrayList<String> dates = new ArrayList<>();

            for (int i = 6; i < strings.length; ++i) {
                String s = strings[i];
                student.attendanceMap.put(strings[i], strings[i + 1]);
                i++;
            }
            studentArrayList.add(student);
        }
        bufferedReader.close();
    }

    public void leaveButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) leaveButton.getScene().getWindow();
        stage.close();
    }
}
