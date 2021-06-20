package ar.edu.unq.sitioInmueble;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;


import ar.edu.unq.siteGUI.IGraphicalUserInterface;
import clases.Inmueble;
import clases.PoliticaCancelacion;
import clases.Reserva;
import clases.Usuario;
import interfaces.IVisualizable;


public class SitioInmuebles {

	
	private HashSet<PoliticaCancelacion> politicasCancelacion = new HashSet<PoliticaCancelacion>();
	private ArrayList<Reserva> reservas = new ArrayList<Reserva>();
	private ArrayList<Inmueble> inmueblesDeAlta = new ArrayList<Inmueble>();
	private ArrayList<String> tiposInmueble = new ArrayList<String>();
	private ArrayList<String> serviciosInmueble = new ArrayList<String>();
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	private IGraphicalUserInterface gui;
	
	
	public SitioInmuebles(IGraphicalUserInterface gui) {
		this.gui = gui;
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
		return this.reservas;
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
	
	//BÚSQUEDAS - VISUALIZACIONES //
	
	public void realizarBusqueda(String ciudad, LocalDate fechaEntrada, LocalDate fechaSalida, Integer cantidadHuespedes, Double precioMinimo, Double precioMaximo){
		
		ArrayList<Inmueble> inmueblesFiltrados = new ArrayList<Inmueble>();
		//this.tiposInmueble.forEach(null);
		
		for(Inmueble inmueble:this.getInmueblesDeAlta()) {
			if(inmueble.getCiudad() == ciudad && !this.hayReservaEn(inmueble, fechaEntrada, fechaSalida)) {
				inmueblesFiltrados.add(inmueble);
			}
		}
		
		if(cantidadHuespedes != null) {
			inmueblesFiltrados.removeIf(inmueble -> inmueble.getCapacidad() < cantidadHuespedes);
		}
		
		if(precioMinimo != null) {
			inmueblesFiltrados.removeIf(inmueble -> inmueble.getPrecioPorPeriodo(fechaEntrada, fechaSalida) > precioMinimo);
		}
		
		if(precioMaximo != null) {
			inmueblesFiltrados.removeIf(inmueble -> inmueble.getPrecioPorPeriodo(fechaEntrada, fechaSalida) > precioMaximo);
		}
		
		//return inmueblesFiltrados;
		
		gui.mostrarEnPantalla(inmueblesFiltrados);
		
	}
	
	public boolean hayReservaEn(Inmueble inmueble, LocalDate fechaEntrada, LocalDate fechaSalida) {
		for(Reserva reserva:this.getReservas()) {
			if(reserva.getInmueble().equals(inmueble) && (reserva.getComienzo().isAfter(fechaSalida) || reserva.getFin().isBefore(fechaEntrada))) {
				return true;
			}
		}
		return false;
	}
	
	public void visualizar(IVisualizable visualizable) {
		
		//EXcepción si inmueble no está en pantalla.
		//Agregado para User
		gui.mostrarInformacion(visualizable);
		
	}
	
	
	//RESERVAS//
	/*
	public void recibirReserva(Usuario inquilino, Inmueble inmueble, IFormaDePago formaDePago, LocalDate fechaEntrada, LocalDate fechaSalida) {
		
		Reserva reservaRealizada = new Reserva(inquilino, inmueble, formaDePago, fechaEntrada, fechaSalida); //puede notificar al propietario al instanciarse??
		reservas.add(reservaRealizada);
		
		reservaRealizada.getInmueble().getPropietario().recibirReserva(reservaRealizada);; //raro
		
	}
	*/
	
	public void recibirReserva(Reserva reserva) {
		Reserva reservaRealizada = this.buscarReserva(reserva);
		reservas.add(reservaRealizada);
	}
	
	public void aprobarReserva(Reserva reserva) {
		Reserva reservaAAprobar= this.buscarReserva(reserva);
		reservaAAprobar.aprobar();
		//reservaAAprobar.getInquilino().notificarAprobacion(reservaAAprobar);
	}
	
	public void rechazarReserva(Reserva reserva) {
		Reserva reservaARechazar= this.buscarReserva(reserva);
		reservaARechazar.rechazar();
		//reservaARechazar.getInquilino().notificarRechazo(reservaAAprobar);
	}
	
	public void cancelarReserva(Reserva reserva) {
		Reserva reservaACancelar = this.buscarReserva(reserva);
		reservaACancelar.cancelar();
		this.getReservas().remove(reservaACancelar);
	}
	
	private Reserva buscarReserva(Reserva reserva) {
		return this.getReservas().stream().filter(r -> r.equals(reserva)).findFirst().get();
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
	
	
	
}
