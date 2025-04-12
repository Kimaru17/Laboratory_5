package controller;

import domain.CircularDoublyLinkedList;
import domain.JobPosition;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import util.Utility;

public class JobPositionAddController {
    @javafx.fxml.FXML
    private TextField tf_description;
    @javafx.fxml.FXML
    private TextField tf_hourlyW;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextField tf_jobPId;

    private CircularDoublyLinkedList jobPositionList; // Instancia de la lista circular

    public void initialize() {
        jobPositionList = Utility.getJobposition();

    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        try {
            String description = tf_description.getText();
            double hourlyWage = Double.parseDouble(tf_hourlyW.getText());

            JobPosition newJob = new JobPosition(description, hourlyWage);

            int totalHours = (int) (Math.random() * 11) + 40; // Genera entre 40 y 50
            newJob.setTotalHours(totalHours);

            jobPositionList.add(newJob);

            // Guardar la lista actualizada
            Utility.setJobposition(jobPositionList);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Job Position Added",
                    "Job Position successfully added to the list.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Hourly Wage",
                    "Please enter a valid hourly wage (numeric value).");
        }
    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        tf_description.clear();
        tf_hourlyW.clear();
        tf_jobPId.clear(); // Depending on if you use this field
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
        // Close the window or perform other actions on close
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "/ucr/lab/jobposition.fxml", bp);
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}