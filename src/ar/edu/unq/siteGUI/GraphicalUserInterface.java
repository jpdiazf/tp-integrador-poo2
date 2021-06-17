package ar.edu.unq.siteGUI;

import java.util.ArrayList;

import ar.edu.unq.sitioInmueble.IInmueble;
//import ar.edu.unq.sitioInmueble.IUsuario;
import ar.edu.unq.sitioInmueble.IVisualizable;

public class GraphicalUserInterface implements IGraphicalUserInterface {

	private ArrayList<IInmueble> inmueblesEnPantalla;
	

	public void mostrarEnPantalla(ArrayList<IInmueble> inmuebles) {
		this.inmueblesEnPantalla = inmuebles;
	}
	
	public ArrayList<IInmueble> getInmueblesEnPantalla() {
		return this.inmueblesEnPantalla;
	}
	
	public void mostrarInformacion(IVisualizable visualizable) {
		//visualizable.mostrarInformacion();
	}
	
}
