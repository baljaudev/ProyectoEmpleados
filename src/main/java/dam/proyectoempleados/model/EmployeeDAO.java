package dam.proyectoempleados.model;

import dam.proyectoempleados.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class EmployeeDAO {
    //*******************************
    //Selecciona un empleado (CUANDO DAMOS A SELECCIONASR UN EMPLEADO PONIENDO ID EN LA APLICACIÓN)
    //*******************************
    public static Employee searchEmployee (String empId) throws SQLException, ClassNotFoundException, ParseException {
        //Declara la sentencia SELECT
        String selectStmt = "SELECT * FROM employees WHERE employee_id=" + empId;
        //Ejecuta la sentencia SELECT
        try {
            //Obtener ResultSet del método dbExecuteQuery
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);
            //Enviar el ResultSet al método getEmployeeFromResultSet y obtener el objeto empleado
            Employee employee = getEmployeeFromResultSet(rsEmp);
            //Retornar el objeto empleado
            return employee;
        } catch (SQLException e) {
            System.out.println("Mientras se buscaba un empleado con el id " + empId + ", se produjo un error: " + e);
            //Retorna la excepción
            throw e;
        }
    }
    //Utiliza el ResultSet de la BD como parámetro y establece los atributos del objeto empleado y devuelve el objeto empleado.
    //PARA CARGAR UN SOLO EMPLEADO EN LA TABLA CUANDO LO SELECCIONAS EN LA APLICACIÓN
    private static Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException, ParseException {
        Employee emp = null;
        if (rs.next()) {
            emp = new Employee();
            emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
            emp.setFirstName(rs.getString("FIRST_NAME"));
            emp.setLastName(rs.getString("LAST_NAME"));
            emp.setEmail(rs.getString("EMAIL"));
            emp.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            emp.setHireDate(Date.valueOf(rs.getString("HIRE_DATE")));
            emp.setJobId(rs.getString("JOB_ID"));
            emp.setSalary(rs.getInt("SALARY"));
            emp.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
            emp.setManagerId(rs.getInt("MANAGER_ID"));
            emp.setDepartmantId(rs.getInt("DEPARTMENT_ID"));
        }
        return emp;
    }
    //*******************************
    //Selecciona empleados FUNCION MOSTRAR TODOS EN LA TABLA CUANDO EN LA APLICACIÓN MOSTRAMOS TODOS
    //*******************************
    public static ObservableList<Employee> searchEmployees () throws SQLException, ClassNotFoundException {
        //Declarar la sentencia SELECT
        String selectStmt = "SELECT * FROM employees";
        //Ejecuta la sentencia SELECT
        try {
            //Obtener el ResultSet del método dbExecuteQuery
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);
            //Enviar el ResultSet al método getEmployeeList y obtener el objeto empleado
            ObservableList<Employee> empList = getEmployeeList(rsEmps);
            //Retorna el Objeto empleado
            return empList;
        } catch (SQLException e) {
            System.out.println("La sentencia SQL ha fallado: " + e);
            //Retorna una excepción
            throw e;
        }
    }

    //Seleccione * de la operación empleados CARGA LA TABLA DE LA APLICACIÓN CON TODOS LOS EMPLEADOS
    private static ObservableList<Employee> getEmployeeList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declarar la ObservableList que comprende los objetos Empleados
        ObservableList<Employee> empList = FXCollections.observableArrayList();
        while (rs.next()) {
            Employee emp = new Employee();
            emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
            emp.setFirstName(rs.getString("FIRST_NAME"));
            emp.setLastName(rs.getString("LAST_NAME"));
            emp.setEmail(rs.getString("EMAIL"));
            emp.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            emp.setHireDate(Date.valueOf(rs.getString("HIRE_DATE")));
            emp.setJobId(rs.getString("JOB_ID"));
            emp.setSalary(rs.getInt("SALARY"));
            emp.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
            emp.setManagerId(rs.getInt("MANAGER_ID"));
            emp.setDepartmantId(rs.getInt("DEPARTMENT_ID"));
            //Añade el empleado a la ObservableList
            empList.add(emp);
        }
        //retorna empList (ObservableList de Empleados)
        return empList;
    }
    //*************************************
    //UPDATE de la dirección de correo electrónico de un empleado
    //*************************************
    public static void updateEmpEmail (String empId, String empEmail) throws SQLException, ClassNotFoundException {
        //Declara un UPDATE
        String updateStmt =
            "UPDATE employees " +
            "SET EMAIL = '" + empEmail + "' " +
            "WHERE EMPLOYEE_ID = " + empId + ";";
        //Ejecuta la operación UPDATE
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.println("Error mientras se hacía la operación UPDATE: " + e);
            throw e;
        }
    }
    //*************************************
    //DELETE un empleado
    //*************************************
    public static void deleteEmpWithId (String empId) throws SQLException, ClassNotFoundException {
        //Declara la operación DELETE
        String updateStmt =
            "DELETE FROM employees " +
            "WHERE employee_id = "
            + empId;
        //Ejecuta la operación UPDATE
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.println("Error mientras se hacía la operación DELETE: " + e);
            throw e;
        }
    }
    //*************************************
    //INSERT empleado
    //*************************************
    public static void insertEmp(String name, String lastname, String email, String phone, String salary, String commission) throws SQLException, ClassNotFoundException {
        //Declara una operación INSERT
        String updateStmt =
            "INSERT INTO employees " +
            "(FIRST_NAME, LAST_NAME, EMAIL,PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT,DEPARTMENT_ID) " +
            "VALUES " +
            "('" + name + "', '"
                    + lastname + "', '" + email + "','" + phone + "', DATE(), 'IT_PROG'," + salary + "," + commission + ",12);";
        //Ejecuta la operación UPDATE
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.println("Error mientras se hacía la operación INSERT: " + e);
            throw e;
        }
    }
}
