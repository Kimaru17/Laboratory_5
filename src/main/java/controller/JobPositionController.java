package controller;

import domain.CircularDoublyLinkedList;
import domain.JobPosition;
import domain.ListException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import util.FXUtility;
import util.Utility;

public class JobPositionController
{
    @javafx.fxml.FXML
    private TableColumn idTableColumn;
    @javafx.fxml.FXML
    private TableView jobOptionTableview;
    @javafx.fxml.FXML
    private TableColumn tHoursTableColumn;
    @javafx.fxml.FXML
    private TableColumn descriptionTableColumn;
    @javafx.fxml.FXML
    private TableColumn hWagesTableColumn;
    @javafx.fxml.FXML
    private TableColumn mWagesTableColumn;
    @javafx.fxml.FXML
    private BorderPane bp;

    private CircularDoublyLinkedList jobpositionList;
    private Alert alert;

    @FXML
    public void initialize() {
        this.jobpositionList = Utility.getJobposition();
        alert = FXUtility.alert("Job Position List", "Display Job");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tHoursTableColumn.setCellValueFactory(new PropertyValueFactory<>("Total Hours"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        hWagesTableColumn.setCellValueFactory(new PropertyValueFactory<>("Hour Wages"));
        mWagesTableColumn.setCellValueFactory(new PropertyValueFactory<>("Monthly Wages"));

    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        jobOptionTableview.getItems().clear();
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        if (!jobpositionList.isEmpty()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Remove Job Position");
            dialog.setHeaderText("Enter Job Position ID:");
            dialog.setContentText("ID:");

            dialog.showAndWait().ifPresent(input -> {
                if (!Utility.isInteger(input)) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Invalid input. Please enter a valid job position ID.");
                } else {
                    try {
                        JobPosition job = new JobPosition(Integer.parseInt(input));
                        if (jobpositionList.contains(job)) {
                            jobpositionList.remove(job);
                            Utility.setJobposition(jobpositionList);
                            initialize();
                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            alert.setContentText("The job position was removed successfully.");
                        } else {
                            alert.setAlertType(Alert.AlertType.ERROR);
                            alert.setContentText("Job position does not exist or was already removed.");
                        }
                    } catch (ListException e) {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("Error removing job position: " + e.getMessage());
                    }
                }
                alert.showAndWait();
            });
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Job position list is empty.");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        FXUtility.loadPage(
                "controller.JobPositionController", "jobpositionadd.fxml", bp);
    }

    @javafx.fxml.FXML
    public void sortByHourlyOnAction(ActionEvent actionEvent) throws ListException {
        if (!this.jobOptionTableview.getItems().isEmpty()) {
            this.jobpositionList.sort();
            util.Utility.setJobposition(this.jobpositionList);
            alert.setContentText("The job positions list was sorted by hourly wages");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
            jobOptionTableview.getItems().clear();
            initialize();
        } else {
            alert.setContentText("The job positions list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void removeLastOnAction(ActionEvent actionEvent) {
        try {
            if (!jobpositionList.isEmpty()) {
                jobpositionList.removeLast();
                Utility.setJobposition(jobpositionList);
                initialize();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Last job position was removed .");
            } else {
                alert.setContentText("Job position list is empty.");
            }
            alert.showAndWait();
        } catch (ListException e) {
            alert.setContentText("Error removing last job position: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Previous Job Position");
        dialog.setHeaderText("Enter Job Position ID:");
        dialog.setContentText("ID:");

        dialog.showAndWait().ifPresent(input -> {
            if (!Utility.isInteger(input)) {
                alert.setContentText("Invalid input. Please enter a valid integer ID.");
            } else {
                try {
                    JobPosition job = new JobPosition(Integer.parseInt(input));
                    int index = jobpositionList.indexOf(job);
                    if (index == -1) {
                        alert.setContentText("Job position not found.");
                    } else {
                        JobPosition prev = (JobPosition) jobpositionList.getNode(index == 1 ? jobpositionList.size() : index - 1).data;
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("Previous Job Position: \n" + prev);
                    }
                } catch (ListException e) {
                    alert.setContentText("Error: " + e.getMessage());
                }
            }
            alert.showAndWait();
        });
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Check Job Position");
        dialog.setHeaderText("Enter Job Position ID:");
        dialog.setContentText("ID:");

        dialog.showAndWait().ifPresent(input -> {
            if (!Utility.isInteger(input)) {
                alert.setContentText("Invalid input. Please enter a valid integer ID.");
            } else {
                try {
                    JobPosition job = new JobPosition(Integer.parseInt(input));
                    if (jobpositionList.contains(job)) {
                        int index = jobpositionList.indexOf(job);
                        JobPosition found = (JobPosition) jobpositionList.getNode(index).data;
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("Job position found:\n" + found);
                    } else {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("Job position not found.");
                    }
                } catch (ListException e) {
                    alert.setContentText("Error: " + e.getMessage());
                }
            }
            alert.showAndWait();
        });
    }

    @javafx.fxml.FXML
    public void sortByNameOnAction(ActionEvent actionEvent) throws ListException {
        if (!this.jobOptionTableview.getItems().isEmpty()) {

            this.jobpositionList.sortByName();
            util.Utility.setJobposition(this.jobpositionList);

            alert.setContentText("The job positions list was sorted by description");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
            jobOptionTableview.getItems().clear();

            initialize();
        } else {
            // Si la lista está vacía, mostramos un mensaje de error
            alert.setContentText("The job positions list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void getNextOnAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Next Job Position");
        dialog.setHeaderText("Enter Job Position ID:");
        dialog.setContentText("ID:");

        dialog.showAndWait().ifPresent(input -> {
            if (!Utility.isInteger(input)) {
                alert.setContentText("Invalid input. Please enter a valid integer ID.");
            } else {
                try {
                    JobPosition job = new JobPosition(Integer.parseInt(input));
                    int index = jobpositionList.indexOf(job);
                    if (index == -1) {
                        alert.setContentText("Job position not found.");
                    } else {
                        JobPosition next = (JobPosition) jobpositionList.getNode(index == jobpositionList.size() ? 1 : index + 1).data;
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("Next Job Position: \n" + next);
                    }
                } catch (ListException e) {
                    alert.setContentText("Error: " + e.getMessage());
                }
            }
            alert.showAndWait();
        });
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {

        if(!this.jobOptionTableview.getItems().isEmpty()) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            try {
                alert.setContentText("Current size of the job position list: " + jobpositionList.size());
            } catch (ListException e) {
                throw new RuntimeException(e);
            }
            alert.showAndWait();
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("The Table is Empty");
        }
    }
}