package clases;

public class SinCancelacion extends PoliticaCancelacion {

	//Sin cancelación: en caso de cancelar el usuario de todas formas paga
	//los días que había reservado.
	
	@Override
	public double valor(Reserva reserva)  {
		return reserva.getPrecio();
	}
	
	
}