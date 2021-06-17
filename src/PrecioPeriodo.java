

import java.time.LocalDate;

public class PrecioPeriodo {
	
	LocalDate comienzo;
	LocalDate fin;
	double valor;
	
	public PrecioPeriodo(LocalDate comienzo, LocalDate fin, double valor) {
		this.comienzo = comienzo;
		this.fin = fin;
		this.valor = valor;
	}
	
	public boolean pertenceAlRango(LocalDate fecha) {
		return 	! fecha.isBefore(comienzo) &&
				! fecha.isAfter(fin);

	}
	
	public LocalDate comienzo() {
		return this.comienzo;
	}

	public LocalDate fin() {
		return this.fin;
	}
	
	public double valor() {
		return this.valor;
	}

}
