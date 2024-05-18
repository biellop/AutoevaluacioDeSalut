package com.example.autoevaluaciodesalut;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField nameField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField moodField;

    @FXML
    private TextField physicalConditionField;

    @FXML
    private CheckBox milkCheckBox;

    @FXML
    private CheckBox cerealCheckBox;

    @FXML
    private CheckBox pastaCheckBox;

    @FXML
    private CheckBox fruitCheckBox;

    @FXML
    private CheckBox vegetablesCheckBox;

    @FXML
    private CheckBox legumesCheckBox;

    @FXML
    private CheckBox waterCheckBox;

    @FXML
    private CheckBox alcoholCheckBox;

    @FXML
    private CheckBox juiceCheckBox;

    private Map<String, Entry> data = new HashMap<>();

    @FXML
    private void addData() {
        String name = nameField.getText();
        String date = dateField.getText();
        if (data.containsKey(name)) {
            System.out.println("Ja s'han introduït les dades per aquest nom. Vols sobreescriure-les? (s/n)");
            return;
        }

        int mood = Integer.parseInt(moodField.getText());
        int physicalCondition = Integer.parseInt(physicalConditionField.getText());

        String[] foods = {
                milkCheckBox.isSelected() ? "Llet" : null,
                cerealCheckBox.isSelected() ? "Cereals" : null,
                pastaCheckBox.isSelected() ? "Pasta" : null,
                fruitCheckBox.isSelected() ? "Fruita" : null,
                vegetablesCheckBox.isSelected() ? "Vegetals" : null,
                legumesCheckBox.isSelected() ? "Llegums" : null,
                waterCheckBox.isSelected() ? "Aigua" : null,
                alcoholCheckBox.isSelected() ? "Alcohol" : null,
                juiceCheckBox.isSelected() ? "Sucs" : null
        };

        Entry entry = new Entry(name, date, mood, physicalCondition, foods);
        data.put(name, entry);

        saveDataToFile(entry);

        System.out.println("Dades afegides correctament per a " + name);
    }

    private void saveDataToFile(Entry entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", true))) {
            writer.write(entry.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showStatistics() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("statistics-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            StatisticsController controller = fxmlLoader.getController();
            controller.loadDataFromFile();
            Stage stage = new Stage();
            stage.setTitle("Estadístiques de Salut");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Entry {
        private final String name;
        private final String date;
        private final int mood;
        private final int physicalCondition;
        private final String[] foods;

        public Entry(String name, String date, int mood, int physicalCondition, String[] foods) {
            this.name = name;
            this.date = date;
            this.mood = mood;
            this.physicalCondition = physicalCondition;
            this.foods = foods;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public int getMood() {
            return mood;
        }

        public int getPhysicalCondition() {
            return physicalCondition;
        }

        public String[] getFoods() {
            return foods;
        }

        @Override
        public String toString() {
            return name + "," + date + "," + mood + "," + physicalCondition + "," + String.join(";", foods);
        }
    }
}
