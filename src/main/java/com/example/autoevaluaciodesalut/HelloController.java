package com.example.autoevaluaciodesalut;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class HelloController {
    @FXML
    private Label welcomeText;

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
        String date = dateField.getText();
        if (data.containsKey(date)) {
            System.out.println("Ja s'han introduït les dades per aquesta data. Vols sobreescriure-les? (s/n)");
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

        data.put(date, new Entry(mood, physicalCondition, foods));

        System.out.println("Dades afegides correctament per a " + date);
    }

    @FXML
    private void showStatistics() {
        int totalDays = data.size();
        double totalMood = 0;
        double totalPhysicalCondition = 0;
        Map<String, Integer> foodCount = new HashMap<>();
        String bestMoodDay = null;
        String bestPhysicalConditionDay = null;
        int bestMood = -1;
        int bestPhysicalCondition = -1;

        for (Map.Entry<String, Entry> entry : data.entrySet()) {
            String date = entry.getKey();
            Entry e = entry.getValue();

            totalMood += e.getMood();
            totalPhysicalCondition += e.getPhysicalCondition();

            for (String food : e.getFoods()) {
                if (food != null) {
                    foodCount.put(food, foodCount.getOrDefault(food, 0) + 1);
                }
            }

            if (e.getMood() > bestMood) {
                bestMood = e.getMood();
                bestMoodDay = date;
            }

            if (e.getPhysicalCondition() > bestPhysicalCondition) {
                bestPhysicalCondition = e.getPhysicalCondition();
                bestPhysicalConditionDay = date;
            }
        }

        double averageMood = totalMood / totalDays;
        double averagePhysicalCondition = totalPhysicalCondition / totalDays;
        String mostConsumedFood = foodCount.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        System.out.println("Quantitat de dies introduïts: " + totalDays);
        System.out.println("Mitjana de l'estat d'ànim: " + averageMood);
        System.out.println("Mitjana de l'estat físic: " + averagePhysicalCondition);
        System.out.println("Menjar o beguda més consumida: " + mostConsumedFood);
        System.out.println("Dies amb millor estat d'ànim: " + bestMoodDay);
        System.out.println("Dies amb millor estat físic: " + bestPhysicalConditionDay);
    }

    private static class Entry {
        private final int mood;
        private final int physicalCondition;
        private final String[] foods;

        public Entry(int mood, int physicalCondition, String[] foods) {
            this.mood = mood;
            this.physicalCondition = physicalCondition;
            this.foods = foods;
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
    }
}
