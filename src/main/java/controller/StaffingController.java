package controller;

import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

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

        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        employeeIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        jobPositionTableColumn.setCellValueFactory(new PropertyValueFactory<>("JobPosition"));
        supervisorTableColumn.setCellValueFactory(new PropertyValueFactory<>("Supervisor"));
        assignmentTableColumn.setCellValueFactory(new PropertyValueFactory<>("Assignment"));

        try {
            if (staffingList!=null && !staffingList.isEmpty()){
                for (int i = 0; i <=staffingList.size() ; i++) {
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
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage( "ucr.lab.HelloApplication",
                "/ucr/lab/staffingAdd.fxml",
                bp);
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void sortByEmpIdOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void sortByEmpNameOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void sortByJobOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void sortByTypeOnAction(ActionEvent actionEvent) {
    }

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

}