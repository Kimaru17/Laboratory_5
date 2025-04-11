package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class EmployeeContains
{

    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextField tf_employeeId;
    //defino la lista enlazada interna
    private CircularLinkedList employeeList;
    private Alert alert; //para el manejo de alertas


    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.employeeList = util.Utility.getEmployeeList();
        alert = util.FXUtility.alert("Employee List", "Contains Employee");
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) throws ListException {
        if (isValid()){
            Employee employee = new Employee(
                    Integer.parseInt(this.tf_employeeId.getText())
            );

            alert.setContentText(this.employeeList.contains(employee)
                    ?"The employee exist in the list"
                    :"The employee doesn't exist"
            );
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();

        } else{
            alert.setContentText("There was an error searching. Enter a new number");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        this.tf_employeeId.setText("");
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
//        util.FXUtility.loadPage("ucr.lab.HelloApplication", "employee.fxml", bp);
        util.FXUtility.loadPage("lab5.laboratory5.HelloApplication", "employee.fxml", bp);
    }

    //verificar que la lista y el tf no esten vacios
    private boolean isValid(){
        return !(this.tf_employeeId.getText().isEmpty() && employeeList.isEmpty()) && util.Utility.isInteger(this.tf_employeeId.getText());
    }

}