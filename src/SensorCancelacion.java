
public class SensorCancelacion {
	
	private ISuscriptorCancelacion suscriptor;
	private String colorFuente;
	private int tamañoFuente;
	private Inmueble inmuebleDeInteres;
	
	public SensorCancelacion(ISuscriptorCancelacion suscriptor, String colorFuente, int tamañoFuente, Inmueble inmuebleDeInteres) {
		this.suscriptor = suscriptor;
		this.colorFuente = colorFuente;
		this.tamañoFuente = tamañoFuente;
		this.inmuebleDeInteres = inmuebleDeInteres;
	}
	
	public void updateCancelacion(Reserva reserva) {
		if(reserva.getInmueble() == this.inmuebleDeInteres) {
			
			String message = "El/la " + reserva.getInmueble().getTipo() + " que te interesa se ha liberado! Corre a reservarlo!";
			
			suscriptor.popUp(message, this.colorFuente, this.tamañoFuente);
		}
	}

}
