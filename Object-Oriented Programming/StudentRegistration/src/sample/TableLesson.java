package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TableLesson extends tableAbstract {
    public TableLesson() throws IOException {
    }

    ArrayList<TableLesson> tableData = new ArrayList<>();

    @Override
    ObservableList<String> getChoiceValues() throws IOException {
        ObservableList<String> choiceBoxValues = FXCollections.observableArrayList();
        BufferedReader reader = new BufferedReader(new FileReader("mokslai.csv"));
        ObservableList<String> subjects = FXCollections.observableArrayList();
        String subject;
        while ((subject = reader.readLine()) != null) {

            choiceBoxValues.add(subject);
        }
        return choiceBoxValues;
    }

    @Override
    void initializeTable(TableView tableView, String lesson, String month, String year, AnchorPane anchorPane) {

        ArrayList<String> dates = getDates(lesson);
        filterDates(dates, month, year);

        ObservableList<TableElement> tableElements = FXCollections.observableArrayList();

        for (Student student : students) {
            TableElement row = new TableElement(student, dates, lesson);
            tableElements.add(row);
        }

        ObservableList<List<String>> date = FXCollections.observableArrayList();
        List<String> namesOfLessons = new ArrayList<>();
        namesOfLessons.add("");

        for (String data : dates) {
            String[] split = data.split("-");
            namesOfLessons.add(split[2]);
        }
        date.add(0, namesOfLessons);

        for (TableElement tableElement : tableElements) {
            date.add(tableElement.getLine());
        }
        String[][] array = new String[date.size()][31];

        for (int i = 0; i < date.size(); i++) {
            for (int j = 0; j < date.get(0).size(); j++) {
                array[i][j] = date.get(i).get(j);
            }
        }

        ObservableList<String[]> date1 = FXCollections.observableArrayList();
        date1.addAll(Arrays.asList(array));
        date1.remove(0);

        for (int i = 0; i < array[0].length; i++) {
            TableColumn column = new TableColumn(array[0][i]);
            if (i != 0) {
                column.setPrefWidth(24);
            } else {
                column.setPrefWidth(160);
            }
            final int columnNumber = i;
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[columnNumber]));
                }
            });
            tableView.getColumns().add(column);
        }
        tableView.setItems(date1);
    }

    ArrayList<String> getDates(String lesson) {
        ArrayList<String> dates = new ArrayList<>();
        List<String> attendDates = FXCollections.observableArrayList();

        for (Student student : students) {
            boolean include = false;

            for (String lessons : student.attendanceMap.keySet()) {
                if (lesson.equals(lessons)) {
                    for (String date : student.attendanceMap.get(lessons)) {
                        dates.add(date);
                    }
                    include = true;
                }
            }
        }
        return dates;
    }

    ArrayList<String> filterDates(ArrayList<String> dates, String month, String year) {
        Iterator<String> i = dates.iterator();

        while (i.hasNext()) {
            String[] splitDate = i.next().split("-");
            if (splitDate[0].equals(year) && splitDate[1].equals(month)) {
            } else {
                i.remove();
            }
        }
        dates.sort((p1, p2) -> p1.compareTo(p2));
        return dates;
    }
}