package clases;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CancelacionIntermedia extends PoliticaCancelacion{

	//Hasta 20 días antes es gratuita, entre el día 19 anterior y el
	//día 10 anterior paga el 50 %, después del 10mo día paga la totalidad.
	
	@Override
	public double valor(Reserva reserva) {
		double monto;
		long diferencia = ChronoUnit.DAYS.between(LocalDate.now(), reserva.getComienzo());
		if(diferencia > 19) {
			monto = 0;
		} else {
			if (diferencia > 9) {
				monto = reserva.getPrecio() * 0.5 ;
			} else {
				monto = reserva.getPrecio();
			}
		}
		return monto;
	}

	
}
