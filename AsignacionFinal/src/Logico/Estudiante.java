package Logico;

public class Estudiante {
    public String getIdEstudiante() {
		return idEstudiante;
	}
	public void setIdEstudiante(String idEstudiante) {
		this.idEstudiante = idEstudiante;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getIdCarrera() {
		return idCarrera;
	}
	public void setIdCarrera(String idCarrera) {
		this.idCarrera = idCarrera;
	}
	public String getIdCategoriaPago() {
		return idCategoriaPago;
	}
	public void setIdCategoriaPago(String idCategoriaPago) {
		this.idCategoriaPago = idCategoriaPago;
	}
	public String getIdNacionalidad() {
		return idNacionalidad;
	}
	public void setIdNacionalidad(String idNacionalidad) {
		this.idNacionalidad = idNacionalidad;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	private String idEstudiante; // nchar(8)
    private String nombre; // nvarchar(30)
    private String apellido; // nvarchar(30)
    private String idCarrera; // nchar(5)
    private String idCategoriaPago; // nchar(5)
    private String idNacionalidad; // nchar(3)
    private String direccion; // nvarchar(256)
    
    

}
