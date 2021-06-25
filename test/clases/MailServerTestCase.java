package clases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.EmailAdressNotFound;

class MailServerTestCase {
	MailServer mailServer;
	Mail mail;
	Mail mailFallido;
	Usuario rodri;
	
	@BeforeEach
	void setUp() {
		mailServer = new MailServer();
		rodri = mock(Usuario.class);
		mail = mock(Mail.class);
		mailFallido = mock(Mail.class);
		String direccion = "rodri@unq.edu.ar";
		
		when(mail.getDestinatario()).thenReturn(direccion);
		when(mailFallido.getDestinatario()).thenReturn("rrodri@unq.edu.ar");
		when(rodri.getMail()).thenReturn(direccion);
		mailServer.agregarUsuario(rodri);  
	}
	
	@Test
	void elServidorAgregaUnUsuarioAlSistemaTest() {
		String direccion = rodri.getMail();
		Usuario actual = mailServer.getUsuario(direccion);		
		
		assertEquals(rodri, actual);
	}
	
	@Test
	void elServidorEnviaUnMailAUnUsuarioTest() throws EmailAdressNotFound {
		mailServer.enviarMail(mail);
		
		verify(rodri, times(1)).recibirMail(mail);
	}
	
	@Test
	void elServidorEnviaUnMailAUnUsuarioYFallaTest() throws EmailAdressNotFound {		
		assertThrows(EmailAdressNotFound.class, () -> mailServer.enviarMail(mailFallido));
	}

}
