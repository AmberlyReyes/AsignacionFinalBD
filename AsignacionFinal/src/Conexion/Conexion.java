package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;



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
}

