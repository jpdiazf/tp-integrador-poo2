package clases;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.sitioInmueble.SitioInmuebles;
import clases.Inmueble;
import clases.MailServer;
import clases.Rankeo;
import clases.Usuario;
import excepciones.ReservationNotFound;
import interfaces.IFormaDePago;

class UsuarioTest {
	private Usuario rodri;
	private Usuario martin;
	private Inmueble departamento;
	private SitioInmuebles sitio;
    private IFormaDePago transferencia;
    private MailServer mailServer;
	
	@BeforeEach
	void setUp() {
		sitio = mock(SitioInmuebles.class);
        mailServer = mock(MailServer.class);
		rodri = new Usuario(sitio, "Rodri", "rodri@unq.edu.ar", "1111111111", mailServer);
        martin = new Usuario(sitio, "Martin", "martin@unq.edu.ar", "2222222222", mailServer);
		departamento = mock(Inmueble.class);
        transferencia = mock(IFormaDePago.class);
	}
	
	@Test
	void usuarioPoneEnAlquilerUnInmuebleTest() {
		rodri.ponerEnAlquiler(departamento);
		
		verify(sitio, times(1)).darDeAltaInmueble(departamento);
	}

	// TODO: PREGUNTAR POR EL PROTOCOLO DE ESTE MÃ‰TODO
	// @Test
	// void usuarioRealizaUnaBusquedaEnElSitioTest() {
	// 	rodri.realizarBusqueda();

	// 	sitio.realizarBusqueda();
	// }
    
	@Test
	void usuarioRealizaUnaReservaEnElSitioTest() {
        Reserva reserva = mock(Reserva.class);
		rodri.realizarReserva(reserva);
        
        verify(sitio, times(1)).recibirReserva(reserva);
	}

    @Test
    void usuarioRankeaAUnaEntidadTest() {
    	Rankeo rankeo = mock(Rankeo.class);
        when(rankeo.getRankeable()).thenReturn(martin);
        rodri.rankear(rankeo);        
        
        boolean testedValue = martin.getRankeos().contains(rankeo);
        assertTrue(testedValue);
    }

//    TODO: qué hacemos con las ocupaciones?
//    void usuarioRankeaAUnaEntidadPeroFallaTest() throws Exception {
//    	Rankeo rankeo = mock(Rankeo.class);
//        when(rankeo.getRankeable()).thenReturn(martin);
//        rodri.rankear(rankeo);
//        
//        boolean testedValue = martin.getRankeos().contains(rankeo);
//        assertTrue(testedValue);
//    }

    @Test
    void usuarioVisualizaUnVisualizableTest() {
        rodri.visualizar(departamento);

        verify(departamento, times(1)).visualizar();
    }

    @Test
    void rodriRecibeUnaReservaDeMartinTest() {
    	Reserva reserva = mock(Reserva.class);
    	when(departamento.getPropietario()).thenReturn(rodri);
    	
        martin.realizarReserva(reserva);

        verify(sitio, times(1)).recibirReserva(reserva);
    }

    @Test
    void usuarioAceptaUnaReservaTest() {
        Reserva reserva = mock(Reserva.class);
        MailServer mailServer = mock(MailServer.class);
        Mail mail = mock(Mail.class);
         
        when(reserva.getInquilino()).thenReturn(martin);
        when(mail.getDestinatario()).thenReturn(martin.getMail());
         
        rodri.aceptarReserva(reserva);

        String expected = "martin@unq.edu.ar";
        String actual = mail.getDestinatario();
         
        verify(sitio, times(1)).aprobarReserva(reserva);
        assertEquals(expected, actual);
        verify(mailServer, times(1));
        
    }

    @Test
    void usuarioCancelaUnaReservaTest() throws Exception {
    	Reserva reserva = mock(Reserva.class);
        rodri.realizarReserva(reserva);
        rodri.cancelarReserva(reserva);
        
        verify(sitio, times(1)).cancelarReserva(reserva);
    }
    
//    TODO: ver qué onda con este test.
    @Test
    void usuarioCancelaUnaReservaYFallaTest() throws Exception {
    	Reserva reserva = mock(Reserva.class);

    	assertThrows(ReservationNotFound.class, () -> rodri.cancelarReserva(reserva));
    }
    
    @Test
    void usuarioRechazaUnaReservaDeOtroUsuarioTest() throws Exception {
    	Reserva reserva = mock(Reserva.class);
    	
    	assertThrows(ReservationNotFound.class, () -> rodri.rechazarReserva(reserva));
    }
}





