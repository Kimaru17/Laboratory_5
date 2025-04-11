package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class EmployeeRemove
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
        alert = util.FXUtility.alert("Employee List", "Remove Employee");
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) throws ListException {
        if (isValid()) {
            Employee employee = new Employee( Integer.parseInt(this.tf_employeeId.getText()));

            if (employeeList.contains(employee)) {
                employeeList.remove(employee);
                util.Utility.setEmployeeList(employeeList);

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("The employee was removed successfully");
                this.tf_employeeId.setText("");
                alert.showAndWait();

                // Si está vacía, volver a la página principal
                if (employeeList.isEmpty()) {
//                    util.FXUtility.loadPage("ucr.lab.HelloApplication", "employee.fxml", bp);
                    util.FXUtility.loadPage("lab5.laboratory5.HelloApplication", "employee.fxml", bp);
                }
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("The employee is already deleted or doesn't exist");
                alert.showAndWait();
            }
        } else {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid employee ID");
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