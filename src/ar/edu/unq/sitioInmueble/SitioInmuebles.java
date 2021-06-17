package ar.edu.unq.sitioInmueble;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;


import ar.edu.unq.siteGUI.IGraphicalUserInterface;


public class SitioInmuebles {

	
	private HashSet<IPoliticaCancelacion> politicasCancelacion;
	private ArrayList<IReserva> reservas;
	private ArrayList<IInmueble> inmueblesDeAlta;
	private ArrayList<String> tiposInmueble;
	private ArrayList<String> serviciosInmueble;
	private ArrayList<IUsuario> usuarios;
	private IGraphicalUserInterface gui;
	
	
	public SitioInmuebles() {
		
	}
	
	
	private ArrayList<IReserva> getReservas() {
		return this.reservas;
	}
	
	private ArrayList<IInmueble> getInmueblesDeAlta() {
		return this.inmueblesDeAlta;
	}
	
	//ALTAS//
	
	public void darDeAltaUsuario(IUsuario usuario) {
		usuarios.add(usuario);
	}
	
	public void darDeAltaTipoInmueble(String tipoInmueble) {
		tiposInmueble.add(tipoInmueble);
	}
	
	public void darDeAltaServicio(String servicioInmueble) {
		serviciosInmueble.add(servicioInmueble);
	}
	
	public void darDeAltaPoliticaCancelacion(IPoliticaCancelacion politicaC) {
		this.politicasCancelacion.add(politicaC);
	}
	
	//BÚSQUEDAS - VISUALIZACIONES //
	
	public void realizarBusqueda(String ciudad, LocalDate fechaEntrada, LocalDate fechaSalida,
						Integer cantidadHuespedes, Double precioMinimo, Double precioMaximo) {
		
		ArrayList<IInmueble> inmueblesFiltrados = new ArrayList<IInmueble>();
		this.tiposInmueble.forEach(null);
		
		for(IInmueble inmueble:this.getInmueblesDeAlta()) {
			if(inmueble.getCiudad() == ciudad && fechaEntrada.isAfter(inmueble.getHorarioCheckIn()) && fechaSalida.isBefore(inmueble.getHorarioCheckOut())) {
				inmueblesFiltrados.add(inmueble);
			}
		}
		
		if(cantidadHuespedes != null) {
			inmueblesFiltrados.removeIf(inmueble -> inmueble.getCantidadHuespedes() < cantidadHuespedes);
		}
		
		if(precioMinimo != null) {
			inmueblesFiltrados.removeIf(inmueble -> inmueble.getPrecioPorDia() > precioMinimo);
		}
		
		if(precioMaximo != null) {
			inmueblesFiltrados.removeIf(inmueble -> inmueble.getPrecioPorDia() > precioMaximo);
		}
		
		//return inmueblesFiltrados;
		
		gui.mostrarEnPantalla(inmueblesFiltrados);
		
	}
	
	public void visualizarInmueble(IInmueble inmueble) {
		
		//EXcepción si inmueble no está en pantalla.
		
		gui.mostrarInformacion(inmueble);
		
	}
	
	public void visualizarPropietario(IInmueble inmueble) {
		
		gui.mostrarInformacion(inmueble.getPropietario());
		
	}
	
	
	//RESERVAS//
	/*
	public void realizarReserva(IInmueble inmueble, IFormaDePago formaDePago, IUsuario inquilino, LocalDate fechaEntrada, LocalDate fechaSalida) {
		
		IReserva reservaRealizada = new IReserva(inquilino, inmueble, formaDePago, fechaEntrada, fechaSalida);
		reservas.add(reservaRealizada);
		
		this.notificarReserva(reservaRealizada);
		
	}
	
	private void notificarReserva(IReserva reserva) {
		reserva.getInmueble().getPropietario().notificarReserva(reserva);
	}
	
	
	public void aprobarReserva(IReserva reserva) {
		IReserva reservaAAprobar= this.getReservas().stream().filter(r -> r.equals(reserva)).findFirst().get();
		reservaAAprobar.aprobar();
		reservaAAprobar.getInquilino().notificarAprobacion(reservaAAprobar);
	}
	
	public void rechazarReserva(IReserva reserva) {
		IReserva reservaARechazar= this.getReservas().stream().filter(r -> r.equals(reserva)).findFirst().get();
		reservaARechazar.rechazar();
		reservaARechazar.getInquilino().notificarRechazo(reservaAAprobar);
	}
	*/
	
	//LISTADOS DE GESTIÓN//
	
	public ArrayList<IUsuario> topTenInquilinosActivos() {
		ArrayList<IUsuario> usuarios = new ArrayList<IUsuario>(this.usuarios); 
		ArrayList<IUsuario> inquilinosActivos = new ArrayList<IUsuario>();
		
		for(int i = 1; (i <= 10 && !usuarios.isEmpty()); i++) {
			IUsuario inquilinoMasActivo = usuarios.stream().max((IUsuario user1, IUsuario user2) -> (Integer.compare(user1.cuantosInmueblesAlquilo(), user2.cuantosInmueblesAlquilo()))).get();
			inquilinosActivos.add(inquilinoMasActivo);
			usuarios.remove(inquilinoMasActivo);
		}
		return inquilinosActivos;
		
	}
	
	public ArrayList<IInmueble> mostrarInmueblesLibres() {
		ArrayList<IInmueble> inmuebles = new ArrayList<IInmueble>();
		
		for(IInmueble inmueble:this.getInmueblesDeAlta()) {
			if(!inmueble.estaOcupado()) {
				inmuebles.add(inmueble);
			}
		}
		
		return inmuebles;
	}
	
	public boolean estaOcupado(IInmueble inmueble) {
		LocalDate hoy = LocalDate.now();
		
		if(this.estaReservado(inmueble)) {
			IReserva reserva = this.getReservas().stream().filter(r -> r.getInmueble().equals(inmueble)).findFirst().get();
			return(hoy.isAfter(reserva.getComienzo()) && hoy.isBefore(reserva.getFin()));
		}
		else {
			return false;
		}
			
	}
	
	public boolean estaReservado(IInmueble inmueble) {
		for(IReserva reserva:this.getReservas()) {
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
		
		for(IReserva reserva:this.getReservas()) {
			if(hoy.isAfter(reserva.getComienzo()) && hoy.isBefore(reserva.getFin())) {
				inmueblesAlquilados++;
			}
		}
		
		return inmueblesAlquilados;
	}
	
	
	
}
