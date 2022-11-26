package dam.proyectoempleados.controller;

import dam.proyectoempleados.model.Employee;
import dam.proyectoempleados.model.EmployeeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.sql.SQLException;

public class EmployeeController {
    @FXML
    private TextField empIdText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField newEmailText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField emailText;
    @FXML
    private TableView employeeTable;
    @FXML
    private TableColumn<Employee, Integer>  empIdColumn;
    @FXML
    private TableColumn<Employee, String>  empNameColumn;
    @FXML
    private TableColumn<Employee, String> empLastNameColumn;
    @FXML
    private TableColumn<Employee, String> empEmailColumn;
    @FXML
    private TableColumn<Employee, String> empPhoneNumberColumn;
    @FXML
    private TableColumn<Employee, Date> empHireDateColumn;
    //Buscar empleado
    @FXML
    private void searchEmployee (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Obtener información de Employee
            Employee emp = EmployeeDAO.searchEmployee(empIdText.getText());
            //Rellenar el empleado en el TableView y mostrarlo en el TextArea
            populateAndShowEmployee(emp);
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error occurred while getting employee information from DB.\n" + e);
            throw e;
        }
    }
    //Selecciona todos los empleados
    @FXML
    private void searchEmployees(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Obtenga toda la información de los empleados
            ObservableList<Employee> empData = EmployeeDAO.searchEmployees();
            //Rellenar empleados TableView
            populateEmployees(empData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }
    //Inicializar la clase controlador
    //Este método se llama automáticamente después de cargar el archivo fxml.
    @FXML
    private void initialize () {
        /*
         El setCellValueFactory(...) que establecemos en las columnas de la tabla se utiliza para determinar
        qué campo dentro de los objetos Employee debe ser utilizado para la columna en particular.
        La flecha -> indica que estamos utilizando una característica de Java 8 llamada Lambdas.
        (Otra opción sería utilizar un PropertyValueFactory, pero esto no es seguro.
        En este ejemplo sólo estamos utilizando valores StringProperty para las columnas de nuestra tabla.
        Si quieres usar IntegerProperty o DoubleProperty, el setCellValueFactory(...)
        debe tener un asObject() adicional:
        */

        empIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
        empNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        empEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        empPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        empHireDateColumn.setCellValueFactory(cellData -> cellData.getValue().hireDateProperty());
    }
    //Introducir Employee
    @FXML
    private void populateEmployee (Employee emp) throws ClassNotFoundException {
        //Declarar la ObservableList para table view
        ObservableList<Employee> empData = FXCollections.observableArrayList();
        //Añadir empleado a la ObservableList
        empData.add(emp);
        //Establecer los elementos en el employeeTable
        employeeTable.setItems(empData);
    }
    //Establecer la información de Employee en el Text Area
    @FXML
    private void setEmpInfoToTextArea ( Employee emp) {
        resultArea.setText("First Name: " + emp.getFirstName() + "\n" +
                "Last Name: " + emp.getLastName());
    }
    //Rellenar Employee para el TableView y mostrar Employee en el TextArea
    @FXML
    private void populateAndShowEmployee(Employee emp) throws ClassNotFoundException {
        if (emp != null) {
            populateEmployee(emp);
            setEmpInfoToTextArea(emp);
        } else {
            resultArea.setText("This employee does not exist!\n");
        }
    }
    //Rellenar empleados para TableView
    @FXML
    private void populateEmployees (ObservableList<Employee> empData) throws ClassNotFoundException {
        //Set items to the employeeTable
        employeeTable.setItems(empData);
    }
    //Actualizar el correo electrónico del empleado con el correo electrónico que se escribe en el campo newEmailText
    @FXML
    private void updateEmployeeEmail (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.updateEmpEmail(empIdText.getText(),newEmailText.getText());
            resultArea.setText("Email has been updated for, employee id: " + empIdText.getText() + "\n");
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while updating email: " + e);
        }
    }
    //Insertar empleado en la base de datos
    @FXML
    private void insertEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.insertEmp(nameText.getText(),surnameText.getText(),emailText.getText());
            resultArea.setText("Employee inserted! \n");
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while inserting employee " + e);
            throw e;
        }
    }
    //Borrar de la base de datos un empleado con un Id de empleado determinado
    @FXML
    private void deleteEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.deleteEmpWithId(empIdText.getText());
            resultArea.setText("Employee deleted! Employee id: " + empIdText.getText() + "\n");
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while deleting employee " + e);
            throw e;
        }
    }
}
