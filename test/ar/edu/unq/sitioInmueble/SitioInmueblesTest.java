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

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unq.siteGUI.IGraphicalUserInterface;

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
		
		
	}
	
	
	
	

}
