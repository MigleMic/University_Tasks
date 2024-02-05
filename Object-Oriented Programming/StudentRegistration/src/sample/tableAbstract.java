package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class tableAbstract {
    ObservableList<Student> students = FXCollections.observableArrayList();

    public tableAbstract() throws IOException {
        readFile();
    }

    abstract ObservableList<String> getChoiceValues() throws IOException;

    abstract void initializeTable(TableView tableView, String lesson, String month, String year, AnchorPane anchorPane);

    public void readFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("studentai.csv"));
        String row;

        students.clear();

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
                String key = values[i];
                student.attendanceMap.put(values[i], values[i + 1]); //!!
                i++;
            }
            students.add(student);
        }
        bufferedReader.close();
    }
}