package model;

import utils.DAOConstants;

import java.sql.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.TreeSet;

public class EmployeeDAO {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /**
     * Añade un nuevo empleado a la base de datos.
     * @param employee El objeto Employee que se quiere añadir.
     * @return true si el empleado se ha añadido correctamente, false si ya existe un empleado con el mismo DNI.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean addEmployee(Employee employee) throws SQLException {
        boolean isAdded = false;
        String sqlAddEmployee = "INSERT INTO employees (dni, name, email, department) VALUES (?, ?, ?, ?)";

        if(getEmployeeByDni(employee.getDni()).isPresent()) { // Comprueba si el empleado ya existe obteniendo el empleado por su DNI
            return false; // Si el empleado ya existe, no se añade
        }
        try (Connection connection = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlAddEmployee)) {

            preparedStatement.setString(1, employee.getDni());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getEmail().toLowerCase()); // Guardamos el email en minúsculas
            preparedStatement.setString(4, employee.getDepartment());

            int rowsAffected = preparedStatement.executeUpdate();
            isAdded = rowsAffected > 0; // Si se ha afectado alguna fila, el empleado se ha añadido correctamente
        }
        return isAdded;
    }

    /**
     * Devuelve un TreeSet con todos los empleados de la base de datos, ordenados por DNI.
     * @return Un TreeSet de empleados.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public TreeSet<Employee> getAllEmployees() throws SQLException {
        // Creamos un TreeSet para almacenar los empleados, ordenados por departamento y luego por nombre
        TreeSet<Employee> employes = new TreeSet<>(Comparator.comparing(Employee::getDepartment).thenComparing(Employee::getName));
        String sqlGetAllEmployees = "SELECT * FROM employees";

        try (Connection conn = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sqlGetAllEmployees);
             ResultSet rs = ps.executeQuery()) {

            // Iteramos sobre el ResultSet y añadimos cada empleado al conjunto
            while (rs.next()) {
                String dni = rs.getString("dni");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String department = rs.getString("department");

                // Creamos el objeto Employee y lo añadimos al conjunto
                employes.add(new Employee(dni, name, email.toLowerCase(), department)); // Guardamos el email en minúsculas
            }
        }
        return employes;
    }

    /**
     * Devuelve un Optional<Employee> que contiene un empleado si se encuentra en la base de datos.
     * @param dni DNI del empleado que se quiere buscar.
     * @return Optional <Employee> que contiene el empleado si se encuentra, o un Optional vacío si no se encuentra.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public Optional<Employee> getEmployeeByDni(String dni) throws SQLException {
        String sqlGetEmployee = "SELECT * FROM employees WHERE dni = ?";
        Optional<Employee> employee = Optional.empty();

        try (Connection conn = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sqlGetEmployee)) {

            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            // Si se encuentra un empleado con el DNI proporcionado, se crea un objeto Employee
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String department = rs.getString("department");

                // Creamos el objeto Employee con los datos obtenidos
                employee = Optional.of(new Employee(dni, name, email.toLowerCase(), department)); // Guardamos el email en minúsculas
            }
        }
        return employee;
    }

    /**
     * Actualiza los datos de un empleado en la base de datos.
     *
     * @param employee El objeto Employee con los nuevos datos.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean updateEmployee(Employee employee) throws SQLException {
        boolean isUpdated = false;
        String sqlUpdateEmployee = "UPDATE employees SET name = ?, email = ?, department = ? WHERE dni = ?";

        try (Connection connection = DriverManager.getConnection(DAOConstants.JDBC_URL, DAOConstants.JDBC_USER, DAOConstants.JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateEmployee)) {

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail().toLowerCase()); // Guardamos el email en minúsculas
            preparedStatement.setString(3, employee.getDepartment());
            preparedStatement.setString(4, employee.getDni());

            int rowsAffected = preparedStatement.executeUpdate();
            isUpdated = rowsAffected > 0; // Si se actualizó al menos una fila, se considera que la actualización fue exitosa
        }
        return isUpdated;
    }
}
