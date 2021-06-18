package interfaces;
import clases.Inmueble;

public interface ISuscriptorBajaDePrecio {
	
	public void updateBajaDePrecio(Inmueble inmueble);
	public void publish(String message);
	
}
