package kt.golaycodekt;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import javafx.embed.swing.*;

import static javax.imageio.ImageIO.read;

public class GolayCode extends Application {

    Coding coding = new Coding();
    Channel channel = new Channel();
    Decoding decoding = new Decoding();

    private Image image;

    private BufferedImage bufferedImage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Golay Code");


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.CENTER);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().addAll(column1, column2);

        Label lblPossibility = new Label("Error possibility:");
        TextField txtPossibility = new TextField();
        Button btnSendVector = new Button("Send vector");
        Button btnSendText = new Button("Send text");
        //Button btnSendPicture = new Button("Send picture");

        GridPane.setHalignment(lblPossibility, HPos.RIGHT);
        GridPane.setHalignment(txtPossibility, HPos.LEFT);
        GridPane.setHalignment(btnSendVector, HPos.CENTER);
        GridPane.setHalignment(btnSendText, HPos.CENTER);
        //GridPane.setHalignment(btnSendPicture, HPos.CENTER);

        grid.add(lblPossibility, 0, 0);
        grid.add(txtPossibility, 1, 0);
        grid.add(btnSendVector, 0, 1, 2, 1);
        grid.add(btnSendText, 0, 2, 2, 1);
        //grid.add(btnSendPicture, 0, 3, 2, 1);


        //Vektoriaus siuntimas
        btnSendVector.setOnAction(e -> {
            if (txtPossibility.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty possibility");
                alert.setHeaderText("Empty possibility field");
                alert.setContentText("Enter a possibility");
                alert.showAndWait();
            } else {
                float possibility = Float.parseFloat(txtPossibility.getText());
                sendVector(possibility);
            }
        });

        //Teksto siuntimas
        btnSendText.setOnAction(e -> {
            if (txtPossibility.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty possibility");
                alert.setHeaderText("Empty possibility field");
                alert.setContentText("Enter a possibility");
                alert.showAndWait();
            } else {
                float possibility = Float.parseFloat(txtPossibility.getText());
                sendText(possibility);
            }
        });

        //Paveikslelio siuntimas
        /*btnSendPicture.setOnAction(e -> {
            if (txtPossibility.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty possibility");
                alert.setHeaderText("Empty possibility field");
                alert.setContentText("Enter a possibility");
                alert.showAndWait();
            } else {
                float possibility = Float.parseFloat(txtPossibility.getText());
                try {
                    sendPicture(possibility);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });*/

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Paveikslelio pasirinkimas
    private void sendPicture(float possibility) throws IOException {
        Stage stage = new Stage();

        int width = 250;
        int height = 250;


        //Gaunam paveiksleli
        ImageView imageView = new ImageView();
        FileChooser fileChooser = new FileChooser();
        Label labelPicture = new Label("Original image");
        labelPicture.setVisible(false);
        Button selectPicture = new Button("Choose image");

        Button codePicture = new Button("Code image");
        codePicture.setVisible(false);
        selectPicture.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                image = new Image(file.toURI().toString());

                try {
                    bufferedImage = SwingFXUtils.fromFXImage(image, null);

                    ImageIO.write(bufferedImage, "bmp", new File("paveiksliukas.bmp"));
                } catch (IOException ex) {
                    System.out.println("Could not read the message");
                }
                imageView.setImage(image);
                imageView.setFitWidth(width);
                imageView.setFitHeight(height);
                selectPicture.setVisible(false);
                labelPicture.setVisible(true);
                codePicture.setVisible(true);
            }
        });

        ImageView imageViewCoded = new ImageView();
        Label labelPictureCoded = new Label("Coded picture");
        imageViewCoded.setVisible(false);
        labelPictureCoded.setVisible(false);

        ImageView imageViewNotCoded = new ImageView();
        Label labelPictureNotCoded = new Label("Not coded picture");
        imageViewNotCoded.setVisible(false);
        labelPictureNotCoded.setVisible(false);

        //Paveikslelio vertimas i int masyva
        codePicture.setOnAction(e -> {
            byte[] imageBytes = AdditionalResources.convertImageToBytes(bufferedImage);

            String[] imageString = AdditionalResources.convertBytesToStrings(imageBytes);

            //1 scenarijus. Siunciam iskart kanalu nenaudojant kodo
            int[][] finalVector1 = new int[imageString.length][24];

            //24 ilgio vektorius, saugome kurioje vietoje yra klaidos
            int[] errorPlace = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            for (int i = 0; i < imageString.length; i++) {
                for (int j = 0; j < imageString[i].length(); j++) {
                    finalVector1[i][j] = imageString[i].charAt(j) - '0';
                }
                if (MatrixCalculations.vectorWeight(finalVector1[i]) % 2 == 0) {
                    finalVector1[i][23] = 1;
                }

                channel.channell(finalVector1[i], possibility, errorPlace);

                finalVector1[i] = decoding.decode(finalVector1[i]);
                if (finalVector1[i] == null) {
                    finalVector1[i] = AdditionalResources.nullVector();
                }
            }

            BufferedImage firstImage = AdditionalResources.convertIntArrayToImage(finalVector1);

            Image withoutCode = SwingFXUtils.toFXImage(firstImage, null);

            imageViewNotCoded.setImage(withoutCode);
            imageViewNotCoded.setFitWidth(width);
            imageViewNotCoded.setFitHeight(height);
            imageViewNotCoded.setVisible(true);
            labelPictureNotCoded.setVisible(true);


            //2 scenarijus
            int[] information = new int[imageString.length];

            for (int i = 0; i < imageString.length; i++) {
                information[i] = Integer.parseInt(imageString[i],2);
            }

            int[][] encodedString = new int[imageString.length][24];
            for (int i = 0; i < information.length; i++) {
                encodedString[i] = coding.codeInformation(imageString[i]);
            }

            int[][] finalVector2 = new int[information.length][24];
            //24 ilgio vektorius, saugome kurioje vietoje yra klaidos
            int[] errorPlace2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            for (int i = 0; i < information.length; i++) {
                MatrixCalculations.copyArray(finalVector2[i], encodedString[i]);

                if (MatrixCalculations.vectorWeight(finalVector2[i]) % 2 == 0) {
                    finalVector2[i][23] = 1;
                }

                channel.channell(finalVector2[i], possibility, errorPlace2);

                finalVector2[i] = decoding.decode(finalVector2[i]);
                if (finalVector2[i] == null) {
                    finalVector2[i] = AdditionalResources.nullVector();
                }
            }

            BufferedImage secondImage = AdditionalResources.convertIntArrayToImage(finalVector2);
            try {
                ImageIO.write(secondImage, "bmp", new File("secondPicture.bmp"));
                ImageIO.write(firstImage, "bmp", new File("firstImage.bmp"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            Image withCode = SwingFXUtils.toFXImage(secondImage, null);

            imageViewCoded.setImage(withCode);
            imageViewCoded.setFitWidth(width);
            imageViewCoded.setFitHeight(height);
            imageViewCoded.setVisible(true);
            labelPictureCoded.setVisible(true);


        });

        HBox hBox1 = new HBox(10);
        hBox1.getChildren().addAll(imageViewNotCoded, labelPictureNotCoded);

        HBox hBox2 = new HBox(20);
        hBox2.getChildren().addAll(imageViewCoded, labelPictureCoded);

        HBox container = new HBox(10);
        container.getChildren().addAll(hBox1, hBox2);

        VBox root = new VBox();
        root.getChildren().
                addAll(
                        imageView, labelPicture,
                        selectPicture, codePicture,
                        container
                );

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


    //Teksto pasirinkimas
    public void sendText(float possibility) {
        Stage stage = new Stage();

        //Gauname teksta
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Your text");
        dialog.setHeaderText("Enter text");
        dialog.setContentText("Text");

        TextArea textArea = new TextArea();
        textArea.setWrapText(true); // Enable text wrapping
        textArea.setEditable(true);
        textArea.setPrefColumnCount(40);
        textArea.setPrefRowCount(10);


        Label enteredTextLabel = new Label("Entered text:");
        TextArea enteredTextTextArea = new TextArea();
        enteredTextTextArea.setEditable(true);


        Label notCodedLabel = new Label("Text after the channel:");
        TextArea notCodedTextArea = new TextArea();
        notCodedTextArea.setEditable(false);

        Label codedLabel = new Label("Decoded text, which was coded with Golay code:");
        TextArea codedTextArea = new TextArea();
        codedTextArea.setEditable(false);

        Button decode = new Button("Decode");
        decode.setOnAction(e -> {
            String text = enteredTextTextArea.getText();
            if (text.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong input");
                alert.setHeaderText("Empty field");
                alert.setContentText("Enter text");
                alert.showAndWait();
            }

            int rowCount = text.split("\n").length + 1;
            int columnCount = text.lines().mapToInt(String::length).max().orElse(0);

            notCodedTextArea.setPrefRowCount(rowCount);
            notCodedTextArea.setPrefColumnCount(columnCount);
            codedTextArea.setPrefRowCount(rowCount);
            codedTextArea.setPrefColumnCount(columnCount);

            char[] letters = text.toCharArray();
            int[] asciiLetters = new int[letters.length];
            String[] information = new String[asciiLetters.length];

            //raide pakeiciama i jos atitikmeni desimtainej ascii
            for (int i = 0; i < asciiLetters.length; i++) {
                asciiLetters[i] = (int) letters[i];
            }

            //uzkoduojam dvejetainiu vektoriu
            for (int i = 0; i < asciiLetters.length; i++) {
                information[i] = Integer.toBinaryString(asciiLetters[i]);
            }

            //pridedam 0 jei yra tusciu poziciju vektoriuje
            AdditionalResources.informationNormalization(information);

            double startTime = System.nanoTime() / 1000000.0;
            int[][] encodedString = new int[information.length][24];
            for (int i = 0; i < information.length; i++) {
                encodedString[i] = coding.codeInformation(information[i]);
            }

            int[][] finalVector2 = new int[information.length][24];
            int[][] fromChannel = new int[information.length][24];

            //23 ilgio vektorius, saugome kurioje vietoje yra klaidos
            int[] errorPlace2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            for (int i = 0; i < information.length; i++) {
                MatrixCalculations.copyArray(finalVector2[i], encodedString[i]);

                //Irasome i paskutine ppzicija 1 kad butu nelyginis 1 skaicius masyve
                if (MatrixCalculations.vectorWeight(finalVector2[i]) % 2 == 0) {
                    finalVector2[i][23] = 1;
                }

                channel.channell(finalVector2[i], possibility, errorPlace2);
                fromChannel[i] = finalVector2[i];

                finalVector2[i] = decoding.decode(finalVector2[i]);
                if (finalVector2[i] == null) {
                    finalVector2[i] = AdditionalResources.nullVector();
                }
            }

            String[] binaryString2 = MatrixConvertions.convertToStringArray(finalVector2);
            StringBuilder decodedMessage2 = new StringBuilder();

            for (String binary : binaryString2) {
                int decimalValue = Integer.parseInt(binary, 2);
                char character = (char) decimalValue;

                if ((char) decimalValue == 32 || (char) decimalValue == 64) {
                    character = '\u0020';
                }
                if ((char) decimalValue == '\u0000') {
                    character = '-';
                }

                if ((char) decimalValue == 10) {
                    character = '\n';
                }

                decodedMessage2.append(character);
            }
            String ascii2 = decodedMessage2.toString();

            String[] binaryString = MatrixConvertions.convertToStringArray(fromChannel);
            StringBuilder decodedMessage = new StringBuilder();

            for (String binary : binaryString) {
                int decimalValue = Integer.parseInt(binary, 2);
                char character = (char) decimalValue;

                if ((char) decimalValue == 32 || (char) decimalValue == 64) {
                    character = '\u0020';
                }
                if ((char) decimalValue == '\u0000') {
                    character = '-';
                }

                if ((char) decimalValue == 10) {
                    character = '\n';
                }

                decodedMessage.append(character);
            }

            String ascii = decodedMessage.toString();
            notCodedTextArea.setText(ascii);

            double endTime = System.nanoTime() / 1000000.0;
            double allTime = endTime - startTime;
            System.out.println(allTime);
            codedTextArea.setText(ascii2);
        });

        Button endButton = new Button("End");

        endButton.setOnAction(e -> {
            stage.close();
        });

        VBox root = new VBox(10);
        root.getChildren().
                addAll(
                        enteredTextLabel, enteredTextTextArea,
                        decode,
                        notCodedLabel, notCodedTextArea,
                        codedLabel, codedTextArea, endButton
                );

        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    //vektoriaus pasirinkimas
    public void sendVector(float possibility) {

        Stage stage = new Stage();

        //Gauname vektoriu
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Vector");
        dialog.setHeaderText("Enter vector");
        dialog.setContentText("Vector:");

        String vector = dialog.showAndWait().orElse("");

        //Patikrinam ar vektorius yra 12 ilgio
        if (vector.matches("^[01]+$") && vector.length() == 12) {

            Label enteredVectorLabel = new Label("Entered vector:");
            TextArea enteredVectorTextArea = new TextArea();
            enteredVectorTextArea.setPrefRowCount(1);
            enteredVectorTextArea.setPrefColumnCount(12);
            String space = vector.replaceAll("(.)", "$1 ");
            enteredVectorTextArea.setText(space);
            enteredVectorTextArea.setEditable(false);

            Label encodedVectorLabel = new Label("Coded vector:");
            TextArea encodedVectorTextArea = new TextArea();
            encodedVectorTextArea.setPrefRowCount(1);
            encodedVectorTextArea.setPrefColumnCount(24);
            encodedVectorTextArea.setEditable(false);

            //Uzkoduojame vektorių
            int[] encodedMessage = coding.codeInformation(vector);

            encodedVectorTextArea.setText(AdditionalResources.vectorToString(encodedMessage));

            //Ilgio 23
            int[] finalVector = new int[23];
            MatrixCalculations.copyArray(finalVector, encodedMessage);
            System.out.println("Final vector pirma kart" + Arrays.toString(finalVector));

            //23 ilgio vektorius, saugome kurioje vietoje yra klaidos
            int[] errorPlace = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            //Perleidziame per kanalą
            channel.channell(finalVector, possibility, errorPlace);

            Label channelVectorLabel = new Label("Vector out of channel:");
            TextArea channelVectorTextArea = new TextArea();
            channelVectorTextArea.setPrefRowCount(1);
            channelVectorTextArea.setPrefColumnCount(23);
            channelVectorTextArea.setEditable(false);

            channelVectorTextArea.setText(AdditionalResources.vectorToString(finalVector));

            Label errorPlacesLabel = new Label("Error positions:");
            TextArea errorPlacesTextArea = new TextArea();
            errorPlacesTextArea.setPrefRowCount(1);
            errorPlacesTextArea.setPrefColumnCount(24);
            errorPlacesTextArea.setEditable(false);

            errorPlacesTextArea.setText(AdditionalResources.intCodeToString(errorPlace));

            TextArea seeIfGud = new TextArea();
            seeIfGud.setPrefRowCount(1);
            seeIfGud.setPrefColumnCount(24);
            seeIfGud.setEditable(false);

            //Nukopijuojame uzkoduota pranesima i kita masyva
            int[] finalVector2 = new int[24];

            for (int i = 0; i < finalVector.length; i++)
                finalVector2[i] = finalVector[i];
            System.out.println("Nukopijavus final vector " + Arrays.toString(finalVector2));

            //Jei naudotojas nori, pakeiciame kodą
            Button changeCode = new Button("Change bit values");
            changeCode.setOnAction(e -> {
                Stage stage2 = new Stage();
                stage2.setTitle("Change vector");

                Label messageLabel = new Label("Enter position:");
                TextField positionField = new TextField();
                positionField.setPromptText("Enter position");

                Label changedCodeLabel = new Label("Vector");
                TextArea changedCodeTextArea = new TextArea();
                changedCodeTextArea.setPrefRowCount(1);
                changedCodeTextArea.setPrefColumnCount(24);
                changedCodeTextArea.setEditable(false);
                changedCodeTextArea.setText(AdditionalResources.intCodeToString(finalVector));

                Button changeButton = new Button("Change bit");
                changeButton.setOnAction(event -> {
                    String input = positionField.getText();
                    if (!input.isEmpty()) {
                        int position = Integer.parseInt(input);
                        if (position >= 0 && position < finalVector.length) {
                            //apkeicia 1 -> 0 ir 0 -> 1
                            AdditionalResources.flipping(finalVector, position);
                            changedCodeTextArea.setText(AdditionalResources.vectorToString(finalVector));
                            AdditionalResources.flipping(errorPlace, position);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Wrong input");
                            alert.setHeaderText("Wrong position");
                            alert.setContentText("Enter an existing position");
                            alert.showAndWait();
                        }
                    }
                });
                Button closeAll = new Button("Done");
                closeAll.setOnAction(ev -> {
                    stage2.close();
                    channelVectorTextArea.setText(AdditionalResources.vectorToString(finalVector));
                    errorPlacesTextArea.setText(AdditionalResources.intCodeToString(errorPlace));
                });

                VBox root = new VBox(10);
                root.setPadding(new Insets(10));
                root.getChildren().addAll(messageLabel, positionField,
                        changedCodeLabel, changedCodeTextArea,
                        changeButton, closeAll);

                Scene scene = new Scene(root, 400, 400);
                stage2.setScene(scene);
                stage2.show();
            });

            //Patikrinam koks svoris, pagal tai pridedame 1 arba 0 i 24 pozicija
            //Kadangi pozicijos masyve yra saugoma nuo 0 iki 23, 24 pozicija masyve yra 23
            if (MatrixCalculations.vectorWeight(finalVector2) % 2 == 0) {
                finalVector2[23] = 1;
            }
            else finalVector2[23] = 0;
            System.out.println("Pirmas vektorius" + Arrays.toString(finalVector));
            System.out.println("Dekoduoti vektorius" + Arrays.toString(finalVector2));

            final int[][] wrapVector = {finalVector2};
            //Dekoduojame vektorių
            Button decodeCode = new Button("Decode");

            Label decodedLabel = new Label("Decoded vector:");
            TextArea decodedTextArea = new TextArea();
            decodedTextArea.setPrefRowCount(1);
            decodedTextArea.setPrefColumnCount(12);
            decodedTextArea.setEditable(false);
            decodedTextArea.setVisible(false);
            decodedLabel.setVisible(false);

            Button jobDone = new Button("End");
            jobDone.setVisible(false);

            decodeCode.setOnAction(e ->
            {
                int[] vectorFromWrap = wrapVector[0];
                vectorFromWrap = decoding.decode(vectorFromWrap);

                //parodome dekoduota vektoriu
                if (vectorFromWrap != null) {
                    int[] originalVector = AdditionalResources.getOriginalMessage(vectorFromWrap);
                    decodedTextArea.setText(AdditionalResources.intCodeToString(originalVector));
                    decodedLabel.setVisible(true);
                    decodedTextArea.setVisible(true);
                    jobDone.setVisible(true);
                }
                if (vectorFromWrap == null) {
                    decodedTextArea.setText("Decoding was unsuccessful");
                    decodedLabel.setVisible(true);
                    decodedTextArea.setVisible(true);
                    jobDone.setVisible(true);
                }
            });

            jobDone.setOnAction(e -> {
                stage.close();
            });

            VBox root = new VBox(10);
            root.getChildren().addAll(
                    enteredVectorLabel, enteredVectorTextArea,
                    encodedVectorLabel, encodedVectorTextArea,
                    errorPlacesLabel, errorPlacesTextArea,
                    channelVectorLabel, channelVectorTextArea,
                    changeCode, decodeCode,
                    decodedLabel, decodedTextArea,
                    jobDone);


            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong input");
            alert.setHeaderText("Wrong vector");
            alert.setContentText("Enter vector of length 12");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
