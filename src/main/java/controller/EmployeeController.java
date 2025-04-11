package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import domain.SinglyLinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.Date;

public class EmployeeController
{
    @FXML
    private BorderPane bp;
    @FXML
    private TableView <Employee> employeeTableview;
    @FXML
    private TableColumn <Employee, Integer> idTableColumn;
    @FXML
    private TableColumn <Employee, String> titleTableColumn;
    @FXML
    private TableColumn <Employee, String> firstNameTableColumn;
    @FXML
    private TableColumn <Employee, String> lastNameTableColumn;
    @FXML
    private TableColumn <Employee, Date> birthdayTableColumn;
    //defino la lista enlazada interna
    private CircularLinkedList employeeList;
    private Alert alert; //para el manejo de alertas

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.employeeList = util.Utility.getEmployeeList();
        alert = util.FXUtility.alert("Employee List", "Display Employee");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        birthdayTableColumn.setCellValueFactory(new PropertyValueFactory<>("Birthday"));
        try{
            if(employeeList!=null && !employeeList.isEmpty()){
                for(int i=1; i<=employeeList.size(); i++) {
                    employeeTableview.getItems().add((Employee) employeeList.getNode(i).data);
                }
            }
        }catch(ListException ex){
            alert.setContentText("Employee list is empty");
            alert.showAndWait();
        }

    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.employeeList.clear();
        util.Utility.setEmployeeList(this.employeeList); //actualizo la lista general
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
    public void removeOnAction(ActionEvent actionEvent) {
        if (tableViewIsNotEmpty()){
//            util.FXUtility.loadPage("ucr.lab.HelloApplication", "employeeRemove.fxml", bp);
            util.FXUtility.loadPage("lab5.laboratory5.HelloApplication", "employeeRemove.fxml", bp);

        }else {
            alert.setContentText("The employee list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
//        util.FXUtility.loadPage("ucr.lab.HelloApplication", "employeeAdd.fxml", bp);
        util.FXUtility.loadPage("lab5.laboratory5.HelloApplication", "employeeAdd.fxml", bp);
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        if (tableViewIsNotEmpty()){
//            util.FXUtility.loadPage("ucr.lab.HelloApplication", "employeeContains.fxml", bp);
            util.FXUtility.loadPage("lab5.laboratory5.HelloApplication", "employeeContains.fxml", bp);
        }else {
            alert.setContentText("The employee list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) throws ListException {
        if(tableViewIsNotEmpty()) {
            alert.setContentText("The size of the employee list is: \n" + employeeList.size());
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
        }
        else {
            alert.setContentText("The employee list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void removeLastOnAction(ActionEvent actionEvent)throws ListException {
        if (tableViewIsNotEmpty() ){
            if (employeeList.size()==1){
                employeeList.clear();
                employeeTableview.getItems().clear();
                initialize();
            }
            else {
                employeeList.removeLast();
                employeeTableview.getItems().clear();
                initialize();
            }
        } else {
            alert.setContentText("The employee list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
        if (tableViewIsNotEmpty()){
//            util.FXUtility.loadPage("ucr.lab.HelloApplication", "employeeGetPrev.fxml", bp);
            util.FXUtility.loadPage("lab5.laboratory5.HelloApplication", "employeeGetPrev.fxml", bp);
        }else {
            alert.setContentText("The employee list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void sortByIdOnAction(ActionEvent actionEvent) throws ListException {
        if (tableViewIsNotEmpty()){
            this.employeeList.sortById();
            util.Utility.setEmployeeList(this.employeeList);
            alert.setContentText("The registration list was sorted by the student name");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
            employeeTableview.getItems().clear();
            initialize();
        }else {
            alert.setContentText("The employee list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void sortByNameOnAction(ActionEvent actionEvent) throws ListException {
        if (tableViewIsNotEmpty()){
            this.employeeList.sortByFirstName();
            util.Utility.setEmployeeList(this.employeeList);
            alert.setContentText("The registration list was sorted by the student name");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
            employeeTableview.getItems().clear();
            initialize();
        }else {
            alert.setContentText("The employee list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void getNextOnAction(ActionEvent actionEvent) {
        if (tableViewIsNotEmpty()){
//            util.FXUtility.loadPage("ucr.lab.HelloApplication", "employeeGetNext.fxml", bp);
            util.FXUtility.loadPage("lab5.laboratory5.HelloApplication", "employeeGetNext.fxml", bp);
        }else {
            alert.setContentText("The employee list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }
    // verificacion de contenido en el TableView
    private boolean tableViewIsNotEmpty(){
        return !(this.employeeTableview.getItems().isEmpty());
    }

    private void updateTableView() throws ListException {
        this.employeeTableview.getItems().clear(); //clear table
        this.employeeList = util.Utility.getEmployeeList(); //cargo la lista
        if(employeeList!=null && !employeeList.isEmpty()){
            for(int i=1; i<=employeeList.size(); i++) {
                this.employeeTableview.getItems().add((Employee) employeeList.getNode(i).data);
            }
        }
    }

}