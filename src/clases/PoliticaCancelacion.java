package clases;


import java.time.LocalDate;

public abstract class PoliticaCancelacion {
	
	final public double valor(Reserva reserva) {
		return montoCancelacion(reserva, LocalDate.now());
	}
	
	abstract double montoCancelacion(Reserva reseva, LocalDate hoy);
		
}