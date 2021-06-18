package clases;

import java.util.HashMap;
import java.util.Map;

import excepciones.EmailAdressNotFound;

public class MailServer {
	private Map<String, Usuario> direcciones = new HashMap<>();
	
	public void agregarUsuario(Usuario usuario) {
		String direccion = usuario.getMail();
		
		this.direcciones.put(direccion, usuario);
	}
	
    public void enviarMail(Mail mail) throws EmailAdressNotFound {
    	this.validarEnvio(mail);
    	
    	String direccion = mail.getDestinatario();
        Usuario usuarioDestino = this.getUsuario(direccion);

        usuarioDestino.recibirMail(mail);
    }

	public Usuario getUsuario(String direccion) {
		return direcciones.get(direccion);
	}
	
	// Privates
	
	private void validarEnvio(Mail mail) throws EmailAdressNotFound {
		String direccion = mail.getDestinatario();
		boolean tieneUsuario = this.direcciones.containsKey(direccion);
		
		if(!tieneUsuario) {
			throw new EmailAdressNotFound("La dirección de mail proporcionada no se encuentra asociada a ningún Usuario");
		}
	}
}
