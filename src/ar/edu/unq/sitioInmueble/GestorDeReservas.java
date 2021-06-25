package ar.edu.unq.sitioInmueble;

import java.time.LocalDate;
import java.time.LocalTime;
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
		this.validarAprobacion(reserva);
		reservaAAprobar.aprobar();
		//reservaAAprobar.getInquilino().notificarAprobacion(reservaAAprobar);
	}
	
	public void rechazarReserva(Reserva reserva) throws Exception{
		Reserva reservaARechazar= this.buscarReserva(reserva);
		this.validarAprobacion(reserva);
		this.getReservas().remove(reservaARechazar);
		//reservaARechazar.getInquilino().notificarRechazo(reservaAAprobar);
	}
	
	public void cancelarReserva(Reserva reserva) throws Exception{
		Reserva reservaACancelar = this.buscarReserva(reserva);
		reservaACancelar.cancelar();
		this.getReservas().remove(reservaACancelar);
		this.getSitioGestion().notificarCancelacionDeReserva(reservaACancelar);
	}
	
	private Reserva buscarReserva(Reserva reserva) throws Exception{
		this.validarReservaActiva(reserva);
		return this.getReservas().stream().filter(r -> r.equals(reserva)).findFirst().get();
	}
	
	
	public boolean hayReservaEn(Inmueble inmueble, LocalDate fechaEntrada, LocalDate fechaSalida) {
		for(Reserva reserva:this.getReservas()) {
			if(reserva.getInmueble().equals(inmueble)) {
				return ( (reserva.getComienzo().isAfter(fechaEntrada) && reserva.getFin().isBefore(fechaSalida)) ||
						(reserva.getComienzo().isBefore(fechaEntrada) && reserva.getFin().isAfter(fechaSalida)) ||
						(reserva.getComienzo().isBefore(fechaEntrada) && reserva.getFin().isAfter(fechaSalida)) ||
						(reserva.getComienzo().isAfter(fechaEntrada) && reserva.getFin().isAfter(fechaSalida) && reserva.getComienzo().isBefore(fechaSalida)) ||
						(reserva.getComienzo().isBefore(fechaEntrada) && reserva.getFin().isBefore(fechaSalida) && reserva.getFin().isAfter(fechaEntrada)));
			}
		}
		return false;
		
	}
	
	public boolean hayReservaHoy(Inmueble inmueble) {
		for(Reserva reserva:this.getReservas()) {
			if(reserva.getInmueble().equals(inmueble)) {
				
				LocalDate min = reserva.getComienzo();
				LocalDate max = reserva.getFin();   // assume these are set to something
				LocalDate hoy = LocalDate.now();          // the date in question

				return hoy.isAfter(min) && hoy.isBefore(max);
			}
		}
		return false;
	}
	
	
	//VALIDACIONES DEL GESTOR
	
	private void validarRealizacionReserva(Reserva reserva) throws Exception {
		Inmueble inmueble = reserva.getInmueble();
		if(!sitioGestion.estaDadoDeAlta(inmueble) || !inmueble.estaPublicadoPeriodo(reserva.getComienzo(), reserva.getFin())) {
			throw new Exception("No se encuentra disponible para reservar el inmueble " + inmueble.getInformacion());
		}
	}
	
	private void validarReservaActiva(Reserva reserva) throws Exception {
		if(!this.reservas.contains(reserva)) {
			throw new Exception("No se encuentra una reserva del inmueble " + reserva.getInmueble().getInformacion());
		}
	}
	
	private void validarAprobacion(Reserva reserva) throws Exception {
		if(reserva.estaAceptada()) {
			throw new Exception("La reserva ya se encuentra aceptada");
		}
	}
	
}
