package clases;

public class Mail {
    private String direccionRemitente;
	private String direccionDestinatario;
	private String asunto;
	private String cuerpo;
	private Object archivoAdjunto;

	public Mail(String direccionRemitente, String direccionDestinatario, String asunto, String cuerpo, Object archivoAdjunto) {
    	this.direccionRemitente = direccionRemitente;
    	this.direccionDestinatario = direccionDestinatario;
    	this.asunto = asunto;
    	this.cuerpo = cuerpo;
    	this.archivoAdjunto = archivoAdjunto;
    }

	// Getters
	
	public String getDestinatario() {
		return this.direccionDestinatario;
	}

	public String getRemitente() {
		return this.direccionRemitente;
	}

	public String getAsunto() {
		return this.asunto;
	}

	public String getCuerpo() {
		return this.cuerpo;
	}

	public Object getArchivoAdjunto() {
		return this.archivoAdjunto;
	}
}