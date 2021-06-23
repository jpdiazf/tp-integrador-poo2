package ar.edu.unq.sitioInmueble;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


import ar.edu.unq.siteGUI.IGraphicalUserInterface;
import clases.Inmueble;
import clases.PoliticaCancelacion;
import clases.Reserva;
import clases.Usuario;
import interfaces.ISuscriptorBajaDePrecio;
import interfaces.ISuscriptorCancelacion;
import interfaces.ISuscriptorReserva;
import interfaces.IVisualizable;


public class SitioInmuebles {

	
	private HashSet<PoliticaCancelacion> politicasCancelacion = new HashSet<PoliticaCancelacion>();
	private ArrayList<Inmueble> inmueblesDeAlta = new ArrayList<Inmueble>();
	private ArrayList<String> tiposInmueble = new ArrayList<String>();
	private ArrayList<String> serviciosInmueble = new ArrayList<String>();
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	private HashMap<Entidad, ArrayList<String>> categoriasRankeables = new HashMap<Entidad, ArrayList<String>>();
	private GestorDeNotificaciones gestorNotificaciones = new GestorDeNotificaciones(this);
	private GestorDeReservas gestorReservas = new GestorDeReservas(this);

	
	
	public HashSet<PoliticaCancelacion> getPoliticasCancelacion() {
		return politicasCancelacion;
	}

	public ArrayList<String> getServiciosInmueble() {
		return serviciosInmueble;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public ArrayList<Reserva> getReservas() {
		return gestorReservas.getReservas();
	}
	
	public ArrayList<Inmueble> getInmueblesDeAlta() {
		return this.inmueblesDeAlta;
	}
	
	public ArrayList<String> getTiposInmueble() {
		return this.tiposInmueble;
	}
	
	
	
	//ALTAS//
	
	
	public void darDeAltaUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}
	
	public void darDeAltaTipoInmueble(String tipoInmueble) {
		tiposInmueble.add(tipoInmueble);
	}
	
	public void darDeAltaServicio(String servicioInmueble) {
		serviciosInmueble.add(servicioInmueble);
	}
	
	public void darDeAltaPoliticaCancelacion(PoliticaCancelacion politicaC) {
		this.politicasCancelacion.add(politicaC);
	}
	
	public void darDeAltaInmueble(Inmueble inmueble) {
		this.inmueblesDeAlta.add(inmueble);
	}
	
	
	
	//RESERVAS
	
	public void recibirReserva(Reserva reserva) throws Exception {
		gestorReservas.recibirReserva(reserva);
	}
	
	public void aprobarReserva(Reserva reserva) throws Exception{
		gestorReservas.aprobarReserva(reserva);
	}
	
	public void rechazarReserva(Reserva reserva) throws Exception{
		gestorReservas.rechazarReserva(reserva);
	}
	
	public void cancelarReserva(Reserva reserva) throws Exception{
		gestorReservas.cancelarReserva(reserva);
	}
	
	
	//BÚSQUEDAS - VISUALIZACIONES //
	
	
	public ArrayList<Inmueble> realizarBusqueda(String ciudad, LocalDate fechaEntrada, LocalDate fechaSalida, Integer cantidadHuespedes, Double precioMinimo, Double precioMaximo){
		
		ArrayList<Inmueble> inmueblesFiltrados = new ArrayList<Inmueble>();
		
		for(Inmueble inmueble:this.getInmueblesDeAlta()) {
			if(inmueble.getCiudad() == ciudad && !this.hayReservaEn(inmueble, fechaEntrada, fechaSalida)) {
				inmueblesFiltrados.add(inmueble);
			}
		}
		
		if(cantidadHuespedes != null) {
			inmueblesFiltrados.removeIf(inmueble -> inmueble.getCapacidad() < cantidadHuespedes);
		}
		
		if(precioMinimo != null) {
			inmueblesFiltrados.removeIf(inmueble -> inmueble.getPrecioPorPeriodo(fechaEntrada, fechaSalida) < precioMinimo);
		}
		
		if(precioMaximo != null) {
			inmueblesFiltrados.removeIf(inmueble -> inmueble.getPrecioPorPeriodo(fechaEntrada, fechaSalida) > precioMaximo);
		}
		
		return inmueblesFiltrados;
		
	}
	
