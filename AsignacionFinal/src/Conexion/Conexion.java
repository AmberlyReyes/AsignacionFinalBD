package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;


/*
public class Conexion {
	
	
	/*public static Connection getconection() {
		
		String conexionUrl= "jdbc:sqlserver://localhost:1433;"
				+"database=Academica;"+"user:LAPTOP-036AI9G5\\PC;"
				+"password:*"+"loginTimeOut=30";
		try {
			Connection con= DriverManager.getConnection(conexionUrl);
			return con;
			
		}catch(SQLException ex){
			System.out.print(ex.toString());
			return null;
			
		}
	}*/
	/*Connection conectar=null;
	
	String usuario="MGMZ";
	String contrasenna="1234";
	String bd="Academica";
	String ip= "localhost";
	String puerto= "1433";
	
	String cadena= "jdbc:sqlserver://"+ip+":"+puerto+"/"+bd;
	
	public Connection establecerConexion () {
		
		try {
			String cadena="jdbc:sqlserver://localhost"+ puerto+";"+"dataBaseName="+bd;
			conectar= DriverManager.getConnection(cadena, usuario, contrasenna);
			
			
		}catch (Exception e) {
			
			System.out.println("Se produjo un error al conectar a la base de datos: " + e.getMessage());
			
		}
		return conectar;
		
	}*/
	
	/*public static void main(String[] args) {
		// TODO code application logic here
		
		String conexion= "jdbc:sqlserver://localhost:1443;" + "databaseName=Academica";
		Connection conector = null;
		
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conector= DriverManager.getConnection (conexion, "MGMZ", "1234"); 
			System.out.println("conexion exitosa ");
		}
		catch (Exception e) {
			System.out.println("error de conexion ");
		}
	}

}
*/


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
