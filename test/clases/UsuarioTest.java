package clases;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.sitioInmueble.SitioInmuebles;
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
	void constructorTest() {  
        Usuario paula = new Usuario(sitio, "Paula", "paula@unq.edu.ar", "1234567890", mailServer);
        
        assertEquals(sitio, paula.getSitioInmuebles());
        assertEquals("Paula", paula.getNombre());
        assertEquals("paula@unq.edu.ar", paula.getMail());
        assertEquals("1234567890", paula.getNroTelefono());
        assertEquals(mailServer, paula.getMailServer());
        assertEquals(0, paula.getReservasRecibidas().size());
        assertEquals(0, paula.getReservasRealizadas().size());
        assertEquals(0, paula.getMailsRecibidos().size());
        assertEquals(0, paula.getInmueblesPreferidos().size());
        assertEquals(0, paula.cuantosInmueblesAlquilo());
	}
	
	@Test
	void usuarioPoneEnAlquilerUnInmuebleTest() {
		rodri.ponerEnAlquiler(departamento);
		
		verify(sitio, times(1)).darDeAltaInmueble(departamento);
	}

	@Test
	void usuarioRealizaUnaBusquedaEnElSitioTest() {
		LocalDate fechaEntrada = LocalDate.of(2021, 6, 27);
		LocalDate fechaSalida = LocalDate.of(2021, 7, 15);
		rodri.realizarBusqueda("Berazategui", fechaEntrada, fechaSalida, 4, 3000d, 4000d);

		verify(sitio, times(1)).realizarBusqueda("Berazategui", fechaEntrada, fechaSalida, 4, 3000d, 4000d);
	}
    
	@Test
	void usuarioRealizaUnaReservaEnElSitioTest() throws Exception{
        Reserva reserva = mock(Reserva.class);
		rodri.realizarReserva(reserva);
        
        verify(sitio, times(1)).recibirReserva(reserva);
	}

//    @Test
//    void usuarioRankeaAUnaEntidadTest() {
//    	Rankeo rankeo = mock(Rankeo.class);
//        when(rankeo.getRankeable()).thenReturn(martin);
//        rodri.rankear(rankeo);        
//        
//        boolean testedValue = martin.getRankeos().contains(rankeo);
//        assertTrue(testedValue);
//    }
//
//    void usuarioRankeaAUnaEntidadPeroFallaTest() throws Exception {
//    	Rankeo rankeo = mock(Rankeo.class);
//        when(rankeo.getRankeable()).thenReturn(martin);
//        rodri.rankear(rankeo);
//        
//        boolean testedValue = martin.getRankeos().contains(rankeo);
//        assertTrue(testedValue);
//    }