	public boolean hayReservaEn(Inmueble inmueble, LocalDate fechaEntrada, LocalDate fechaSalida) {
		for(Reserva reserva:this.getReservas()) {
			if(reserva.getInmueble().equals(inmueble) && (reserva.getComienzo().isAfter(fechaSalida) || reserva.getFin().isBefore(fechaEntrada))) {
				return true;
			}
		}
		return false;
	}
	
	
	
	//LISTADOS DE GESTIÓN//
	
	
	public ArrayList<Usuario> topTenInquilinosActivos() {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>(this.usuarios); 
		ArrayList<Usuario> inquilinosActivos = new ArrayList<Usuario>();
		
		for(int i = 1; (i <= 10 && !usuarios.isEmpty()); i++) {
			Usuario inquilinoMasActivo = usuarios.stream().max((Usuario user1, Usuario user2) -> (Integer.compare(user1.cuantosInmueblesAlquilo(), user2.cuantosInmueblesAlquilo()))).get();
			inquilinosActivos.add(inquilinoMasActivo);
			usuarios.remove(inquilinoMasActivo);
		}
		return inquilinosActivos;
		
	}
	
	public ArrayList<Inmueble> mostrarInmueblesLibres() {
		ArrayList<Inmueble> inmuebles = new ArrayList<Inmueble>();
		
		for(Inmueble inmueble:this.getInmueblesDeAlta()) {
			if(!inmueble.estaOcupado()) {
				inmuebles.add(inmueble);
			}
		}
		
		return inmuebles;
	}
	
	public boolean estaOcupado(Inmueble inmueble) {
		LocalDate hoy = LocalDate.now();
		
		if(this.estaReservado(inmueble)) {
			Reserva reserva = this.getReservas().stream().filter(r -> r.getInmueble().equals(inmueble)).findFirst().get();
			return(hoy.isAfter(reserva.getComienzo()) && hoy.isBefore(reserva.getFin()));
		}
		else {
			return false;
		}
			
	}
	
	public boolean estaReservado(Inmueble inmueble) {
		for(Reserva reserva:this.getReservas()) {
			if(reserva.getInmueble().equals(inmueble)) {
				return true;
			}
		}
		return false;
	}
	
	public Double tasaDeOcupacion() {
		
		return this.getCantidadInmueblesAlquilados() / (double)this.getInmueblesDeAlta().size();
		
	}
	
	public int getCantidadInmueblesAlquilados() {
		int inmueblesAlquilados = 0;
		LocalDate hoy = LocalDate.now();
		
		for(Reserva reserva:this.getReservas()) {
			if(hoy.isAfter(reserva.getComienzo()) && hoy.isBefore(reserva.getFin())) {
				inmueblesAlquilados++;
			}
		}
		
		return inmueblesAlquilados;
	}
	
	
	//SUSCRIPCIONES
	
	public void suscribirBajaDePrecio(Inmueble inmueble, ISuscriptorBajaDePrecio suscriptor) {
		gestorNotificaciones.suscribirBajaDePrecio(inmueble, suscriptor); //PREGUNTAR
	}
	
	public void desuscribirBajaDePrecio(ISuscriptorBajaDePrecio suscriptor) {
		gestorNotificaciones.desuscribirBajaDePrecio(suscriptor); //PREGUNTAR
	}
	
	public void suscribirCancelacionDeReserva(Reserva reserva, ISuscriptorCancelacion suscriptor) {
		gestorNotificaciones.suscribirCancelacionDeReserva(reserva, suscriptor);
	}
	
	public void desuscribirCancelacionDeReserva(Reserva reserva, ISuscriptorCancelacion suscriptor) {
		gestorNotificaciones.desuscribirCancelacionDeReserva(reserva, suscriptor);
	}
	
	public void suscribirNuevaReserva(Inmueble inmueble, ISuscriptorReserva suscriptor) {
		gestorNotificaciones.suscribirNuevaReserva(inmueble, suscriptor);
	}
	
	public void desuscribirNuevaReserva(Inmueble inmueble, ISuscriptorReserva suscriptor) {
		gestorNotificaciones.desuscribirNuevaReserva(inmueble, suscriptor);
	}
	
	
	
}
