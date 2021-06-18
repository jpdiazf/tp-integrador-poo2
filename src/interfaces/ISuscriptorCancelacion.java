package interfaces;
import clases.Reserva;

public interface ISuscriptorCancelacion {
	
	public void updateCancelacion(Reserva reserva);
	public void popUp(String message, String color, int font);
	
}
