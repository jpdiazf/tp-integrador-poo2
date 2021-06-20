package clases;
import java.time.LocalDate;

import interfaces.IFormaDePago;

public class Reserva {

	private Usuario inquilino;
	private Inmueble inmueble;
	private IFormaDePago formaDePago;
	private LocalDate comienzo;
	private LocalDate fin;
	private Double precio;
	private boolean aceptada;
	
	
	public Reserva(Usuario inquilino, Inmueble inmueble, IFormaDePago formaDePago, LocalDate comienzo, LocalDate fin) {
		this.inquilino = inquilino;
		this.inmueble = inmueble;
		this.comienzo = comienzo;
		this.fin = fin;
		this.aceptada = false;
		this.precio = inmueble.getPrecioPorPeriodo(comienzo, fin);
	}
	
    public void visualizar() {
        // TODO Auto-generated method stub
        
    }
    
    public void aprobar() {
    	this.aceptada = true; //y podrá luego notificar al inquilino?
    }
    
    public void rechazar() {
    	this.aceptada = false; //y podrá luego notificar al inquilino?
    }
    
    public void cancelar() {
    	Double montoAPagar = this.getInmueble().getPoliticaCancelacion().montoCancelacion(this, LocalDate.now());
    	this.getFormaDePago().pagar(montoAPagar, this.getInmueble().getPropietario().getSitioInmuebles()); //REVER (PAGAR AL SITIO O AL PROPIETARIO???)
    }

	public LocalDate getComienzo() {
		return this.comienzo;
	}

	public LocalDate getFin() {
		return this.fin;
	}

	public double getPrecio() {
		return precio;
	}

	public Inmueble getInmueble() {
		return this.inmueble;
	}

	public Usuario getInquilino() {
		return this.inquilino;
	}

	public IFormaDePago getFormaDePago() {
		return this.formaDePago;
	}
}
