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
	
	private IGraphicalUserInterface gui;
	private Usuario adan;
	private Inmueble casita;
	
	
	
	@BeforeEach
	void setUp() {
		
		gui = mock(IGraphicalUserInterface.class);
		adan = mock(Usuario.class);
		casita = mock(Inmueble.class);
		
		sitio = new SitioInmuebles(gui);
		
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
	void BusquedaDeInmuebles() {
		
		sitio.darDeAltaUsuario(adan);
		sitio.darDeAltaInmueble(casita);
		
		when(casita.getCiudad()).thenReturn("Quilmes");
		
		
		sitio.realizarBusqueda("Quilmes", LocalDate.now(), LocalDate.now().plusDays(2), null, null, null);
		
		verify(gui, times(1)).mostrarEnPantalla();
		
	}

}
