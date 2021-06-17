
public class SensorReserva {

	private ISuscriptorReserva suscriptor;
	
	public SensorReserva(ISuscriptorReserva suscriptor) {
		this.suscriptor = suscriptor;
	}
	
	public void updateReservas(Reserva reserva) {
		if(reserva.getInmueble().getPropietario().equals(this.suscriptor)) {
			this.suscriptor.recibirReserva(reserva);
		}
	}

}
