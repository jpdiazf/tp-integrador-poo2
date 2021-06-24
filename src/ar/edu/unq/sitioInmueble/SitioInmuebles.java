package ar.edu.unq.sitioInmueble;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import clases.Inmueble;
import clases.PoliticaCancelacion;
import clases.Reserva;
import clases.Usuario;
import interfaces.IListenerBajaDePrecio;
import interfaces.IListenerCancelacion;
import interfaces.IListenerReserva;
import interfaces.IVisualizable;


public class SitioInmuebles {

	
	private HashSet<PoliticaCancelacion> politicasCancelacion;
	private ArrayList<Inmueble> inmueblesDeAlta;
	private ArrayList<String> tiposInmueble;
	private ArrayList<String> serviciosInmueble;
	private ArrayList<Usuario> usuarios;
	
	private HashMap<Entidad, ArrayList<String>> categoriasRankeables;
	private GestorDeNotificaciones gestorNotificaciones;
	private GestorDeReservas gestorReservas;

	
	
	public SitioInmuebles() {
		super();
		this.politicasCancelacion = new HashSet<PoliticaCancelacion>();
		this.inmueblesDeAlta = new ArrayList<Inmueble>();
		this.tiposInmueble = new ArrayList<String>();
		this.serviciosInmueble = new ArrayList<String>();
		this.usuarios = new ArrayList<Usuario>();
		this.categoriasRankeables = new HashMap<Entidad, ArrayList<String>>();
		
		categoriasRankeables.put(Entidad.INMUEBLE, new ArrayList<String>());
		categoriasRankeables.put(Entidad.INQUILINO, new ArrayList<String>());
		categoriasRankeables.put(Entidad.PROPIETARIO, new ArrayList<String>());
		
		this.gestorNotificaciones = new GestorDeNotificaciones(this);
		this.gestorReservas = new GestorDeReservas(this);
	}

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
	
	public ArrayList<String> getCategoriasRankeables(Entidad entidad) {
		return categoriasRankeables.get(entidad);
	}
	
	public GestorDeReservas getGestorReservas() {
		return this.gestorReservas;
	}
	
	public GestorDeNotificaciones getGestorNotificaciones() {
		return this.gestorNotificaciones;
	}
	
	
	//ALTAS//
	
	
	public void setGestorReservas(GestorDeReservas nuevoGestor) {
		this.gestorReservas = nuevoGestor;
	}
	
	public void setGestorNotificaciones(GestorDeNotificaciones nuevoGestor) {
		this.gestorNotificaciones = nuevoGestor;
	}
	
	
	public void darDeAltaUsuario(Usuario usuario) {
		usuarios.add(usuario);
	//	gestorNotificaciones.suscribirNuevaReserva(usuario);
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
	
	public void darDeAltaCategoriaPara(Entidad entidad, String categoria) {
		this.categoriasRankeables.get(entidad).add(categoria);
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
		this.getGestorNotificaciones().notificarCancelacionDeReserva(reserva);
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
		return this.getGestorReservas().hayReservaEn(inmueble, fechaEntrada, fechaSalida);
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
			if(!this.estaOcupado(inmueble)) {
				inmuebles.add(inmueble);
			}
		}
		
		return inmuebles;
	}
	

	public boolean estaOcupado(Inmueble inmueble) {
		//LocalDate hoy = LocalDate.now();
		
		if(this.estaReservado(inmueble)) {
			return this.getGestorReservas().hayReservaHoy(inmueble);
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
	
	public Double getCantidadInmueblesAlquilados() {
		Double inmueblesAlquilados = 0d;
		LocalDate hoy = LocalDate.now();
		
		for(Reserva reserva:this.getReservas()) {
			if(hoy.isAfter(reserva.getComienzo())) {
				inmueblesAlquilados++;
			}
		}
		
		return inmueblesAlquilados;
	}
	
	/*
	public Double promedioPuntaje(Entidad entidad) {
		
		Double cantidad = 0d;
		Double puntajeTotal = 0d;
		
		for(Usuario usuario:usuarios) {
			if(usuario.getEntidad() == entidad) {
				cantidad++;
				puntajeTotal += usuario.getGestorRankeos().getPromedioTotal();
			}
		}
		if(cantidad == 0d) {
            return 0d;
        } else {
        	return Math.floor((puntajeTotal / cantidad) * 100) / 100;
        }
		
	}
	*/
	
	
	//SUSCRIPCIONES
	
	public void suscribirBajaDePrecio(Inmueble inmueble, IListenerBajaDePrecio listener) {
		gestorNotificaciones.suscribirBajaDePrecio(inmueble, listener); 
	}
	
	public void desuscribirBajaDePrecio(Inmueble inmueble, IListenerBajaDePrecio listener) {
		gestorNotificaciones.desuscribirBajaDePrecio(inmueble, listener);
	}
	
	public void suscribirCancelacionDeReserva(Reserva reserva, IListenerCancelacion listener) {
		gestorNotificaciones.suscribirCancelacion(reserva.getInmueble(), listener);
	}
	
	public void desuscribirCancelacionDeReserva(Reserva reserva, IListenerCancelacion listener) {
		gestorNotificaciones.desuscribirCancelacion(reserva.getInmueble(), listener);
	}
	
	public void suscribirNuevaReserva(IListenerReserva listener) {
		gestorNotificaciones.suscribirReserva(listener);
	}
	
	public void desuscribirNuevaReserva(IListenerReserva listener) {
		gestorNotificaciones.desuscribirReserva(listener);
	}
	
	
	//NOTIFICACIONES
	
	public void notificarBajaDePrecio(Inmueble inmueble, Double nuevoPrecio) {
		gestorNotificaciones.notificarBajaDePrecio(inmueble, nuevoPrecio);
	}
	
	public void notificarCancelacionDeReserva(Reserva reserva) {
		gestorNotificaciones.notificarCancelacionDeReserva(reserva);
	}
	
	public void notificarNuevaReserva(Reserva reserva) {
		gestorNotificaciones.notificarNuevaReserva(reserva);
	}
	
	
	//AUXILIARES

	public boolean estaDadoDeAlta(Inmueble inmueble) {
		return this.getInmueblesDeAlta().contains(inmueble);
	}
	
	
	
	
	
	
	
}
