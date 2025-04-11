package controller;

import domain.*;
import domain.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class EmployeeController
{
    @javafx.fxml.FXML
    private TableView employeeTableview;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> idTableColumn;
    private TableColumn<Employee, String> lastNameTableColumn;
    private TableColumn<Employee, String> firstNameTableColumn;
    private TableColumn<Employee, String> tittleTableColumn;
    private TableColumn<Employee, String> birthdayTableColumn;

    //defino la lista enlazada interna
    private CircularLinkedList EmployeeList;
    private Alert alert; //para el manejo de alertas
    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.EmployeeList = util.Utility.getEmployeeList();
        alert = util.FXUtility.alert("Employee List", "Display Employee");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        tittleTableColumn.setCellValueFactory(new PropertyValueFactory<>("Tittle"));
        try{
            if(EmployeeList!=null && !EmployeeList.isEmpty()){
                for(int i=1; i<=EmployeeList.size(); i++) {
                    employeeTableview.getItems().add((Employee) EmployeeList.getNode(i).data);
                }
            }
            //this.EmployeeTableView.setItems(observableList);
        }catch(ListException ex){
            alert.setContentText("Employee list is empty");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.EmployeeList.clear();
        util.Utility.setEmployeeList(this.EmployeeList); //actualizo la lista general
        this.alert.setContentText("The list was deleted");
        this.alert.setAlertType(Alert.AlertType.INFORMATION);
        this.alert.showAndWait();
        try {
            updateTableView(); //actualiza el contenido del tableview
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addEmployee.fxml", bp);
    }

    @Deprecated
    public void addFirstOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addFirstEmployee.fxml", bp);
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void addSortedOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void getFirstOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void removeFirstOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void getLastOnAction(ActionEvent actionEvent) {
    }

    private void updateTableView() throws ListException {
        this.employeeTableview.getItems().clear(); //clear table
        this.EmployeeList = util.Utility.getEmployeeList(); //cargo la lista
        if(EmployeeList!=null && !EmployeeList.isEmpty()){
            for(int i=1; i<=EmployeeList.size(); i++) {
                this.employeeTableview.getItems().add((Employee)EmployeeList.getNode(i).data);
            }
        }
    }

    @FXML
    public void removeLastOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void sortByIdOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void sortByNameOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void getNextOnAction(ActionEvent actionEvent) {
    }
}