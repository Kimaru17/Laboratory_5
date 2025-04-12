package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.Date;
import java.util.Optional;

public class EmployeeController
{
    @javafx.fxml.FXML
    private TableView<Employee> employeeTableview;
    @javafx.fxml.FXML
    private TableColumn<Employee, Integer> idTableColumn;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TableColumn <Employee, String> titleTableColumn;
    @javafx.fxml.FXML
    private TableColumn <Employee, String> firstNameTableColumn;
    @javafx.fxml.FXML
    private TableColumn <Employee, String> lastNameTableColumn;
    @javafx.fxml.FXML
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

        birthdayTableColumn.setCellFactory(column -> new javafx.scene.control.TableCell<>() {
            private final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });

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
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Remove Employee");
            dialog.setHeaderText("Enter the employee ID to remove:");
            dialog.setContentText("Employee ID:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(input -> {
                if (input.isEmpty() || !util.Utility.isInteger(input)) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Invalid input. Please enter a valid employee ID.");
                    alert.showAndWait();
                    return;
                }

                try {
                    int id = Integer.parseInt(input);
                    Employee employee = new Employee(id);

                    if (employeeList.contains(employee)) {
                        employeeList.remove(employee);
                        util.Utility.setEmployeeList(employeeList);

                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("The employee was removed successfully.");
                        alert.showAndWait();
                        employeeTableview.getItems().clear();
                        initialize();


                        if (employeeList.isEmpty()) {
                            util.FXUtility.loadPage("ucr.lab.HelloApplication", "/ucr/lab/employee.fxml", bp);
                        }
                    } else {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("The employee doesn't exist or was already deleted.");
                        alert.showAndWait();
                    }

                } catch (ListException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Error accessing the list: " + e.getMessage());
                    alert.showAndWait();
                }
            });
        }else {
            alert.setContentText("The employee list is empty");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        //util.FXUtility.loadPage("ucr.lab.HelloApplication", "employeeAdd.fxml", bp);
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "/ucr/lab/employeeAdd.fxml", bp);
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        if (tableViewIsNotEmpty()){
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Contains Employee");
            dialog.setHeaderText("Enter the employee ID:");
            dialog.setContentText("Employee ID:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(input -> {
                if (input.isEmpty() || !util.Utility.isInteger(input)) {
                    alert.setContentText("Invalid input. Please enter a valid numeric employee ID.");
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.showAndWait();
                    return;
                }

                Employee employee = new Employee(Integer.parseInt(input));

                boolean exists = false;

                try {
                    exists = employeeList.contains(employee);
                } catch (ListException e) {
                    throw new RuntimeException(e);
                }

                alert.setContentText(exists
                        ? "The employee exists in the list."
                        : "The employee doesn't exist.");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.showAndWait();
            });
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
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Get Previous Employee");
            dialog.setHeaderText("Enter the employee ID:");
            dialog.setContentText("Employee ID:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(input -> {
                if (input.isEmpty() || !util.Utility.isInteger(input)) {
                    alert.setContentText("Invalid input. Please enter a valid numeric employee ID.");
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.showAndWait();
                    return;
                }

                try {
                    int id = Integer.parseInt(input);
                    Employee employee = new Employee(id);

                    if (!employeeList.contains(employee)) {
                        alert.setContentText("That employee ID doesn't exist in the list.");
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.showAndWait();
                        return;
                    }

                    Object current = employeeList.getObject(id);
                    Object prev = employeeList.getPrev(current);

                    if (prev != null) {
                        alert.setAlertType(Alert.AlertType.INFORMATION);

                        TextArea textArea = new TextArea("The previous employee in the list is:\n" + prev);
                        textArea.setEditable(false);
                        textArea.setWrapText(true);
                        textArea.setMaxWidth(Double.MAX_VALUE);
                        textArea.setMaxHeight(Double.MAX_VALUE);

                        alert.getDialogPane().setContent(textArea);
                    } else {
                        alert.setContentText("There is no previous employee.");
                        alert.setAlertType(Alert.AlertType.WARNING);
                    }

                    alert.showAndWait();

                } catch (ListException e) {
                    alert.setContentText("Error accessing the list: " + e.getMessage());
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.showAndWait();
                }
            });
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
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Get Next Employee");
            dialog.setHeaderText("Enter the employee ID:");
            dialog.setContentText("Employee ID:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(input -> {
                if (input.isEmpty() || !util.Utility.isInteger(input)) {
                    alert.setContentText("Invalid input. Please enter a valid numeric employee ID.");
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.showAndWait();
                    return;
                }

                try {
                    int id = Integer.parseInt(input);
                    Employee employee = new Employee(id);

                    if (!employeeList.contains(employee)) {
                        alert.setContentText("That employee ID doesn't exist in the list.");
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.showAndWait();
                        return;
                    }

                    Object current = employeeList.getObject(id);
                    Object next = employeeList.getNext(current);

                    if (next != null) {
                        alert.setAlertType(Alert.AlertType.INFORMATION);

                        TextArea textArea = new TextArea("The next employee in the list is:\n" + next);
                        textArea.setEditable(false);
                        textArea.setWrapText(true);
                        textArea.setMaxWidth(Double.MAX_VALUE);
                        textArea.setMaxHeight(Double.MAX_VALUE);

                        alert.getDialogPane().setContent(textArea);
                    } else {
                        alert.setContentText("There is no next employee.");
                        alert.setAlertType(Alert.AlertType.WARNING);
                    }

                    alert.showAndWait();

                } catch (ListException e) {
                    alert.setContentText("Error accessing the list: " + e.getMessage());
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.showAndWait();
                }
            });
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

    public void updateTableView() throws ListException {
        this.employeeTableview.getItems().clear(); //clear table
        this.employeeList = util.Utility.getEmployeeList(); //cargo la lista
        if(employeeList!=null && !employeeList.isEmpty()){
            for(int i=1; i<=employeeList.size(); i++) {
                this.employeeTableview.getItems().add((Employee) employeeList.getNode(i).data);
            }
        }
    }

}