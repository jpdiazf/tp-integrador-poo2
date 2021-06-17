
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Inmueble implements IVisualizable {
	
	private Usuario dueño;
	private String tipo;
	private Double superficie;
	private String pais;
	private String ciudad;
	private String direccion;
	private List<String> servicios;
	private Integer capacidad;
	private List<Foto> fotos;
	private LocalDateTime horarioCheckIn;
	private LocalDateTime horarioCheckOut;
	private Cancelacion politicaCancelacion;
	private List<String> formasDePagoAceptadas; // DEFINIR LA INTERFACE
	private List<PrecioPeriodo> precios;
	private Rankeo rankeo;
	private Integer vecesAlquilado;
	
	public Inmueble(Usuario dueño, String tipo, Double superficie, String pais, String ciudad,
					String direccion, List<String> servicios, Integer capacidad, List<Foto> fotos,
					LocalDateTime horarioCheckIn, LocalDateTime horarioCheckOut, Cancelacion politicaCancelacion,
					List<String> formasDePagoAceptadas, List<PrecioPeriodo> precios) {
		
		this.dueño = dueño;
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
		
	}
	
	public double precioPorPeriodo(LocalDate desde, LocalDate hasta) {
		double total = 0;
		for (LocalDate fecha = desde; fecha.isBefore(hasta); fecha = fecha.plusDays(1)) {
		    total += this.precio(fecha);
		}
		return total;
	}
	
	private double precio(LocalDate fecha) {
		double retorno = 0; 
		for(PrecioPeriodo p : precios) {
			if (p.pertenceAlRango(fecha)) {
				retorno = p.valor();
				break;
			}
		}
		return retorno;
	}
	
	public boolean estaPublicadoPerido(LocalDate desde, LocalDate hasta) {
		for (LocalDate fecha = desde; fecha.isBefore(hasta); fecha = fecha.plusDays(1)) {
		    if(! this.estaPublicado(fecha)) {
		    	return false;
		    }
		}
		return true;
	}
	
	private boolean estaPublicado(LocalDate fecha) {
		for(PrecioPeriodo p : precios) {
			if (p.pertenceAlRango(fecha)) {
				return true;
			}
		}
		return false;
	}

	
	public Usuario dueño() {
		return this.dueño;
	}
	public String tipo() {
		return this.tipo;
	}
	public double superficie() {
		return this.superficie;
	}
	public String pais() {
		return this.pais;
	}
	public String ciudad() {
		return this.ciudad;
	}
	public String direccion() {
		return this.direccion;
	}
	public List<String> servicios() {
		return this.servicios;
	}
	public Integer capacidad() {
		return this.capacidad;
	}
	public List<Foto> fotos() {
		return this.fotos;
	}
	public LocalDateTime horarioCheckIn() {
		return this.horarioCheckIn;
	}
	public LocalDateTime horarioCheckOut() {
		return this.horarioCheckOut;
	}
	public Integer vecesAlquilado() {
		return this.vecesAlquilado;
	}
	public Cancelacion politicaCancelacion() {
		return this.politicaCancelacion;
	}
	public List<String> formasDePagoAceptadas() {
		return this.formasDePagoAceptadas;
	}
	public Rankeo rankeo() {
		return this.rankeo;
	}

	@Override
	public void visualizar() {
		// TODO Auto-generated method stub
		
	}
	
}