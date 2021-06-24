package clases;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ar.edu.unq.sitioInmueble.GestorDeRankeos;
import ar.edu.unq.sitioInmueble.SitioInmuebles;
import excepciones.EmailAdressNotFound;
import excepciones.ReservationNotFound;
import interfaces.IRankeable;
import interfaces.IVisualizable;

public class Usuario implements IRankeable, IVisualizable {
	private SitioInmuebles sitioInmuebles;
	private String nombre;
	private String direccionMail;
	private String nroTelefono;
    private List<Reserva> reservasRecibidas = new ArrayList<>();
    private List<Reserva> reservasRealizadas = new ArrayList<>();
    private List<Mail> mailsRecibidos = new ArrayList<>();
    private List<Inmueble> inmueblesPreferidos = new ArrayList<>(); 
    private MailServer mailServer;
    private int inmueblesAlquiladosInquilino;
    private Integer inmueblesAlquiladosPropietario;
    private Pantalla pantalla = new Pantalla();
    private LocalDate usuarioDesde;
    private GestorDeRankeos gestorRankeos; 

	public Usuario(SitioInmuebles sitio, String nombre, String direccionMail, String nroTelefono, MailServer mailServer, GestorDeRankeos gestor, Pantalla pantalla) {
		this.sitioInmuebles = sitio;
		this.nombre = nombre;
		this.direccionMail = direccionMail;
		this.nroTelefono = nroTelefono;
        this.mailServer = mailServer;
        this.inmueblesAlquiladosInquilino = 0; //sumar� al alquilar...
        this.inmueblesAlquiladosPropietario = 0;
        this.gestorRankeos = gestor;
        this.pantalla = pantalla;
        this.usuarioDesde = LocalDate.now();
	}

	public String getMail() {
		return direccionMail;
	}
	
    public List<Reserva> getReservasRecibidas() {
        return this.reservasRecibidas;
    }
    
    public void realizarBusqueda(String ciudad, LocalDate fechaEntrada, LocalDate fechaSalida, int cantidadHuespedes, double precioMinimo, double precioMaximo) {
    	inmueblesPreferidos = this.sitioInmuebles.realizarBusqueda(ciudad, fechaEntrada, fechaSalida, cantidadHuespedes, precioMinimo, precioMaximo);
    }

	public void ponerEnAlquiler(Inmueble inmueble) {
		this.sitioInmuebles.darDeAltaInmueble(inmueble);
	}
    
	public void realizarReserva(Reserva reserva) throws Exception {
        sitioInmuebles.recibirReserva(reserva);
        this.reservasRealizadas.add(reserva);
    }

	public GestorDeRankeos getGestorRankeos() {
		return this.gestorRankeos;
	}
	
	public void rankear(Rankeo rankeo) {
        rankeo.getRankeable().recibirRankeo(rankeo);
    }

	public void recibirRankeo(Rankeo rankeo) {
        gestorRankeos.addRankeo(rankeo);
    }

	public void recibirReserva(Reserva reserva) {
		this.reservasRecibidas.add(reserva);
	}
	
	public void cancelarReserva(Reserva reserva) throws Exception {
		this.validarCancelacion(reserva);
		
		this.sitioInmuebles.cancelarReserva(reserva);
		this.reservasRealizadas.remove(reserva);
	}
	
	public String getNombre() {
		return nombre;
	}
	
    public void visualizar(IVisualizable visualizable) {
        visualizable.visualizarse();
    }

	public void aceptarReserva(Reserva reserva) throws Exception {        
		String remitente = this.direccionMail;
		String destino = reserva.getInquilino().getMail();
        Mail mail = new Mail(remitente, destino, "Aceptaci�n de Reserva", "Se acept� su reserva. Se adjunta a continuaci�n", reserva);
        
        this.sitioInmuebles.aprobarReserva(reserva);
        this.enviarMail(mail);
	}

	public void rechazarReserva(Reserva reserva) throws Exception {
		this.validarRechazo(reserva);
		
		sitioInmuebles.rechazarReserva(reserva);
		reservasRecibidas.remove(reserva);
	}
	
	public void enviarMail(Mail mail) throws EmailAdressNotFound {
		this.mailServer.enviarMail(mail);
	}

	public int cuantosInmueblesAlquilo() {
		return this.inmueblesAlquiladosInquilino;
	}
	
	public SitioInmuebles getSitioInmuebles() {
		return this.sitioInmuebles;
	}
	
	public void recibirMail(Mail mail) {
		this.mailsRecibidos.add(mail);
	}
	
	public List<Mail> getMailsRecibidos() {
		return this.mailsRecibidos;
	}	
	
	public String getNroTelefono() {
		return this.nroTelefono;
	}
	
	public MailServer getMailServer() {
		return this.mailServer;
	}
	
	public List<Reserva> getReservasRealizadas() {
		return this.reservasRealizadas;
	}
	
	public List<Inmueble> getInmueblesPreferidos() {
		return this.inmueblesPreferidos;
	}

	public List<Reserva> getTodasLasReservas() {
		return this.getReservasRealizadas();
	}
	
	public List<Reserva> getReservasConCiudad(String ciudad) {
		return reservasRealizadas.stream()
								 .filter(r -> r.getInmueble().getCiudad().equals(ciudad))
								 .collect(Collectors.toList());
	}
	
	public List<String> getCiudadesConReservas() {
		List<String> ciudades = reservasRealizadas
								 .stream()
								 .map(r -> r.getInmueble().getCiudad())
								 .collect(Collectors.toList());
		
		return this.sinRepetidos(ciudades);
	}
	
	public List<Reserva> getReservasFuturas() {
		List<Reserva> reservasFuturas = reservasRealizadas
				.stream()
				.filter(r -> r.getComienzo().compareTo(LocalDate.now()) > 0)
				.collect(Collectors.toList());
		
		return reservasFuturas;
	}
	
	@Override
	public void visualizarse() {
		String textoVisualizacion =
			"Comentarios: " +
				this.textoPuntajes() +
			"Puntaje promedio: " +
				this.puntajePromedio() +
			"Usuario desde: " +
				this.getUsuarioDesde() +
			"Cantidad de inmuebles alquilados: " +
				this.getInmueblesAlquilados();
		
		this.pantalla.visualizar(textoVisualizacion);
	}
	
	// Privates
	
	private String getUsuarioDesde() {
		return usuarioDesde.toString();
	}

	private String getInmueblesAlquilados() {
		return inmueblesAlquiladosPropietario.toString();
	}

	private double puntajePromedio() {
		return gestorRankeos.promedioTotalRanking();
	}

	private String textoPuntajes() {		
		return gestorRankeos.textoComentarios();
	}

	private List<String> sinRepetidos(List<String> listCiudades) {
		Set<String> setCiudades = new HashSet<>(listCiudades);
		listCiudades.clear();
		listCiudades.addAll(setCiudades);
		
		return listCiudades;
	}
	
	private void validarCancelacion(Reserva reserva) throws Exception {
		if(!this.reservasRealizadas.contains(reserva)) {
			throw new ReservationNotFound("No se puede cancelar una Reserva no realizada");
		}
	}
	
	private void validarRechazo(Reserva reserva) throws Exception {
		if(!this.reservasRecibidas.contains(reserva)) {
			throw new ReservationNotFound("No se puede rechazar una Reserva no realizada");
		}
	}
}