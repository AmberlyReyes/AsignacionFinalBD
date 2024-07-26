package Logico;
import java.time.*;

public class Horario {
    public String getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(String idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public String getIdAsignatura() {
		return idAsignatura;
	}
	public void setIdAsignatura(String idAsignatura) {
		this.idAsignatura = idAsignatura;
	}
	public String getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
	public int getNumDia() {
		return numDia;
	}
	public void setNumDia(int numDia) {
		this.numDia = numDia;
	}
	public LocalDateTime getFechaHoraInicio() {
		return fechaHoraInicio;
	}
	public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}
	public LocalDateTime getFechaHoraFin() {
		return fechaHoraFin;
	}
	public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}
	private String idPeriodo; // nchar(10)
    private String idAsignatura; // nchar(8)
    private String idGrupo; // nchar(4)
    private int numDia; // int
    private LocalDateTime fechaHoraInicio; // datetime
    private LocalDateTime fechaHoraFin; // datetime

    // Getters and setters...
}