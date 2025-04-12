package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
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
        aplicarFormatoDatePicker(birthday);
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
        birthday.getEditor().clear();
        birthday.setValue(null);
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "/ucr/lab/employee.fxml", bp);
    }

    //verificar que no falte ningun dato en los tf
    private boolean employeeIsValid() throws ListException {
        boolean error=false;
        LocalDate today = LocalDate.now();
        if (birthday.getValue() == null || birthday.getValue().isAfter(today)) {
            error=true;
        }
        return !(this.tf_employeeId.getText().isEmpty()) && !(this.tf_lastName.getText().isEmpty())
                && !(this.tf_firstName.getText().isEmpty()) && !(this.tf_title.getText().isEmpty()) && util.Utility.isInteger(this.tf_employeeId.getText())
                && !error;
    }

    private void aplicarFormatoDatePicker(DatePicker datePicker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        datePicker.setPromptText("dd/MM/yyyy");

        datePicker.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                try {
                    return LocalDate.parse(string, formatter);
                } catch (DateTimeParseException e) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Invalid date format. Use dd/MM/yyyy.\nExample: 10/04/2003");
                    alert.showAndWait();
                    datePicker.getEditor().clear();
                    datePicker.setValue(null);
                    return null;
                }
            }
        });

        // Forzar validación al perder el foco
        datePicker.getEditor().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                String text = datePicker.getEditor().getText();
                if (text != null && !text.trim().isEmpty()) {
                    try {
                        LocalDate parsedDate = LocalDate.parse(text, formatter);
                        datePicker.setValue(parsedDate);
                    } catch (DateTimeParseException e) {
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.setContentText("Invalid date format. Use dd/MM/yyyy.\nExample: 10/04/2003");
                        alert.showAndWait();
                        datePicker.getEditor().clear();
                        datePicker.setValue(null);
                    }
                }
            }
        });
    }



}
