package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class EmployeeGetNext
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
        alert = util.FXUtility.alert("Employee List", "Get Next Employee");
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

    @javafx.fxml.FXML
    public void getNextOnAction(ActionEvent actionEvent) throws ListException {
        try {
            if (isValid()) {
                Employee employee = new Employee(
                        Integer.parseInt(this.tf_employeeId.getText())
                );
                if (!this.employeeList.contains(employee)){
                    alert.setContentText("That employee id doesn't exists in the list.");
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.showAndWait();
                    return;
                }


                Object current = employeeList.getObject(Integer.parseInt(this.tf_employeeId.getText()));
                Object next = employeeList.getNext(current);

                if (next != null) {
                    alert.setContentText("The next employee in the list is: \n" + next);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                } else {
                    alert.setContentText("There is no next employee.");
                    alert.setAlertType(Alert.AlertType.WARNING);
                }
                alert.showAndWait();
            }
        } catch (ListException e) {
            alert.setContentText("The employee list doesn't exist or there's an error accessing it.");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    //verificar que la lista y el tf no esten vacios
    private boolean isValid(){
        return !(this.tf_employeeId.getText().isEmpty() && employeeList.isEmpty()) && util.Utility.isInteger(this.tf_employeeId.getText());
    }
}