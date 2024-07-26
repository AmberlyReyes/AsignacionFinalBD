package Logico;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conexion.Conexion;

public class StudentSchedule {
    public static ResultSet getSchedule(String studentId, String date) throws SQLException {
        Connection conn = Conexion.getConnection();
        String sql = "EXEC sp_GenerarHorarioEstudiante @IdEstudiante = '10304050', @IdPeriodo = '1870'";
        CallableStatement stmt = conn.prepareCall(sql);
        stmt.setString(1, studentId);
        stmt.setString(2, date);
        return stmt.executeQuery();
    }
}