package clases;
import interfaces.ISuscriptorCancelacion;

public class SensorCancelacion {
	
	private ISuscriptorCancelacion suscriptor;
	private String colorFuente;
	private int tamanioFuente;
	private Inmueble inmuebleDeInteres;
	
	public SensorCancelacion(ISuscriptorCancelacion suscriptor, String colorFuente, int tamanioFuente, Inmueble inmuebleDeInteres) {
		this.suscriptor = suscriptor;
		this.colorFuente = colorFuente;
		this.tamanioFuente = tamanioFuente;
		this.inmuebleDeInteres = inmuebleDeInteres;
	}
	
	public void updateCancelacion(Reserva reserva) {
		if(reserva.getInmueble().equals(this.inmuebleDeInteres)) {
			
			String message = "El/la " + reserva.getInmueble().getTipo() + " que te interesa se ha liberado! Corre a reservarlo!";
			
			suscriptor.popUp(message, this.colorFuente, this.tamanioFuente);
		}
	}

}
