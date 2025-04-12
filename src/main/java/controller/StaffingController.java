package controller;

import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import util.Utility;
import javafx.scene.control.TextInputDialog;

import java.util.Date;

public class StaffingController
{
    @FXML
    private BorderPane bp;

    @FXML
    private TableView <Staffing> staffingTableview;
    @FXML
    private TableColumn <Staffing, Integer>idTableColumn;
    @FXML
    private TableColumn <Staffing, Date>dateTableColumn;
    @FXML
    private TableColumn  <Staffing, Integer>employeeIdTableColumn;
    @FXML
    private TableColumn <Staffing, String>employeeNameTableColumn;
    @FXML
    private TableColumn <Staffing, String>jobPositionTableColumn;
    @FXML
    private TableColumn <Staffing, String>supervisorTableColumn;
    @FXML
    private TableColumn <Staffing, String>assignmentTableColumn;

    private CircularDoublyLinkedList staffingList;
    private Alert alert; //para el manejo de alertas


    @FXML
    public void initialize() {
        //Cargar lista
        this.staffingList = util.Utility.getStaffList();

        alert = util.FXUtility.alert("Employee List", "Display Employee");
        alert.setAlertType(Alert.AlertType.ERROR);

        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        employeeIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        jobPositionTableColumn.setCellValueFactory(new PropertyValueFactory<>("jobPosition"));
        supervisorTableColumn.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
        assignmentTableColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentType"));

        try {
            if (staffingList!=null && !staffingList.isEmpty()){
                for (int i = 1; i <=staffingList.size() ; i++) {
                    staffingTableview.getItems().add((Staffing) staffingList.getNode(i).data);
                    
                }
            }
        }catch (Exception e){
            alert.setContentText("Staffing list is empty");
            alert.showAndWait();
        }
    }

    @FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.staffingList.clear();
        util.Utility.setStaffId(1);
        util.Utility.setStaffList(this.staffingList);
        alert.setContentText("The list was deleted");
        this.alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.showAndWait();
        try {
            updateTableView();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        if (!staffingList.isEmpty()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Remove Staff Position");
            dialog.setHeaderText("Enter Staff Position ID:");
            dialog.setContentText("Staff:");

            dialog.showAndWait().ifPresent(input -> {
                if (!Utility.isInteger(input)) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Invalid input. Please enter a valid job position ID.");
                } else {
                    try {
                        int idToRemove = Integer.parseInt(input);
                        boolean removed = false;

                        // Buscar y eliminar por ID
                        for (int i = 1; i <= staffingList.size(); i++) {
                            Staffing current = (Staffing) staffingList.getNode(i).data;
                            if (current.getId() == idToRemove) {
                                staffingList.remove(current);
                                Utility.setStaffList(staffingList);
                                staffingTableview.getItems().remove(current);
                                removed = true;
                                break; // Salir del bucle al encontrar
                            }
                        }

                        // Mostrar alerta según resultado
                        if (removed) {
                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            alert.setContentText("The Staff position was removed successfully.");
                        } else {
                            alert.setAlertType(Alert.AlertType.ERROR);
                            alert.setContentText("Staff position does not exist.");
                        }

                    } catch (ListException e) {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("Error removing Staff position: " + e.getMessage());
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
        util.FXUtility.loadPage( "ucr.lab.HelloApplication",
                "/ucr/lab/staffingAdd.fxml",
                bp);
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) throws ListException {
        if(!this.staffingTableview.getItems().isEmpty()) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            try {
                alert.setContentText("Current size of the job position list: " + staffingList.size());
            } catch (ListException e) {
                throw new RuntimeException(e);
            }
            alert.showAndWait();
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("The Table is Empty");
        }
    }

    @javafx.fxml.FXML
    public void sortByEmpIdOnAction(ActionEvent actionEvent) {
        if (!staffingList.isEmpty()) {
        sortStaffingList("EMPLOYEE_ID");
    }else {
            alert.setContentText("The list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }}

    @javafx.fxml.FXML
    public void sortByEmpNameOnAction(ActionEvent actionEvent) {
        if (!staffingList.isEmpty()) {
            sortStaffingList("NAME");
        }else {
            alert.setContentText("The list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }}

    @javafx.fxml.FXML
    public void sortByJobOnAction(ActionEvent actionEvent) {
        if (!staffingList.isEmpty()) {
        sortStaffingList("JOB_POSITION");
        }else {
            alert.setContentText("The list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }}

    @javafx.fxml.FXML
    public void sortByTypeOnAction(ActionEvent actionEvent) {
        if (!staffingList.isEmpty()) {
        sortStaffingList("TYPE");
        }else {
            alert.setContentText("The list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }}

    private boolean tableViewIsNotEmpty(){
        return !(this.staffingTableview.getItems().isEmpty());
    }

    private void updateTableView() throws ListException {
        this.staffingTableview.getItems().clear(); //clear table
        this.staffingList = util.Utility.getStaffList(); //cargo la lista
        if(staffingList!=null && !staffingList.isEmpty()){
            for(int i=1; i<=staffingList.size(); i++) {
                this.staffingTableview.getItems().add((Staffing) staffingList.getNode(i).data);
            }
        }
    }

    private void sortStaffingList(String staffCase) {
        try {
            System.out.println("Sorting by: " + staffCase);
            Utility.staffSorting = staffCase;
            CircularDoublyLinkedList staffList = Utility.getStaffList();
            staffList.sort();

            // Actualizar la TableView
            staffingTableview.getItems().clear();
            for (int i = 1; i <= staffList.size(); i++) {
                staffingTableview.getItems().add((Staffing) staffList.getNode(i).data);
            }

        } catch (ListException e) {
            // Manejar error
        }
    }

}