package sample;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class Student {
    StringProperty name;
    StringProperty surname;
    StringProperty code;
    IntegerProperty age;
    IntegerProperty group;
    StringProperty password;
    Multimap<String, String> attendanceMap = ArrayListMultimap.create();

    public String getName() {
        return name.getValue();
    }

    public String getSurname() {
        return surname.getValue();
    }

    public String getCode() {
        return code.getValue();
    }

    public int getAge() {
        return age.getValue();
    }

    public int getGroup() {
        return group.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public Multimap<String, String> getAttendanceMap() {
        return attendanceMap;
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public void setSurname(String surname) {
        this.surname = new SimpleStringProperty(surname);
    }

    public void setAge(int age) {
        this.age = new SimpleIntegerProperty(age);
    }

    public void setGroup(int group) {
        this.group = new SimpleIntegerProperty(group);
    }

    public void setCode(String code) {
        this.code = new SimpleStringProperty(code);
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    void printStudent() {
        System.out.println("Kodas " + this.code.getValue());
        System.out.println("Vardas " + this.name.getValue());
        System.out.println("Pavarde " + this.surname.getValue());
        System.out.println("Grupe " + this.group.getValue());
        System.out.println("Slaptazodis " + this.password.getValue());

        for (String string : attendanceMap.keySet()) {
            List<String> dates = (List<String>) attendanceMap.get(string);
            System.out.println("Mokslas" + string + " data " + dates.toString());
        }
        System.out.println(" ");
    }
}