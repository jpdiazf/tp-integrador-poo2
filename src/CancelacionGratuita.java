

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CancelacionGratuita extends Cancelacion{


	//Cancelación gratuita hasta 10 días antes de la fecha de inicio de la
	//ocupación y luego abona el equivalente a dos días de reserva
	
	public CancelacionGratuita() {
		super();
	}
	
	@Override
	double montoCancelacion(Reserva reserva, LocalDate fecha) {
		double monto = 0;
		long diasReserva = ChronoUnit.DAYS.between(reserva.comienzo(), reserva.fin());
		long diferencia = ChronoUnit.DAYS.between(fecha, reserva.comienzo());
		if(diferencia < 10) {
			monto = (reserva.valor() / diasReserva) * 2;
		}
		return monto;
	}

}
