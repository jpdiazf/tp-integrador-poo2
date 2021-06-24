package ar.edu.unq.sitioInmueble;

import java.util.ArrayList;

import clases.Inmueble;
import clases.Reserva;
import clases.Usuario;

public class GestorDeReservas {

	
	private SitioInmuebles sitioGestion;
	private ArrayList<Reserva> reservas = new ArrayList<Reserva>();
	
	
	public GestorDeReservas(SitioInmuebles sitio) {
		this.sitioGestion = sitio;
	}
	
	public ArrayList<Reserva> getReservas() {
		return reservas;
	}
	
	public SitioInmuebles getSitioGestion() {
		return this.sitioGestion;
	}
	
	public void recibirReserva(Reserva reserva) throws Exception {
		this.validarRealizacionReserva(reserva);
		this.getSitioGestion().notificarNuevaReserva(reserva);
		reservas.add(reserva);
	}
	
	public void aprobarReserva(Reserva reserva) throws Exception{
		Reserva reservaAAprobar= this.buscarReserva(reserva);
		//PREGUNTAR SOBRE MÁS VALIDACIONES
		reservaAAprobar.aprobar();
		//reservaAAprobar.getInquilino().notificarAprobacion(reservaAAprobar);
	}
	
	public void rechazarReserva(Reserva reserva) throws Exception{
		Reserva reservaARechazar= this.buscarReserva(reserva);
		reservaARechazar.rechazar();
		//reservaARechazar.getInquilino().notificarRechazo(reservaAAprobar);
	}
	
	public void cancelarReserva(Reserva reserva) throws Exception{
		Reserva reservaACancelar = this.buscarReserva(reserva);
		reservaACancelar.cancelar();
		this.getReservas().remove(reservaACancelar);
	}
	
	private Reserva buscarReserva(Reserva reserva) throws Exception{
		this.validarReservaActiva(reserva);
		return this.getReservas().stream().filter(r -> r.equals(reserva)).findFirst().get();
	}
	
	
	//VALIDACIONES DEL GESTOR
	
	private void validarRealizacionReserva(Reserva reserva) throws Exception {
		Inmueble inmueble = reserva.getInmueble();
		if(!sitioGestion.getInmueblesDeAlta().contains(inmueble) || !inmueble.estaPublicadoPeriodo(reserva.getComienzo(), reserva.getFin())) {
			throw new Exception("No se encuentra disponible para reservar el inmueble " + inmueble.getInformacion());
		}
	}
	
	private void validarReservaActiva(Reserva reserva) throws Exception {
		if(!this.reservas.contains(reserva)) {
			throw new Exception("No se encuentra una reserva del inmueble " + reserva.getInmueble().getInformacion());
		}
	}
	
	private void validarReservaANombreDe(Reserva reserva, Usuario usuario) throws Exception {
		if(!reserva.getInquilino().equals(usuario)) {
			throw new Exception("La reserva no se encuentra a nombre de " + usuario.getNombre());
		}
	}
	
	private void validarReservaHacia(Reserva reserva, Usuario usuario) throws Exception {
		if(!reserva.getPropietario().equals(usuario)) {
			throw new Exception("La reserva no es hacia" + usuario.getNombre());
		}
	}
	
}
