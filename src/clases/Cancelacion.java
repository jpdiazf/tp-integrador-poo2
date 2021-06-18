package clases;


import java.time.LocalDate;

public abstract class Cancelacion {
	
	final public double valor(Reserva reserva) {
		return montoCancelacion(reserva, LocalDate.now());
	}
	
	abstract double montoCancelacion(Reserva reseva, LocalDate hoy);
		
}