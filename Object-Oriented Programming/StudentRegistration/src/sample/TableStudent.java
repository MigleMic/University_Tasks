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

public class TableStudent extends tableAbstract {
    public TableStudent() throws IOException {
    }

    @Override
    ObservableList<String> getChoiceValues() {
        ObservableList<String> values = FXCollections.observableArrayList();

        for (Student student : students) {
            String text = student.getCode() + " - " + student.getName() + " " + student.getSurname();
            values.add(text);
        }
        return values;
    }

    ArrayList<String> getDates(String code) {
        Set<String> dates = new TreeSet<>();

        for (Student student : students) {
            if (student.getCode().equals(code)) {
                for (String subject : student.attendanceMap.keySet()) {
                    for (String studentDate : student.attendanceMap.get(subject)) {
                        dates.add(studentDate);
                    }
                }
            }
        }
        ArrayList<String> datesArray = new ArrayList<>();
        datesArray.addAll(dates);
        return datesArray;
    }

    ArrayList<String> filterDate(ArrayList<String> dates, String month, String year) {
        Iterator<String> iterator = dates.iterator();

        while (iterator.hasNext()) {
            String[] split = iterator.next().split("-");

            if (split[0].equals(year) && split[1].equals(month)) {
            } else {
                iterator.remove();
            }
        }
        dates.sort((p1, p2) -> p1.compareTo(p2));
        return dates;
    }

    @Override
    void initializeTable(TableView tableView, String code, String month, String year, AnchorPane anchorPane) {
        //Filtruotas listas pagal grupe
        ArrayList<String> dates = getDates(code); //visos dienos
        filterDate(dates, month, year); //pasirinktos dienos

        ObservableList<TableElement> tableElements = FXCollections.observableArrayList();

        for (Student student : students) {
            if (student.getCode().equals(code))
            {
                for (String key : student.attendanceMap.keySet()) {
                    String subject = key;
                    TableElement row = new TableElement(subject, student, dates);
                    tableElements.add(row);
                }
            }
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
}