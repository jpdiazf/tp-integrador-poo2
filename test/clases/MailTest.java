package clases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MailTest {
	Mail mail;
	Reserva reserva;
	
	@BeforeEach
	void setUp() {
		reserva = mock(Reserva.class);
		mail = new Mail("rodri@unq.edu.ar", "martin@unq.edu.ar", "Concreci�n de Reserva", "Se adjunta la concreci�n de la Reserva", reserva);
	}
	
	@Test
	void testConstructor() {
		assertEquals("rodri@unq.edu.ar", mail.getRemitente());
		assertEquals("martin@unq.edu.ar", mail.getDestinatario());
		assertEquals("Concreci�n de Reserva", mail.getAsunto());
		assertEquals("Se adjunta la concreci�n de la Reserva", mail.getCuerpo());
		assertEquals(reserva, mail.getArchivoAdjunto());
	}
}
