
public class SensorBajaDePrecio {

	private ISuscriptorBajaDePrecio suscriptor;
		
	public SensorBajaDePrecio(ISuscriptorBajaDePrecio suscriptor) {
		this.suscriptor = suscriptor;
	}
	
	public void updateBajaDePrecio(Inmueble inmueble, double precio) {
		
		String mensaje = "No te pierdas esta oferta: Un inmueble " + inmueble.getTipo() + " a tan sólo "+ precio + " pesos";
		suscriptor.publish(mensaje);
		
	}

}