//    @Test
//    void usuarioVisualizaUnVisualizableTest() {
//        rodri.visualizar(departamento);
//
//        verify(departamento, times(1)).visualizar();
//    }

    @Test
    void usuarioRecibeUnMailTest() {
    	Mail mail = mock(Mail.class);
        martin.recibirMail(mail);
        boolean actual = martin.getMailsRecibidos().contains(mail);
        
        assertTrue(actual);
    }
    
    @Test
    void rodriRecibeUnaReservaDeMartinTest() throws Exception{
    	Reserva reserva = mock(Reserva.class);
    	when(departamento.getPropietario()).thenReturn(rodri);
    	
        martin.realizarReserva(reserva);

        verify(sitio, times(1)).recibirReserva(reserva);
    }

    @Test
    void usuarioAceptaUnaReservaTest() throws Exception {
        Reserva reserva = mock(Reserva.class);
        Mail mail = mock(Mail.class);
         
        when(reserva.getInquilino()).thenReturn(martin);
        when(mail.getDestinatario()).thenReturn(martin.getMail());
         
        rodri.aceptarReserva(reserva);

        String expected = "martin@unq.edu.ar";
        String actual = mail.getDestinatario();
         
        verify(sitio, times(1)).aprobarReserva(reserva);
        assertEquals(expected, actual);
    }

    @Test
    void usuarioEnviaUnMailAOtroSinArchivoAdjunto() throws Exception {
    	Mail mail = mock(Mail.class);
    	rodri.enviarMail(mail);
    	
    	verify(mailServer, times(1)).enviarMail(mail);
    }
    
    @Test
    void usuarioRecibeUnMail() throws Exception {
    	Mail mail = mock(Mail.class);
    	martin.recibirMail(mail);
    	boolean actual = martin.getMailsRecibidos().contains(mail); 
    	
    	assertTrue(actual);
    }
    
    @Test
    void usuarioCancelaUnaReservaTest() throws Exception {
    	Reserva reserva = mock(Reserva.class);
        rodri.realizarReserva(reserva);
        rodri.cancelarReserva(reserva);
        
        verify(sitio, times(1)).cancelarReserva(reserva);
    }
    
    @Test
    void usuarioCancelaUnaReservaYFallaTest() throws Exception {
    	Reserva reserva = mock(Reserva.class);

    	assertThrows(ReservationNotFound.class, () -> rodri.cancelarReserva(reserva));
    }
    
    @Test
    void usuarioRechazaUnaReservaDeOtroUsuarioTest() throws Exception {
    	Reserva reserva = mock(Reserva.class);
    	rodri.recibirReserva(reserva);
    	rodri.rechazarReserva(reserva);
    	boolean actual = rodri.getReservasRecibidas().contains(reserva);
    	
    	verify(sitio, times(1)).rechazarReserva(reserva);
    	assertFalse(actual);
    }
    
    @Test
    void usuarioRechazaUnaReservaDeOtroUsuarioYFallaTest() throws Exception {
    	Reserva reserva = mock(Reserva.class);
    	
    	assertThrows(ReservationNotFound.class, () -> rodri.rechazarReserva(reserva));
    }
    
    @Test
    void todasLasReservasTest() throws Exception {
    	Reserva casa = mock(Reserva.class);
    	Reserva departamento = mock(Reserva.class);
    	
    	rodri.realizarReserva(casa);
    	rodri.realizarReserva(departamento);
    	
    	List<Reserva> todas = rodri.getTodasLasReservas();
    	
    	assertTrue(todas.contains(casa));
    	assertTrue(todas.contains(departamento));
    }
    
    @Test
    void reservasFuturasTest() throws Exception {
    	Reserva habitacion = mock(Reserva.class);
    	Reserva casa = mock(Reserva.class);
    	Reserva departamento = mock(Reserva.class);
    	LocalDate hoy = LocalDate.of(2021, 6, 24);
    	LocalDate ayer = hoy.minusDays(1);
    	LocalDate mañana = hoy.plusDays(1);
    	
    	when(habitacion.getComienzo()).thenReturn(hoy);
    	when(casa.getComienzo()).thenReturn(mañana);
    	when(departamento.getComienzo()).thenReturn(ayer);
    	
    	rodri.realizarReserva(habitacion);
    	rodri.realizarReserva(casa);
    	rodri.realizarReserva(departamento);
    	
    	List<Reserva> reservas = rodri.getReservasFuturas();
    	
    	assertTrue(reservas.contains(casa));
    	assertFalse(reservas.contains(departamento));
    	assertFalse(reservas.contains(habitacion));
    }
    
    @Test
    void reservasDeCiudadTest() throws Exception {
    	Reserva reservaHabitacion = mock(Reserva.class);
    	Reserva reservaCasa = mock(Reserva.class);
    	Reserva reservaDepartamento = mock(Reserva.class);
    	Inmueble habitacion = mock(Inmueble.class);
    	Inmueble casa = mock(Inmueble.class);
    	Inmueble departamento = mock(Inmueble.class);
    	
    	when(casa.getCiudad()).thenReturn("Berazategui");
    	when(habitacion.getCiudad()).thenReturn("Quilmes");
    	when(departamento.getCiudad()).thenReturn("Berazategui");
    	
    	when(reservaHabitacion.getInmueble()).thenReturn(habitacion);
    	when(reservaCasa.getInmueble()).thenReturn(casa);
    	when(reservaDepartamento.getInmueble()).thenReturn(departamento);
    	
    	rodri.realizarReserva(reservaHabitacion);
    	rodri.realizarReserva(reservaCasa);
    	rodri.realizarReserva(reservaDepartamento);
    	
    	List<Reserva> reservas = rodri.getReservasConCiudad("Berazategui");
    	
    	assertTrue(reservas.contains(reservaCasa));
    	assertTrue(reservas.contains(reservaDepartamento));
    	assertFalse(reservas.contains(reservaHabitacion));
    }
    
    @Test
    void ciudadesConReserva() throws Exception {
    	Reserva reservaHabitacion = mock(Reserva.class);
    	Reserva reservaCasa = mock(Reserva.class);
    	Reserva reservaDepartamento = mock(Reserva.class);
    	Inmueble habitacion = mock(Inmueble.class);
    	Inmueble casa = mock(Inmueble.class);
    	Inmueble departamento = mock(Inmueble.class);
    	
    	when(casa.getCiudad()).thenReturn("Berazategui");
    	when(habitacion.getCiudad()).thenReturn("Quilmes");
    	when(departamento.getCiudad()).thenReturn("Berazategui");
    	
    	when(reservaHabitacion.getInmueble()).thenReturn(habitacion);
    	when(reservaCasa.getInmueble()).thenReturn(casa);
    	when(reservaDepartamento.getInmueble()).thenReturn(departamento);
    	
    	rodri.realizarReserva(reservaHabitacion);
    	rodri.realizarReserva(reservaCasa);
    	rodri.realizarReserva(reservaDepartamento);
    	
    	List<String> ciudades = rodri.getCiudadesConReservas();
    	
    	assertTrue(ciudades.contains("Berazategui"));
    	assertTrue(ciudades.contains("Quilmes"));
    	assertEquals(2, ciudades.size());
    }
}





