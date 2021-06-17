import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		
		verify(sitio, times(1)).agregarInmueble(departamento);
	}

	// TODO: PREGUNTAR POR EL PROTOCOLO DE ESTE MÉTODO
	// @Test
	// void usuarioRealizaUnaBusquedaEnElSitioTest() {
	// 	rodri.realizarBusqueda();

	// 	sitio.realizarBusqueda();
	// }

	// @Test
	// void usuarioRealizaUnaReservaEnElSitioTest() {
	// 	rodri.realizarReserva(departamento, transferencia);
        
    //     verify(sitio, times(1)).recibirReserva(departamento, transferencia, rodri);
	// }

    // @Test
    // void usuarioRankeaAUnaEntidadTest() {
    //     String comentario = "Muy copado";
    //     Map<String, Integer> categorias = new HashMap<>();
    //     Rankeo rankeo = new Rankeo(martin, comentario, categorias);
        
    //     categorias.put("Propietario", 5);
        
    //     rodri.rankear(rankeo);
        
    //     boolean testedValue = martin.getRankeos().contains(rankeo);
    //     assertTrue(testedValue);
    // }

    // TODO: Aregar los métodos de checkIn y checkOut.
    // void usuarioRankeaAUnaEntidadPeroFallaTest() throws Exception {
    //     String comentario = "Un capo";
    //     Map<String, Integer> categorias = new HashMap<>();
    //     Rankeo rankeo = new Rankeo(martin, comentario, categorias);
        
    //     categorias.put("Inquilino", 4);
        
    //     rodri.rankear(rankeo);
        
    //     boolean testedValue = martin.getRankeos().contains(rankeo);
    //     assertTrue(testedValue);
    // }

    // TODO: Preguntar por el testeo de elementos de la consola.
    @Test
    void usuarioVisualizaUnVisualizableTest() {
        rodri.visualizar(departamento);

        verify(departamento, times(1)).visualizar();
    }

    @Test
    void rodriRecibeUnaReservaDeMartinTest() {
        when(departamento.getPropietario()).thenReturn(rodri);

        martin.realizarReserva(departamento, transferencia);
        // TODO: preguntar por el monto. Además el pago podría ser al usuario.
        
        int expected = rodri.getReservasRecibidas().size();
        int actual = 1;

        assertEquals(expected, actual);
    }

//    @Test
//    void usuarioAceptaUnaReservaTest() {
//         Reserva reserva = new Reserva();
//         MailServer mailServer = mock(MailServer.class);
//         Mail mail = mock(Mail.class);
//
//         rodri.aceptarReserva(reserva);
//
//         verify(mailServer, times(1)).enviarMail(Mail.class);
//         verify(sitio, times(1)).aceptarReserva(reserva);
//    }

    // @Test
    // void usuarioCancelaUnaReservaTest() {
    //     rodri.realizarReserva(departamento, transferencia);
    //     rodri.cancelarReserva(reserva);
    // }
}