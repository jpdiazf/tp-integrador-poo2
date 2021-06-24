package ar.edu.unq.sitioInmueble;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import clases.*;
import excepciones.EmailAdressNotFound;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SitioInmueblesTest {

	private SitioInmuebles sitio;
	
	private PoliticaCancelacion p1;
	private PoliticaCancelacion p2;
	
	private String tipoInmueble1;
	private String tipoInmueble2;
	private String tipoInmueble3;
	
	private String servicioInmueble1;
	private String servicioInmueble2;
	private String servicioInmueble3;

	private Usuario adan;
	private Inmueble casita;
	
	private String categoriaRankInmueble;
	private String categoriaRankInquilino;
	private String categoriaRankPropietario;
	
	LocalDate hoy = LocalDate.now();
	LocalDate maniana = LocalDate.now().plusDays(1);
	
	Reserva reservaDemo;
	GestorDeReservas nuevoGestorR;
	GestorDeNotificaciones nuevoGestorN;
	
	@BeforeEach
	void setUp() {
		
		adan = mock(Usuario.class);
		casita = mock(Inmueble.class);
		when(casita.getCiudad()).thenReturn("Quilmes");
		
		sitio = new SitioInmuebles();
		
		p1 = mock(PoliticaCancelacion.class);
		p2 = mock(PoliticaCancelacion.class);
		
		tipoInmueble1 = "Casa";
		tipoInmueble2 = "Habitación";
		tipoInmueble3 = "Quincho";
		
		servicioInmueble1 = "Aire acondicionado";
		servicioInmueble2 = "Calefaccion";
		servicioInmueble3 = "Agua Potable";
		
		reservaDemo = mock(Reserva.class);
		nuevoGestorR = mock(GestorDeReservas.class);
		nuevoGestorN = mock(GestorDeNotificaciones.class);
		
	}
	
	
	@Test
	void testConstructorAltas() {
		
		assertTrue(sitio.getInmueblesDeAlta().isEmpty());
		assertTrue(sitio.getPoliticasCancelacion().isEmpty());
		assertTrue(sitio.getReservas().isEmpty());
		assertTrue(sitio.getServiciosInmueble().isEmpty());
		assertTrue(sitio.getUsuarios().isEmpty());
		
		sitio.darDeAltaPoliticaCancelacion(p1);
		sitio.darDeAltaPoliticaCancelacion(p2);
		
		assertEquals(sitio.getPoliticasCancelacion().size(), 2);
		
		sitio.darDeAltaTipoInmueble(tipoInmueble1);
		sitio.darDeAltaTipoInmueble(tipoInmueble2);
		sitio.darDeAltaTipoInmueble(tipoInmueble3);
		
		assertEquals(sitio.getTiposInmueble().size(), 3);
		
		sitio.darDeAltaServicio(servicioInmueble1);
		sitio.darDeAltaServicio(servicioInmueble2);
		sitio.darDeAltaServicio(servicioInmueble3);
		
		assertEquals(sitio.getServiciosInmueble().size(), 3);
		
		sitio.darDeAltaUsuario(adan);
		
		assertEquals(sitio.getUsuarios().size(), 1);
		
		sitio.darDeAltaInmueble(casita);
		
		assertEquals(sitio.getInmueblesDeAlta().size(), 1);
		
		assertTrue(sitio.getCategoriasRankeables(Entidad.INMUEBLE).size() == 0);
		assertTrue(sitio.getCategoriasRankeables(Entidad.INQUILINO).size() == 0);
		assertTrue(sitio.getCategoriasRankeables(Entidad.PROPIETARIO).size() == 0);
		
		sitio.darDeAltaCategoriaPara(Entidad.INMUEBLE, categoriaRankInmueble);
		sitio.darDeAltaCategoriaPara(Entidad.INQUILINO, categoriaRankInquilino);
		sitio.darDeAltaCategoriaPara(Entidad.PROPIETARIO, categoriaRankPropietario);
		
		assertTrue(sitio.getCategoriasRankeables(Entidad.INMUEBLE).contains(categoriaRankInmueble));
		assertTrue(sitio.getCategoriasRankeables(Entidad.INQUILINO).contains(categoriaRankInquilino));
		assertTrue(sitio.getCategoriasRankeables(Entidad.PROPIETARIO).contains(categoriaRankPropietario));
		
	}
	
	
	@Test
	void testBusquedaDeInmueblePorCiudad() {
		
		sitio.darDeAltaUsuario(adan);
		sitio.darDeAltaInmueble(casita);
		
		ArrayList<Inmueble> inmueblesFiltrados = new ArrayList<Inmueble>();
		
		inmueblesFiltrados = sitio.realizarBusqueda("Quilmes", LocalDate.now(), LocalDate.now().plusDays(2), null, null, null);
		
		assertTrue(inmueblesFiltrados.contains(casita));
		
		//NUEVO INMUEBLE
		Inmueble habitacion = mock(Inmueble.class);
		
		sitio.darDeAltaInmueble(habitacion);
		when(habitacion.getCiudad()).thenReturn("Quilmes");
		
		inmueblesFiltrados = sitio.realizarBusqueda("Quilmes", LocalDate.now(), LocalDate.now().plusDays(3), null, null, null);
		
		assertTrue(inmueblesFiltrados.contains(habitacion));
		
		assertEquals(inmueblesFiltrados.size(), 2);
		
		//NUEVO INMUEBLE
		Inmueble quincho = mock(Inmueble.class);
		when(quincho.getCiudad()).thenReturn("Berazategui");
		
		inmueblesFiltrados = sitio.realizarBusqueda("Quilmes", LocalDate.now(), LocalDate.now().plusDays(4), null, null, null);
		
		assertFalse(inmueblesFiltrados.contains(quincho));
		
	}
	
	@Test
	void testBusquedaDeInmueblesPorParametrosOpcionales() {
		
		Inmueble quincho = mock(Inmueble.class);
		Inmueble habitacion = mock(Inmueble.class);
		
		sitio.darDeAltaUsuario(adan);
		sitio.darDeAltaInmueble(casita);
		sitio.darDeAltaInmueble(habitacion);
		sitio.darDeAltaInmueble(quincho);
		
		when(habitacion.getCiudad()).thenReturn("Quilmes");
		when(quincho.getCiudad()).thenReturn("Berazategui");
		
		ArrayList<Inmueble> inmueblesFiltrados = new ArrayList<Inmueble>();
		
		when(casita.getCapacidad()).thenReturn(6);
		when(habitacion.getCapacidad()).thenReturn(2);
		when(quincho.getCapacidad()).thenReturn(20);
		
		LocalDate hoy = LocalDate.now();
		LocalDate mañana = LocalDate.now().plusDays(1);
		
		when(casita.getPrecioPorPeriodo(hoy, mañana)).thenReturn(1000d);
		when(habitacion.getPrecioPorPeriodo(hoy, mañana)).thenReturn(500d);
		when(quincho.getPrecioPorPeriodo(hoy, mañana)).thenReturn(2500d);
		
		//BÚSQUEDA CANTIDAD HUÉSPEDES
		inmueblesFiltrados = sitio.realizarBusqueda("Quilmes", hoy, mañana, 6, null, null);
		//habitación es de Quilmes y está disponible, ¡pero sólo alberga 2 personas! (quincho es de Berazategui)
		assertTrue(inmueblesFiltrados.contains(casita) && !inmueblesFiltrados.contains(habitacion));
		
		//BÚSQUEDA PRECIO MÍNIMO
		inmueblesFiltrados = sitio.realizarBusqueda("Quilmes", hoy, mañana, null, 2000d, null); 
		//casa y habitación son de Quilmes y están disponibles, ¡pero sus precios no superan al mínimo! (quincho es de Berazategui)
		assertEquals(inmueblesFiltrados.size(), 0);
		
		//BÚSQUEDA PRECIO MÁXIMO
		inmueblesFiltrados = sitio.realizarBusqueda("Quilmes", hoy, mañana, null, null, 800d);
		//casa es de Quilmes y está disponible, ¡pero el precio supera al máximo!
		assertTrue(inmueblesFiltrados.contains(habitacion) && !inmueblesFiltrados.contains(casita));
		
		
		//NUEVOS INMUEBLES
		Inmueble depto = mock(Inmueble.class);
		Inmueble cabania = mock(Inmueble.class);
		
		when(depto.getCiudad()).thenReturn("Quilmes");
		when(cabania.getCiudad()).thenReturn("Berazategui");
		
		when(depto.getCapacidad()).thenReturn(4);
		when(cabania.getCapacidad()).thenReturn(8);
		
		when(depto.getPrecioPorPeriodo(hoy, mañana)).thenReturn(1500d);
		when(cabania.getPrecioPorPeriodo(hoy, mañana)).thenReturn(2000d);
		
		sitio.darDeAltaInmueble(depto);
		sitio.darDeAltaInmueble(cabania);
		
		/**
		 * Hasta ahora, los inmuebles dados de alta:
		 * Quilmes -> (casita, $1000, 6pers), (habitacion, $500, 2pers), (depto, $1500, 4pers)
		 * Berazategui -> (quincho, $2500, 20pers), (cabania, $2000, 8pers)
		 */
		
		//BÚSQUEDAS TOTALES
		inmueblesFiltrados = sitio.realizarBusqueda("Quilmes", hoy, mañana, 4, 1000d, 1400d);
		//habitacion es de Quilmes pero sólo admite 2 personas; depto es de quilmes pero supera el precio máximo de $1400
		assertTrue(inmueblesFiltrados.contains(casita) && inmueblesFiltrados.size() == 1);
		
		inmueblesFiltrados = sitio.realizarBusqueda("Berazategui", hoy, mañana, 8, 2000d, 2500d);
		//ambos inmuebles de Berazategui cumplen con cada parámetro
		assertTrue(inmueblesFiltrados.contains(quincho) && inmueblesFiltrados.contains(cabania) && inmueblesFiltrados.size() == 2);
		
		
		
		//SE BUSCA PERO NO APARECEN RESULTADOS DEBIDO A QUE HAY UNA RESERVA!!!
		
		sitio.setGestorReservas(nuevoGestorR);
		
		when(nuevoGestorR.hayReservaEn(casita, hoy, mañana)).thenReturn(true); //ahora tengo entre mis reservas, a la correspondiente a la casita.
		
		
		//PRIMERA BÚSQUEDA TOTAL, DE NUEVO
		inmueblesFiltrados = sitio.realizarBusqueda("Quilmes", hoy, mañana, 4, 1000d, 1400d);
		
		verify(nuevoGestorR, times(1)).hayReservaEn(casita, hoy, mañana);
		//habitacion es de Quilmes pero sólo admite 2 personas; depto es de quilmes pero supera el precio máximo de $1400.
		//sólo corresponde la casita, pero esta se encuentra ya reservada!!!
		assertFalse(inmueblesFiltrados.contains(casita) && inmueblesFiltrados.size() == 1);
		
	}
	
	@Test
	void testRealizacionesReservas() throws Exception {
		
		//REALIZACIÓN EXITOSA
		
		when(reservaDemo.getInmueble()).thenReturn(casita);
		when(reservaDemo.getComienzo()).thenReturn(hoy);
		when(reservaDemo.getFin()).thenReturn(maniana);
		
		sitio.darDeAltaInmueble(casita);
		
		when(casita.estaPublicadoPeriodo(hoy, maniana)).thenReturn(true);
		
		assertFalse(sitio.getGestorReservas().getReservas().contains(reservaDemo));
		
		sitio.recibirReserva(reservaDemo);
		
		assertTrue(sitio.getGestorReservas().getReservas().contains(reservaDemo));
		
		
		//REALIZACIÓN CON ERROR
		
		Reserva reservaDemo2 = mock(Reserva.class);
		Inmueble inmuebleDemo = mock(Inmueble.class);
		
		when(reservaDemo2.getInmueble()).thenReturn(inmuebleDemo);
		when(reservaDemo2.getComienzo()).thenReturn(hoy);
		when(reservaDemo2.getFin()).thenReturn(maniana);
		
		//¡no doy de alta el inmueble en el sitio!
		
		when(inmuebleDemo.estaPublicadoPeriodo(hoy, maniana)).thenReturn(true);
		when(inmuebleDemo.getInformacion()).thenReturn("inmueble de prueba");
		
		Exception excepcion = assertThrows(Exception.class, () -> sitio.recibirReserva(reservaDemo2));
		
		assertEquals("No se encuentra disponible para reservar el inmueble " + inmuebleDemo.getInformacion(), excepcion.getMessage());
		
		
		//doy de alta el inmueble, pero no se encuentra publicado para el período.
		
		sitio.darDeAltaInmueble(inmuebleDemo);
		
		when(inmuebleDemo.estaPublicadoPeriodo(hoy, maniana)).thenReturn(false);
		
		excepcion = assertThrows(Exception.class, () -> sitio.recibirReserva(reservaDemo2));
		
		assertEquals("No se encuentra disponible para reservar el inmueble " + inmuebleDemo.getInformacion(), excepcion.getMessage());
		
	}
	
	
	@Test
	void testAceptacionReserva() throws Exception {
		
		sitio.setGestorReservas(nuevoGestorR);
		
		Reserva reservaDemo = mock(Reserva.class);
		
		sitio.aprobarReserva(reservaDemo);
		
		verify(nuevoGestorR, times(1)).aprobarReserva(reservaDemo);
		
	}
	
	
	@Test
	void testRechazoReserva() throws Exception {
		
		sitio.setGestorReservas(nuevoGestorR);
		
		sitio.rechazarReserva(reservaDemo);
		
		verify(nuevoGestorR, times(1)).rechazarReserva(reservaDemo);
		
	}
	
	
	@Test
	void testCancelacionReserva() throws Exception {
		
		sitio.setGestorReservas(nuevoGestorR);
		sitio.setGestorNotificaciones(nuevoGestorN);
		
		sitio.cancelarReserva(reservaDemo);
		
		verify(nuevoGestorR, times(1)).cancelarReserva(reservaDemo);
		
		verify(nuevoGestorN, times(1)).notificarCancelacionDeReserva(reservaDemo);
		
	}

	
	@Test
	void testListadosDeGestion() {
		
		//TOP TEN INQUILINOS ACTIVOS
		//mockeo 15 usuarios
		
		Usuario pablo = mock(Usuario.class);
		Usuario marcos = mock(Usuario.class); 
		Usuario martin = mock(Usuario.class); 
		Usuario samuel = mock(Usuario.class); 
		Usuario nacho = mock(Usuario.class); 
		Usuario laura = mock(Usuario.class); 
		Usuario paola = mock(Usuario.class); 
		Usuario veronica = mock(Usuario.class); 
		Usuario malena = mock(Usuario.class); 
		Usuario ezequiel = mock(Usuario.class); 
		Usuario daniela = mock(Usuario.class); 
		Usuario cesar = mock(Usuario.class); 
		Usuario julio = mock(Usuario.class); 
		Usuario goku = mock(Usuario.class); 
		Usuario naruto = mock(Usuario.class); 
		
		sitio.darDeAltaUsuario(pablo);
		sitio.darDeAltaUsuario(marcos);
		sitio.darDeAltaUsuario(martin);
		sitio.darDeAltaUsuario(samuel);
		sitio.darDeAltaUsuario(nacho);
		sitio.darDeAltaUsuario(laura);
		sitio.darDeAltaUsuario(paola);
		sitio.darDeAltaUsuario(veronica);
		sitio.darDeAltaUsuario(malena);
		sitio.darDeAltaUsuario(ezequiel);
		sitio.darDeAltaUsuario(daniela);
		sitio.darDeAltaUsuario(cesar);
		sitio.darDeAltaUsuario(julio);
		sitio.darDeAltaUsuario(goku);
		sitio.darDeAltaUsuario(naruto);
		
		
		when(pablo.cuantosInmueblesAlquilo()).thenReturn(20);
		when(marcos.cuantosInmueblesAlquilo()).thenReturn(25);
		when(martin.cuantosInmueblesAlquilo()).thenReturn(21);
		when(samuel.cuantosInmueblesAlquilo()).thenReturn(18);
		when(nacho.cuantosInmueblesAlquilo()).thenReturn(15);
		when(laura.cuantosInmueblesAlquilo()).thenReturn(25);
		when(paola.cuantosInmueblesAlquilo()).thenReturn(16);
		when(veronica.cuantosInmueblesAlquilo()).thenReturn(14); //pocos
		when(malena.cuantosInmueblesAlquilo()).thenReturn(18);
		when(ezequiel.cuantosInmueblesAlquilo()).thenReturn(5); //pocos
		when(daniela.cuantosInmueblesAlquilo()).thenReturn(20);
		when(cesar.cuantosInmueblesAlquilo()).thenReturn(21);
		when(julio.cuantosInmueblesAlquilo()).thenReturn(8); //pocos
		when(goku.cuantosInmueblesAlquilo()).thenReturn(1); //pocos
		when(naruto.cuantosInmueblesAlquilo()).thenReturn(1); //pocos
		
		
		ArrayList<Usuario> inquilinosActivosCheck = sitio.topTenInquilinosActivos();
		
		assertEquals(inquilinosActivosCheck.size(), 10);
		
		assertTrue(inquilinosActivosCheck.contains(pablo));
		assertTrue(inquilinosActivosCheck.contains(marcos));
		assertTrue(inquilinosActivosCheck.contains(martin));
		assertTrue(inquilinosActivosCheck.contains(samuel));
		assertTrue(inquilinosActivosCheck.contains(nacho));
		assertTrue(inquilinosActivosCheck.contains(laura));
		assertTrue(inquilinosActivosCheck.contains(paola));
		assertFalse(inquilinosActivosCheck.contains(veronica));
		assertTrue(inquilinosActivosCheck.contains(malena));
		assertFalse(inquilinosActivosCheck.contains(ezequiel));
		assertTrue(inquilinosActivosCheck.contains(daniela));
		assertTrue(inquilinosActivosCheck.contains(cesar));
		assertFalse(inquilinosActivosCheck.contains(julio));
		assertFalse(inquilinosActivosCheck.contains(goku));
		assertFalse(inquilinosActivosCheck.contains(naruto));
		
	
		//INMUEBLES LIBRES
		
		//MOCKS INMUEBLES A AGREGAR
		Inmueble casa = mock(Inmueble.class);
		Inmueble depto = mock(Inmueble.class);
		Inmueble quincho = mock(Inmueble.class);
		Inmueble habitacion = mock(Inmueble.class);
		Inmueble cabania = mock(Inmueble.class);
		
		sitio.darDeAltaInmueble(casa);
		sitio.darDeAltaInmueble(depto);
		sitio.darDeAltaInmueble(quincho);
		sitio.darDeAltaInmueble(habitacion);
		sitio.darDeAltaInmueble(cabania);
		
		
		//MOCKS RESERVAS
		Reserva reservaEj1 = mock(Reserva.class);
		Reserva reservaEj2 = mock(Reserva.class);
		Reserva reservaEj3 = mock(Reserva.class);
		
		when(reservaEj1.getInmueble()).thenReturn(depto);
		when(reservaEj2.getInmueble()).thenReturn(quincho);
		when(reservaEj3.getInmueble()).thenReturn(habitacion);
		
		ArrayList<Reserva> reservados = new ArrayList<Reserva>();
		reservados.add(reservaEj1);
		reservados.add(reservaEj2);
		reservados.add(reservaEj3);
		
		when(nuevoGestorR.getReservas()).thenReturn(reservados);
		
		//SET DEL GESTOR
		sitio.setGestorReservas(nuevoGestorR);
		
		//HAY RESERVAS
		assertTrue(sitio.estaReservado(depto));	
		assertTrue(sitio.estaReservado(quincho));	
		assertTrue(sitio.estaReservado(habitacion));
		assertFalse(sitio.estaReservado(casa));	
		assertFalse(sitio.estaReservado(cabania));	
		
		
		when(nuevoGestorR.hayReservaHoy(depto)).thenReturn(true);
		when(nuevoGestorR.hayReservaHoy(quincho)).thenReturn(true);
		when(nuevoGestorR.hayReservaHoy(habitacion)).thenReturn(true);
		
		ArrayList<Inmueble> inmueblesLibres = sitio.mostrarInmueblesLibres();
		
		assertTrue(inmueblesLibres.size() == 2);
		
		assertTrue(inmueblesLibres.contains(casa));
		assertTrue(inmueblesLibres.contains(cabania));
		assertFalse(inmueblesLibres.contains(depto));
		assertFalse(inmueblesLibres.contains(quincho));
		assertFalse(inmueblesLibres.contains(habitacion));
		
	}
	
	
}
