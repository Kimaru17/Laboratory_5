package controller;

import domain.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
        cbEmployee.getItems().clear();

        try {
            CircularLinkedList employeesList = util.Utility.getEmployeeList();

            for (int i = 1; i <= employeesList.size(); i++) {
                Employee e = (Employee) employeesList.getNode(i).data;
                cbEmployee.getItems().add(e);
            }
        } catch (ListException e) {
            System.out.println("Error al cargar los empleados: " + e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) throws ListException {
        if (staffIsValid()){
            Employee selectedEmployee = (Employee) cbEmployee.getValue();
            JobPosition selectedJob = (JobPosition) cbJob.getValue();
            int staffId = Integer.parseInt(this.tf_StaffId.getText());
            if (!staffingList.isEmpty()){
                for (int i = 1; i <= staffingList.size(); i++) {
                    Staffing existingStaff = (Staffing) staffingList.getNode(i).data;

                }
            }

            Staffing staff = new Staffing(
                    Integer.parseInt(tf_StaffId.getText()),
                    LocalDateTime.of(date.getValue(), LocalTime.MIDNIGHT),
                    selectedEmployee.getId(),
                    selectedEmployee.getLastName()+", "+selectedEmployee.getFirstName(),
                    selectedJob.getDescription(),
                    (String) cbSupervisor.getValue(),
                    (String) cbAssigType.getValue()
            );

            this.staffingList.add(staff);
            util.Utility.setStaffList(this.staffingList);
            alert.setContentText("The staff was added successfully");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
        } else{
            alert.setContentText("The staff can't be added successfully");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
        util.Utility.setStaffId(util.Utility.getStaffId()+1);
        this.tf_StaffId.setText(String.valueOf(util.Utility.getStaffId()));
        cleanOnAction(actionEvent);
    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        date.setValue(null);
        cbEmployee.getSelectionModel().clearSelection();
        cbSupervisor.getSelectionModel().clearSelection();
        cbJob.getSelectionModel().clearSelection();
        cbAssigType.getSelectionModel().clearSelection();
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage( "ucr.lab.HelloApplication", "/ucr/lab/staffing.fxml",bp);
    }

    //verificar que no falte ningun dato
    private boolean staffIsValid() throws ListException {
        return  this.date.getValue() != null
                && this.cbEmployee.getValue() != null
                && this.cbJob.getValue() != null
                && this.cbSupervisor.getValue() != null
                && this.cbAssigType.getValue() != null;

    }
}