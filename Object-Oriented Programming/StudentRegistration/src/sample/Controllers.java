package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public interface Controllers {
    public static ObservableList<Student> studentsList = FXCollections.observableArrayList();
    void readFromFile() throws IOException;
}
