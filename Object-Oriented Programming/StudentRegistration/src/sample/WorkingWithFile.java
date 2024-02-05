package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkingWithFile {
    public void writeToFile(ArrayList<Student> studentArrayList) throws IOException {
        PrintWriter writer1 = new PrintWriter("studentai.csv");
        writer1.print("");
        writer1.close();
        Writer writer = null;
        File file = new File("studentai.csv");
        writer = new BufferedWriter(new FileWriter(file, false));
        Student student = new Student();
        for (Student pupil : studentArrayList) {
            String text = pupil.getCode() + ";" + pupil.getName() + ";" + pupil.getSurname() + ";" + pupil.getAge() + ";" + pupil.getGroup() + ";" + pupil.getPassword();
            for (String subject : pupil.attendanceMap.keySet()) {
                List<String> dates = (List<String>) pupil.attendanceMap.get(subject);
                for (String date : dates) {
                    text = text + ";" + subject + ";" + date;
                }
            }
            text = text + "\n";
            writer.append(text);
        }
        writer.close();
    }
}