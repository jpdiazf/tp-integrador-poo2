package interfaces;

import ar.edu.unq.sitioInmueble.SitioInmuebles;

public interface IFormaDePago {
    public void pagar(Double monto, SitioInmuebles sitio);
}
