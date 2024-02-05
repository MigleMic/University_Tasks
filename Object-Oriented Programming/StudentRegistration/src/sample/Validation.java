package sample;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Validation {
    public boolean validateInput(TextField code, TextField name, TextField surname, TextField age,
                                 ChoiceBox group, TextField password, Label codeError, Label nameError,
                                 Label surnameError, Label ageError, Label groupError, Label passwordError) {

        if (textFilled(code, codeError) && textFilled(name, nameError) && textFilled(surname, surnameError)
                && textFilled(age, ageError) && choiceSelected(group, groupError) && textFilled(password, passwordError)) {

            String string = code.getText();
            String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,20}$";
            boolean valid = true;

            if (!code.getText().matches("[0-9]{7}")) {
                code.clear();
                codeError.setText("!");
                valid = false;
            }

            if (!name.getText().matches("[a-z A-Z]+")) {
                name.clear();
                nameError.setText("!");
                valid = false;
            }

            if (!surname.getText().matches("[a-z A-Z]+")) {
                surname.clear();
                surnameError.setText("!");
                valid = false;
            }

            if (!age.getText().matches("[0-9]{2}")) {
                age.clear();
                ageError.setText("!");
                valid = false;
            }

            if (!password.getText().matches(passwordRegex)) {
                password.clear();
                passwordError.setText("!");
                valid = false;
            }
            return valid;
        }
        return false;
    }

    boolean textFilled(TextField textField, Label label) {
        boolean filled = true;
        if (textField.getText().isEmpty()) {
            label.setText("!");
            filled = false;
        } else {
            label.setText("");
        }
        return filled;
    }

    boolean choiceSelected(ChoiceBox choiceBox, Label label) {
        boolean selection = true;

        if (choiceBox == null || choiceBox.getValue() == "" || choiceBox.getValue() == null) {
            label.setText("!");
            selection = false;
        } else {
            label.setText("");
        }
        return selection;
    }
}