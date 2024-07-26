package Conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadFromDataBase {
    public static void main(String[] args) {
        try {
            Connection connection = Conexion.getConnection(); // Asume que tienes una clase Conexion con este método
            String query = "SELECT IdEstudiante, Nombre, Apellido FROM Estudiante";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Usar los nombres correctos de las columnas
                //String id = resultSet.getString("IdEstudiante");
                //String name = resultSet.getString("Nombre");
                //String lastname = resultSet.getString("Apellido");

                //System.out.println("ID: " + id);
                //System.out.println("Nombre: " + name);
                //System.out.println("Apellido: " + lastname);
            	System.out.println(resultSet.getString("IdEstudiante"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}





