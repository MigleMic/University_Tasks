package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;

public class TableGroup extends tableAbstract {
    public TableGroup() throws IOException {
    }

    @Override
    ObservableList<String> getChoiceValues() {
        Set<String> choices = new TreeSet<>();
        ObservableList<String> choicesValues = FXCollections.observableArrayList();

        for (Student student : students) {
            boolean presentValue = false;
            String string = new Integer(student.getGroup()).toString();

            for (String value : choicesValues) {
                if (value.equals(string)) {
                    presentValue = true;
                }
            }
            if (!presentValue) {
                choicesValues.add(string);
            }
        }
        return choicesValues;
    }

    @Override
    void initializeTable(TableView tableView, String group, String month, String year, AnchorPane anchorPane) {
        ArrayList<String> dates = getDates(group);
        filterDates(dates, month, year);

        ObservableList<TableElement> tableElements = FXCollections.observableArrayList();
        Set<String> lessons = new TreeSet<>();

        for (Student student : students) {
            if (Integer.toString(student.getGroup()).equals(group)) {
                lessons.addAll(student.attendanceMap.keySet());
            }
        }

        for (String leson : lessons) {
            TableElement row = new TableElement(dates, group, students, leson);
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

    ArrayList<String> getDates(String group) {
        Set<String> dates = new TreeSet<>();
        for (Student student : students) {
            if (group.equals(Integer.toString(student.getGroup()))) {
                for (String lesson : student.attendanceMap.keySet()) {
                    for (String date : student.attendanceMap.get(lesson)) {
                        dates.add(date);
                    }
                }
            }
        }
        ArrayList<String> dateList = new ArrayList<>();
        dateList.addAll(dates);
        return dateList;
    }

    ArrayList<String> filterDates(ArrayList<String> date, String month, String year) {
        Iterator<String> i = date.iterator();

        while (i.hasNext()) {
            String[] splitDate = i.next().split("-");
            if (splitDate[0].equals(year) && splitDate[1].equals(month)) {
            } else {
                i.remove();

            }
        }
        date.sort((p1, p2) -> p1.compareTo(p2));
        return date;
    }
}
