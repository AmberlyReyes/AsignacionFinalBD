package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;


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
	Connection conectar=null;
	
	String usuario="MGMZ";
	String contrasenna="1234";
	String bd="Academica";
	String ip= "localhost";
	String puerto= "1433";
	
	String cadena= "jdbc:sqlserver://"+ip+":"+puerto+"/"+bd;
	
	public Connection establecerConexion () {
		
		try {
			String cadena="jdbc:sqlserver://localhost"+ puerto+";"+"dataBaseName="+bd;
			conectar= DriverManager.getConnection(cadena,usuario,contrasenna);
			
			
		}catch (Exception e) {
			
			System.out.println("Se produjo un error al conectar a la base de datos: " + e.getMessage());
			
		}
		return conectar;
		
	}

}
