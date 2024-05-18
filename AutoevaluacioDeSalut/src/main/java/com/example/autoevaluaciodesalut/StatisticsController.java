package com.example.autoevaluaciodesalut;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsController {
    @FXML
    private Label totalDaysLabel;

    @FXML
    private Label averageMoodLabel;

    @FXML
    private Label averagePhysicalConditionLabel;

    @FXML
    private Label mostConsumedFoodLabel;

    @FXML
    private Label bestMoodDayLabel;

    @FXML
    private Label bestPhysicalConditionDayLabel;

    private Map<String, HelloController.Entry> data = new HashMap<>();

    public void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String date = parts[1];
                int mood = Integer.parseInt(parts[2]);
                int physicalCondition = Integer.parseInt(parts[3]);
                String[] foods = parts[4].split(";");
                HelloController.Entry entry = new HelloController.Entry(name, date, mood, physicalCondition, foods);
                data.put(name, entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        showStatistics();
    }

    private void showStatistics() {
        int totalDays = data.size();
        double totalMood = 0;
        double totalPhysicalCondition = 0;
        Map<String, Integer> foodCount = new HashMap<>();
        String bestMoodDay = null;
        String bestPhysicalConditionDay = null;
        int bestMood = -1;
        int bestPhysicalCondition = -1;

        for (Map.Entry<String, HelloController.Entry> entry : data.entrySet()) {
            HelloController.Entry e = entry.getValue();

            totalMood += e.getMood();
            totalPhysicalCondition += e.getPhysicalCondition();

            for (String food : e.getFoods()) {
                if (food != null) {
                    foodCount.put(food, foodCount.getOrDefault(food, 0) + 1);
                }
            }

            if (e.getMood() > bestMood) {
                bestMood = e.getMood();
                bestMoodDay = e.getDate();
            }

            if (e.getPhysicalCondition() > bestPhysicalCondition) {
                bestPhysicalCondition = e.getPhysicalCondition();
                bestPhysicalConditionDay = e.getDate();
            }
        }

        double averageMood = totalMood / totalDays;
        double averagePhysicalCondition = totalPhysicalCondition / totalDays;
        String mostConsumedFood = foodCount.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        totalDaysLabel.setText("Quantitat de dies introduïts: " + totalDays);
        averageMoodLabel.setText("Mitjana de l'estat d'ànim: " + averageMood);
        averagePhysicalConditionLabel.setText("Mitjana de l'estat físic: " + averagePhysicalCondition);
        mostConsumedFoodLabel.setText("Menjar o beguda més consumida: " + mostConsumedFood);
        bestMoodDayLabel.setText("Dies amb millor estat d'ànim: " + bestMoodDay);
        bestPhysicalConditionDayLabel.setText("Dies amb millor estat físic: " + bestPhysicalConditionDay);
    }

    @FXML
    private void goBack() {
        Stage stage = (Stage) totalDaysLabel.getScene().getWindow();
        stage.close();
    }
}
