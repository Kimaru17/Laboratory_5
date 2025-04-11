package controller;

import domain.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class staffAddController
{
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private DatePicker date;
    @javafx.fxml.FXML
    private ChoiceBox cbSupervisor;
    @javafx.fxml.FXML
    private TextField tf_StaffId;
    @javafx.fxml.FXML
    private ChoiceBox cbAssigType;
    @javafx.fxml.FXML
    private ChoiceBox cbEmployee;
    @javafx.fxml.FXML
    private ChoiceBox cbJob;

    private CircularDoublyLinkedList staffingList;
    private Alert alert;

    @javafx.fxml.FXML
    public void initialize() {
        this.staffingList = util.Utility.getStaffList();
        tf_StaffId.setText(String.valueOf(util.Utility.getStaffId()));
        alert = util.FXUtility.alert("Staff List", "Add Staff");
        loadEmployeesToCB();
        loadJobPositionsToCB();
        loadSupervisorsToCB();
        loadAssignmentsToCB();
        alert.setAlertType(Alert.AlertType.ERROR);
    }

    private void loadAssignmentsToCB() {
        cbAssigType.getItems().clear();
        cbAssigType.getItems().clear();


        try {
            CircularLinkedList assignmentsList = util.Utility.getAssignTypeList();

            for (int i = 1; i <= assignmentsList.size(); i++) {
                String s = (String) assignmentsList.getNode(i).data;
                cbAssigType.getItems().add(s);
            }
        } catch (ListException e) {
            System.out.println("Error al cargar asignaciones: " + e.getMessage());
        }
    }

    private void loadSupervisorsToCB() {
        cbSupervisor.getItems().clear();

        try {
            CircularLinkedList supervisorList = util.Utility.getSupervisorList();

            for (int i = 1; i <= supervisorList.size(); i++) {
                String s = (String) supervisorList.getNode(i).data;
                cbSupervisor.getItems().add(s);
            }
        } catch (ListException e) {
            System.out.println("Error al cargar estudiantes: " + e.getMessage());
        }
    }

    private void loadJobPositionsToCB() {
        cbJob.getItems().clear();

        try {
            CircularDoublyLinkedList jobsList = util.Utility.getJobPositionList();

            for (int i = 1; i <= jobsList.size(); i++) {
                JobPosition j = (JobPosition) jobsList.getNode(i).data;
                cbJob.getItems().add(j);
            }
        } catch (ListException e) {
            System.out.println("Error al cargar las posiciones de trabajo: " + e.getMessage());
        }
    }

    private void loadEmployeesToCB() {
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
    }
}