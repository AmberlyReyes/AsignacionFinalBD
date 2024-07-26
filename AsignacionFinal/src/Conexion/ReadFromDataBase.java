package Conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadFromDataBase {
    public static void main(String[] args) {
        try {
            Connection connection = Conexion.getConnection();
            String query = "SELECT IdEstudiante, Nombre, Apellido FROM Estudiante";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Suponiendo que la tabla tiene columnas 'id', 'name', 'email'
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Email: " + lastname);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
