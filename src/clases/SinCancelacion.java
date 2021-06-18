package clases;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SinCancelacion extends Cancelacion {

	//Sin cancelación: en caso de cancelar el usuario de todas formas paga
	//los días que había reservado.

	public SinCancelacion() {
		super();
	}
	
	@Override
	double montoCancelacion(Reserva reserva, LocalDate fecha) {
		return reserva.valor();
	}
	
	
}