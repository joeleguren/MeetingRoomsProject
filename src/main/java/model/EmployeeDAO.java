package model;

import utils.DAOConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDAO {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

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
