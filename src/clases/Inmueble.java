package clases;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import interfaces.IFormaDePago;
import interfaces.IFoto;
import interfaces.IVisualizable;
import ar.edu.unq.sitioInmueble.*;

public class Inmueble implements IVisualizable {
	
	private Usuario propietario;
	private String tipo;
	private Double superficie;
	private String pais;
	private String ciudad;
	private String direccion;
	private List<String> servicios;
	private Integer capacidad;
	private List<IFoto> fotos;
	private LocalTime horarioCheckIn;
	private LocalTime horarioCheckOut;
	private PoliticaCancelacion politicaCancelacion;
	private List<IFormaDePago> formasDePagoAceptadas;
	private List<PrecioPeriodo> precios;
	private GestorDeRankeos gestorDeRankeos;
	private Integer vecesAlquilado;
	
	public Inmueble(Usuario propietario, String tipo, Double superficie, String pais, String ciudad,
					String direccion, List<String> servicios, Integer capacidad, List<IFoto> fotos,
					LocalTime horarioCheckIn, LocalTime horarioCheckOut, PoliticaCancelacion politicaCancelacion,
					List<IFormaDePago> formasDePagoAceptadas, List<PrecioPeriodo> precios) {
		
		this.propietario = propietario;
		this.tipo = tipo ;
		this.superficie = superficie;
		this.pais = pais;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.servicios = servicios ;
		this.capacidad = capacidad;
		this.fotos = fotos;
		this.horarioCheckIn = horarioCheckIn;
		this.horarioCheckOut = horarioCheckOut;
		this.politicaCancelacion = politicaCancelacion;
		this.formasDePagoAceptadas = formasDePagoAceptadas;
		this.precios = precios;
		this.vecesAlquilado = 0;
		this.gestorDeRankeos = new GestorDeRankeos();
		
	}
	
	public double getPrecioPorPeriodo(LocalDate desde, LocalDate hasta) {
		double total = 0;
		for (LocalDate fecha = desde; fecha.isBefore(hasta); fecha = fecha.plusDays(1)) {
		    total += this.getPrecio(fecha);
		}
		return total;
	}
	
	private double getPrecio(LocalDate fecha) {
		double retorno = 0; 
		for(PrecioPeriodo p : precios) {
			if (p.pertenceAlRango(fecha)) {
				retorno = p.valor();
				break;
			}
		}
		return retorno;
	}
	
	public boolean estaPublicadoPeriodo(LocalDate desde, LocalDate hasta) {
		for (LocalDate fecha = desde; fecha.isBefore(hasta); fecha = fecha.plusDays(1)) {
		    if(! this.estaPublicado(fecha)) {
		    	return false;
		    }
		}
		return true;
	}

	public void sumarVezAlquilado() {
		this.vecesAlquilado ++;
	}
	
	public void addRankeo(Rankeo rankeo) {
		this.gestorDeRankeos.addRankeo(rankeo);
	}
	
	private boolean estaPublicado(LocalDate fecha) {
		for(PrecioPeriodo p : precios) {
			if (p.pertenceAlRango(fecha)) {
				return true;
			}
		}
		return false;
	}

	public Usuario getPropietario() {
		return this.propietario;
	}
	public String getTipo() {
		return this.tipo;
	}
	public double getSuperficie() {
		return this.superficie;
	}
	public String getPais() {
		return this.pais;
	}
	public String getCiudad() {
		return this.ciudad;
	}
	public String getDireccion() {
		return this.direccion;
	}
	public List<String> getServicios() {
		return this.servicios;
	}
	public Integer getCapacidad() {
		return this.capacidad;
	}
	public List<IFoto> getFotos() {
		return this.fotos;
	}
	public LocalTime getHorarioCheckIn() {
		return this.horarioCheckIn;
	}
	public LocalTime getHorarioCheckOut() {
		return this.horarioCheckOut;
	}
	public Integer getVecesAlquilado() {
		return this.vecesAlquilado;
	}
	public PoliticaCancelacion getPoliticaCancelacion() {
		return this.politicaCancelacion;
	}
	public List<IFormaDePago> getFormasDePagoAceptadas() {
		return this.formasDePagoAceptadas;
	}
	
	public List<PrecioPeriodo> getPrecios() {
		return this.precios;
	}

	public List<Rankeo> getRankeos(){
		return this.gestorDeRankeos.getRankeos();
	}
	
	public double promedioRanking(String categoria) {
		return this.gestorDeRankeos.promedioRanking(categoria);
	}
	
	public double promedioTotalRanking() {
		return this.gestorDeRankeos.promedioTotalRanking();
	}

	public boolean estaOcupado() {
		return this.getPropietario().getSitioInmuebles().estaOcupado(this);
	}

	@Override
	public void visualizarse() {
		// TODO Auto-generated method stub
		
	}

	public String getInformacion() {
		// TODO Auto-generated method stub
		return null;
	}
}