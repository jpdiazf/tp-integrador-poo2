import java.security.DrbgParameters.Reseed;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements ISuscriptorReserva {
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

    public List<Reserva> getReservasRecibidas() {
        return this.reservasRecibidas;
    }

	public void ponerEnAlquiler(Inmueble inmueble) {
		this.sitio.agregarInmueble(inmueble);
	}
    
	public void realizarReserva(Inmueble inmueble, IFormaDePago formaDePago) {
        Usuario propietario = inmueble.getPropietario();
        
        this.sitio.recibirReserva(inmueble, formaDePago, this);
        propietario.recibirReserva(inmueble, formaDePago);
        this.reservasRecibidas.add(new Reserva()); // TODO
    }

	// public void rankear(Rankeo rankeo) {
    //     rankeo.getUsuario().recibirRankeo(rankeo);
    // }

	// private void recibirRankeo(Rankeo rankeo) {
    //     rankeos.add(rankeo);
    // }

    // public List<Rankeo> getRankeos() {
	// 	return this.rankeos;
	// }

    public void recibirReserva(Reserva reserva) {
        this.reservasRecibidas.add(reserva);
    }

    public void visualizar(IVisualizable visualizable) {
        visualizable.visualizar();
    }

//	public void aceptarReserva(Reserva reserva) {        
//        // TODO: testear esta instancia.
//		String direccionMail = reserva.getInquilino().getMail();
//		
//        Mail mail = new Mail(direccionMail, "Aceptación de Reserva", "Se aceptó su reserva. Se adjunta a continuación", reserva);
//        
//        this.mailServer.enviarMail(mail);
//        this.sitio.aceptarReserva(reserva);
//	}
}