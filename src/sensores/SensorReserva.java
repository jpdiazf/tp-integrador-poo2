package sensores;
import java.util.ArrayList;
import java.util.List;

import clases.Inmueble;
import clases.Reserva;
import interfaces.IListenerReserva;

public class SensorReserva {
	
	private List<IListenerReserva> listeners;
	
	public SensorReserva() {
		this.listeners = new ArrayList<IListenerReserva>();
	}
	

	public void addSensorListener(IListenerReserva listener) {
		if(this.listeners.indexOf(listener) == -1) {
			this.listeners.add(listener);
		}
	}

	public void removeSensorListener(Inmueble inmueble, IListenerReserva listener) {
		if(this.listeners.indexOf(listener) != -1) {
			this.listeners.remove(this.listeners.indexOf(listener));
		}
	}
	
	public void notificarReserva(Reserva reserva) {
		for (IListenerReserva listener : this.listeners) {
			if (reserva.getInmueble().getPropietario() == listener) {
				listener.recibirReserva(reserva);
			}
		}
	}
	
}