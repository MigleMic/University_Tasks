package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TableElement {
    SimpleStringProperty FirstColumnValue;
    ObservableList<SimpleStringProperty> columns = FXCollections.observableArrayList();

    public TableElement(Student student, ArrayList<String> dates, String lesson)  //Filtruojama pagal dalyka
    {
        dates.sort((p1, p2) -> p1.compareTo(p2));

        String name = student.getName() + " " + student.getSurname();
        this.FirstColumnValue = new SimpleStringProperty(name);

        for (int i = 0; i < dates.size(); i++) {
            columns.add(new SimpleStringProperty(""));
        }

        List<String> copy = new ArrayList<>(student.attendanceMap.get(lesson));
        Collections.sort(copy);

        ArrayList<String> sortedCopy = new ArrayList<>();
        //Ismetam kad data dukart nebutu
        for (String value : copy) {
            if (!sortedCopy.contains(value)) {
                sortedCopy.add(value);
            }
        }

        List<String> noDuplicates = copy.stream().distinct().collect(Collectors.toList());

        for (String value : noDuplicates) {
            System.out.print(value);
        }

        for (int i = 0; i < dates.size(); i++) {
            Collections.sort(noDuplicates);
            for (String date : noDuplicates) {
                if (dates.get(i).equals(date)) {
                    columns.set(i, new SimpleStringProperty("X"));
                }
            }
        }
    }

    public TableElement(String lesson, Student student, ArrayList<String> dates)       //Kai pasirinkta filtruoti pagal studenta
    {
        dates.sort((d1, d2) -> d1.compareTo(d2));
        String columnName = lesson;
        this.FirstColumnValue = new SimpleStringProperty(columnName);

        for (int i = 0; i < dates.size(); i++) {
            columns.add(new SimpleStringProperty(""));
        }

        ArrayList<String> copy = new ArrayList<>(student.attendanceMap.get(lesson));
        Collections.sort(copy);

        for (int i = 0; i < dates.size(); i++) {
            Collections.sort(copy);
            for (String date : copy) {
                if (dates.get(i).equals(date)) {
                    columns.set(i, new SimpleStringProperty("X"));
                }
            }
        }
    }

    public TableElement(ArrayList<String> dates, String chosenGroup, ObservableList<Student> students, String subject) //Filtruojam pagal grupe
    {
        dates.sort((p1, p2) -> p1.compareTo(p2));
        for (Student student : students) {
            if ((Integer.valueOf(student.getGroup())).toString().equals(chosenGroup)) {

            }
        }
        for (int i = 0; i < dates.size(); i++) {
            columns.add(new SimpleStringProperty(""));
        }
        String name = subject;
        for (Student student : students) {
            if (Integer.toString(student.getGroup()).equals(chosenGroup)) {
                student.attendanceMap.get(subject);
                for (int i = 0; i < dates.size(); i++) {
                    for (String attendedDate : student.attendanceMap.get(subject)) {
                        if (attendedDate.equals(dates.get(i))) {
                            columns.add(i, new SimpleStringProperty("X"));
                        }
                    }
                }
            }
        }
        this.FirstColumnValue = new SimpleStringProperty(name);
    }

    ArrayList<String> getLine() {
        ArrayList<String> line = new ArrayList<String>();
        line.add(this.FirstColumnValue.getValue());
        for (SimpleStringProperty date : this.columns) {
            line.add(date.getValue());
        }
        return line;
    }
}
