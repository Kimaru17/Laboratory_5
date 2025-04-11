package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class EmployeeAdd
{
    @javafx.fxml.FXML
    private DatePicker birthday;
    @javafx.fxml.FXML
    private TextField tf_firstName;
    @javafx.fxml.FXML
    private TextField tf_lastName;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextField tf_employeeId;
    @javafx.fxml.FXML
    private TextField tf_title;
    //defino la lista enlazada interna
    private CircularLinkedList employeeList;
    private Alert alert; //para el manejo de alertas


    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.employeeList = util.Utility.getEmployeeList();
        alert = util.FXUtility.alert("Employee List", "Add Employee");
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) throws ListException {
        if (employeeIsValid()){
            int employeeId = Integer.parseInt(this.tf_employeeId.getText());
            boolean idExists = false;
            if (!employeeList.isEmpty()) {
                for (int i = 1; i <= employeeList.size(); i++) {
                    Employee existingEmployee = (Employee) employeeList.getNode(i).data;
                    if (existingEmployee.getId() == employeeId) {
                        idExists = true;
                        break;
                    }
                }
            }
            if (idExists) {
                alert.setContentText("The employee Id is already registered ");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.showAndWait();
                return;
            }
            LocalDateTime localDateTime = LocalDateTime.of(birthday.getValue(), LocalTime.now());
            Date birthday = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

            Employee employee = new Employee(
                    Integer.parseInt(tf_employeeId.getText()),
                    tf_lastName.getText(),
                    tf_firstName.getText(),
                    tf_title.getText(),
                    birthday
            );
            this.employeeList.add(employee);
            util.Utility.setEmployeeList(this.employeeList);
            alert.setContentText("The employee was added successfully");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
        } else{
            alert.setContentText("The employee can't be added successfully");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        this.tf_employeeId.setText("");
        this.tf_lastName.setText("");
        this.tf_firstName.setText("");
        this.tf_title.setText("");
        birthday.setValue(null);
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
//        util.FXUtility.loadPage("ucr.lab.HelloApplication", "employee.fxml", bp);
        util.FXUtility.loadPage("lab5.laboratory5.HelloApplication", "employee.fxml", bp);
    }

    //verificar que no falte ningun dato en los tf
    private boolean employeeIsValid() throws ListException {
        return !(this.tf_employeeId.getText().isEmpty()) && !(this.tf_lastName.getText().isEmpty())
                && !(this.tf_firstName.getText().isEmpty()) && !(this.tf_title.getText().isEmpty()) && util.Utility.isInteger(this.tf_employeeId.getText());
    }
}