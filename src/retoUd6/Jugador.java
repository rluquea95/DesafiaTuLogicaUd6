package retoUd6;

import java.time.LocalTime;
import java.time.temporal.*;

public class Jugador {

	private String nombre;
	private String arma;
	private String habitacion;
	private String horaPartida;
	
	public Jugador(String nombre, String arma, String habitacion) {
		this.nombre=nombre;
		this.arma=arma;
		this.habitacion=habitacion;
		horaPartida=LocalTime.now().truncatedTo(ChronoUnit.MINUTES).toString();
	}

	/* Se sobreescribe el método para poder mostrar la información del Jugador con la información
	 * que nos interesa, ya que el método de forma predeterminada devuelve el nombre del objeto
	 * tal y como lo almacena java.
	 */
	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", arma=" + arma + ", habitacion=" + habitacion + ", horaPartida="
				+ horaPartida + "]";
	}
}
