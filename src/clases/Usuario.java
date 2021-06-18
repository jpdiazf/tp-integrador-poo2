package clases;
import java.util.ArrayList;
import java.util.List;

import excepciones.EmailAdressNotFound;
import excepciones.ReservationNotFound;
import interfaces.IRankeable;
import interfaces.ISuscriptorReserva;
import interfaces.IVisualizable;

// TODO: que el sitio agregue el mail del usuario cuando se instancie.
public class Usuario implements ISuscriptorReserva, IRankeable {
	private SitioInmuebles sitio;
	private String nombre;
	private String mail;
	private String nroTelefono;
    private List<Rankeo> rankeos = new ArrayList<>();
    private List<Reserva> reservasRecibidas = new ArrayList<>();
    private List<Reserva> reservasRealizadas = new ArrayList<>();
    private MailServer mailServer;

	public Usuario(SitioInmuebles sitio, String nombre, String mail, String nroTelefono, MailServer mailServer) {
		this.sitio = sitio;
		this.nombre = nombre;
		this.mail = mail;
		this.nroTelefono = nroTelefono;
        this.mailServer = mailServer;
	}

	public String getMail() {
		return mail;
	}
	
    public List<Reserva> getReservasRecibidas() {
        return this.reservasRecibidas;
    }

	public void ponerEnAlquiler(Inmueble inmueble) {
		this.sitio.agregarInmueble(inmueble);
	}
    
	public void realizarReserva(Reserva reserva) {
        sitio.recibirReserva(reserva);
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
		
		this.sitio.cancelarReserva(reserva);
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
		String destino = reserva.getUsuario().getMail();
        Mail mail = new Mail(destinatario, destino, "Aceptación de Reserva", "Se aceptó su reserva. Se adjunta a continuación", reserva);
        
        this.sitio.aceptarReserva(reserva);
        this.enviarMail(mail);
	}

	public void rechazarReserva(Reserva reserva) throws Exception {
		this.validarRechazo(reserva);
		
		sitio.rechazarReserva(reserva);
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
}









