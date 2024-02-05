package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FilterAttendanceController implements Initializable, Controllers {
    @FXML
    private Label chosenLabel;

    @FXML
    private RadioButton studentChosenButton;

    @FXML
    private RadioButton groupChosenButton;

    @FXML
    private RadioButton lessonChosenButton;

    @FXML
    private ChoiceBox<String> yearBox;

    @FXML
    private ChoiceBox<String> monthBox;

    @FXML
    private Button filterConfirmButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TableView<String[]> attendanceTable;

    @FXML
    Label informationLabel;

    @FXML
    Button saveButton;

    private ObservableList<String> months = FXCollections.observableArrayList();
    private ObservableList<String> years = FXCollections.observableArrayList();

    @FXML
    void filterConfirmButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (studentChosenButton.isSelected()) {
            chosenLabel.setText("Pasirinkite studentą:");
            TableStudent tableStudent = new TableStudent();
            choiceBox.setItems(tableStudent.getChoiceValues());
        } else if (groupChosenButton.isSelected()) {
            chosenLabel.setText("Pasirinkite grupę:");
            TableGroup tableGroup = new TableGroup();
            choiceBox.setItems(tableGroup.getChoiceValues());
        } else if (lessonChosenButton.isSelected()) {
            chosenLabel.setText("Pasirinkite dalyką:");
            TableLesson tableLesson = new TableLesson();
            choiceBox.setItems(tableLesson.getChoiceValues());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeButtonGroup();
        try {
            readFromFile();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    void makeButtonGroup() {
        ToggleGroup buttons = new ToggleGroup();
        studentChosenButton.setToggleGroup(buttons);
        groupChosenButton.setToggleGroup(buttons);
        lessonChosenButton.setToggleGroup(buttons);
    }

    @FXML
    void choiceBoxConfirmButtonOnAction(ActionEvent event) {
        if (choiceBox.getValue() != null) {
            months.clear();

            for (int i = 1; i <= 12; ++i) {
                if (i < 10) {
                    months.add("0" + i);
                } else {
                    months.add("" + i);
                }
            }
            monthBox.setItems(months);
            yearBox.setItems(years);
        }
    }

    String getCode(ChoiceBox choiceBox) {
        String[] string = ((String) choiceBox.getValue()).split(" - ");
        String code = string[0];
        return code;
    }

    @FXML
    void periodConfirmButtonOnAction(ActionEvent actionEvent) throws IOException {
        String string;
        if (groupChosenButton.isSelected()) {
            string = "Filtruojama pagal: " + choiceBox.getValue() + " grupę" +
                    "\nLaikotarpis " + monthBox.getValue() + " " + yearBox.getValue();
        } else {
            string = "Filtruojama pagal: " + choiceBox.getValue() +
                    "\nLaikotarpis " + monthBox.getValue() + " " + yearBox.getValue();
        }
        informationLabel.setText(string);
        attendanceTable.getItems().clear();
        attendanceTable.getColumns().clear();
        if (choiceBox.getValue() != null && monthBox.getValue() != null && yearBox.getValue() != null) {
            if (studentChosenButton.isSelected()) {
                attendanceTable.getItems().clear();
                TableStudent tableStudent = new TableStudent();

                tableStudent.initializeTable(attendanceTable, getCode(choiceBox),
                        monthBox.getValue(), yearBox.getValue(),
                        anchorPane);
            }
            if (groupChosenButton.isSelected()) {
                TableGroup tableGroup = new TableGroup();
                attendanceTable.getItems().clear();
                tableGroup.initializeTable(attendanceTable, (String) choiceBox.getValue(),
                        monthBox.getValue(), yearBox.getValue(),
                        anchorPane);
            }
            if (lessonChosenButton.isSelected()) {
                TableLesson tableLesson = new TableLesson();
                attendanceTable.getItems().clear();
                tableLesson.initializeTable(attendanceTable, (String) choiceBox.getValue(),
                        monthBox.getValue(), yearBox.getValue(),
                        anchorPane);
            }
        }
    }


    @Override
    public void readFromFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("studentai.csv"));
        String row;
        while ((row = bufferedReader.readLine()) != null) {
            String[] values = row.split(";");
            for (int i = 6; i < values.length; ++i) {
                i++;
                String date = values[i];
                String[] date1 = date.split("-");
                boolean newYear = true;
                if (years.isEmpty()) {
                    years.add(date1[0]);
                } else {
                    for (String year : years) {
                        if (year.equals(date1[0])) {
                            newYear = false;
                        }
                    }
                    if (newYear) {
                        years.add(date1[0]);
                    }
                }
            }
        }
        bufferedReader.close();
    }

    public void saveButtonOnAction(ActionEvent actionEvent) throws AWTException, IOException {
        Robot robot = new Robot();
        Rectangle rectangle = new Rectangle(350, 15, 550, 700);
        BufferedImage image = robot.createScreenCapture(rectangle);
        ImageIO.write(image, "png", new File("table.png"));
    }
}
