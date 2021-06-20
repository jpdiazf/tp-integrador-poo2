package clases;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.sitioInmueble.SitioInmuebles;
import excepciones.EmailAdressNotFound;
import excepciones.ReservationNotFound;
import interfaces.IRankeable;
import interfaces.ISuscriptorReserva;
import interfaces.IVisualizable;

// TODO: que el sitio agregue el mail del usuario cuando se instancie.
public class Usuario implements ISuscriptorReserva, IRankeable, IVisualizable {
	private SitioInmuebles sitioInmuebles;
	private String nombre;
	private String mail;
	private String nroTelefono;
    private List<Rankeo> rankeos = new ArrayList<>();
    private List<Reserva> reservasRecibidas = new ArrayList<>();
    private List<Reserva> reservasRealizadas = new ArrayList<>();
    private MailServer mailServer;
    private int inmueblesAlquiladosInquilino;

	public Usuario(SitioInmuebles sitio, String nombre, String mail, String nroTelefono, MailServer mailServer) {
		this.sitioInmuebles = sitio;
		this.nombre = nombre;
		this.mail = mail;
		this.nroTelefono = nroTelefono;
        this.mailServer = mailServer;
        this.inmueblesAlquiladosInquilino = 0; //sumará al alquilar...
	}

	public String getMail() {
		return mail;
	}
	
    public List<Reserva> getReservasRecibidas() {
        return this.reservasRecibidas;
    }

	public void ponerEnAlquiler(Inmueble inmueble) {
		this.sitioInmuebles.darDeAltaInmueble(inmueble);
	}
    
	public void realizarReserva(Reserva reserva) {
        sitioInmuebles.recibirReserva(reserva);
        this.reservasRealizadas.add(reserva);
    }

	public void rankear(Rankeo rankeo) {
        rankeo.getRankeable().recibirRankeo(rankeo);
    }

	public void recibirRankeo(Rankeo rankeo) {
        this.rankeos.add(rankeo);
    }

	public void recibirReserva(Reserva reserva) {
		this.reservasRecibidas.add(reserva);
	}
	
	public void cancelarReserva(Reserva reserva) throws Exception {
		this.validarCancelacion(reserva);
		
		this.sitioInmuebles.cancelarReserva(reserva);
		this.reservasRealizadas.remove(reserva);
	}
	
    public List<Rankeo> getRankeos() {
		return this.rankeos;
	}

    public void visualizar(IVisualizable visualizable) {
        visualizable.visualizar();
    }

	public void aceptarReserva(Reserva reserva) throws EmailAdressNotFound {        
		String destinatario = this.mail;
		String destino = reserva.getInquilino().getMail();
        Mail mail = new Mail(destinatario, destino, "Aceptación de Reserva", "Se aceptó su reserva. Se adjunta a continuación", reserva);
        
        this.sitioInmuebles.aprobarReserva(reserva);
        this.enviarMail(mail);
	}

	public void rechazarReserva(Reserva reserva) throws Exception {
		this.validarRechazo(reserva);
		
		sitioInmuebles.rechazarReserva(reserva);
	}
	
	public void enviarMail(Mail mail) throws EmailAdressNotFound {
		this.mailServer.enviarMail(mail);
	}

	public void recibirMail(Mail mail) {
		// TODO Auto-generated method stub
	}
	
	// Privates
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
	
	public int cuantosInmueblesAlquilo() {
		return this.inmueblesAlquiladosInquilino;
	}
	
	public SitioInmuebles getSitioInmuebles() {
		return this.sitioInmuebles;
	}

	@Override
	public void visualizar() {
		// TODO Auto-generated method stub
		
	}
	
}









