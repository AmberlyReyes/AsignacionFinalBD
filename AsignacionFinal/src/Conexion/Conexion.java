package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Logico.Estudiante;
import Logico.Grupo;
import Logico.Horario;



public class Conexion {
	private static Connection cn;
	public static Connection getConnection(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			cn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databasename= Academica", "MGMZ","1234");
		} catch (Exception e) {
			cn=null;
		}
			return cn;
		}
		public static void main(String[] args){
		Connection pruebaCn= Conexion.getConnection();
		if(pruebaCn!=null){
			System.out.println("Conectado"); 
			System.out.println(pruebaCn);
		}else{
			System.out.println("Desconectado");
		}
	}

		
		public ArrayList<Estudiante> obtenerEstudiantes() {
	        ArrayList<Estudiante> estudiantes = new ArrayList<>();
	        String query = "SELECT * FROM Estudiante";
	        try (PreparedStatement stmt = cn.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                Estudiante estudiante = new Estudiante();
	                estudiante.setIdEstudiante(rs.getString("IdEstudiante"));
	                estudiante.setNombre(rs.getString("Nombre"));
	                estudiante.setApellido(rs.getString("Apellido"));
	                // Set other fields as needed
	                estudiantes.add(estudiante);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return estudiantes;
	    }

	    public ArrayList<Grupo> obtenerGruposDisponibles() {
	        ArrayList<Grupo> grupos = new ArrayList<>();
	        String query = "SELECT * FROM Grupo WHERE Cupo > 0";
	        try (PreparedStatement stmt = cn.prepareStatement(query); 
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                Grupo grupo = new Grupo();
	                grupo.setIdGrupo(rs.getString("IdGrupo"));
	                grupo.setIdAsignatura(rs.getString("IdAsignatura"));
	                grupo.setIdPeriodo(rs.getString("IdPeriodo"));
	                // Set other fields as needed
	                grupos.add(grupo);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return grupos;
	    }

	    public ArrayList<Grupo> obtenerGruposInscritos(Estudiante estudiante) {
	        ArrayList<Grupo> grupos = new ArrayList<>();
	        String query = "SELECT * FROM GrupoInscrito WHERE IdEstudiante = ?";
	        try (PreparedStatement stmt = cn.prepareStatement(query)) {
	            stmt.setString(1, estudiante.getIdEstudiante());
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    Grupo grupo = new Grupo();
	                    grupo.setIdGrupo(rs.getString("IdGrupo"));
	                    grupo.setIdAsignatura(rs.getString("IdAsignatura"));
	                    grupo.setIdPeriodo(rs.getString("IdPeriodo"));
	                    // Set other fields as needed
	                    grupos.add(grupo);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return grupos;
	    }

	    public Horario obtenerHorarioGrupo(Grupo grupo) {
	        Horario horario = new Horario();
	        String query = "SELECT * FROM Horario WHERE IdGrupo = ?";
	        try (PreparedStatement stmt = cn.prepareStatement(query)) {
	            stmt.setString(1, grupo.getIdGrupo());
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    horario.setIdPeriodo(rs.getString("IdHorario"));
	                    horario.setIdGrupo(rs.getString("IdGrupo"));
	                    horario.setNumDia(rs.getInt("NumDia"));
	                    horario.setFechaHoraInicio(rs.getTimestamp("FechaHoraInicio").toLocalDateTime());
	                    horario.setFechaHoraFin(rs.getTimestamp("FechaHoraFin").toLocalDateTime());
	                    // Set other fields as needed
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return horario;
	    }

	    public void inscribirGrupo(Estudiante estudiante, Grupo grupo) {
	        String query = "INSERT INTO GrupoInscrito (IdEstudiante, IdGrupo, IdPeriodo, IdAsignatura) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement stmt = cn.prepareStatement(query)) {
	            stmt.setString(1, estudiante.getIdEstudiante());
	            stmt.setString(2, grupo.getIdGrupo());
	            stmt.setString(3, grupo.getIdPeriodo());
	            stmt.setString(4, grupo.getIdAsignatura());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public boolean agregarGrupoInscrito(Estudiante estudiante, Grupo grupo) {
	        String query = "INSERT INTO GrupoInscrito (IdEstudiante, IdGrupo) VALUES (?, ?)";
	        try (PreparedStatement stmt = cn.prepareStatement(query)) {
	            stmt.setString(1, estudiante.getIdEstudiante());
	            stmt.setString(2, grupo.getIdGrupo());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return false;
	    }

	    public boolean eliminarGrupoInscrito(Estudiante estudiante, Grupo grupo) {
	        String query = "DELETE FROM GrupoInscrito WHERE IdEstudiante = ? AND IdGrupo = ?";
	        try (PreparedStatement stmt = cn.prepareStatement(query)) {
	            stmt.setString(1, estudiante.getIdEstudiante());
	            stmt.setString(2, grupo.getIdGrupo());
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return false;
	    }
		
	}

