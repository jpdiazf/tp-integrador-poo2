

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CancelacionIntermedia extends Cancelacion{

	//Hasta 20 días antes es gratuita, entre el día 19 anterior y el
	//día 10 anterior paga el 50 %, después del 10mo día paga la totalidad.
	
	public CancelacionIntermedia() {
		super();
	}
	
	@Override
	double montoCancelacion(Reserva reserva, LocalDate fecha) {
		double monto;
		long diferencia = ChronoUnit.DAYS.between(fecha, reserva.comienzo());
		if(diferencia > 19) {
			monto = 0;
		} else {
			if (diferencia > 9) {
				monto = reserva.valor() * 0.5 ;
			} else {
				monto = reserva.valor();
			}
		}
		return monto;
	}

	
}
