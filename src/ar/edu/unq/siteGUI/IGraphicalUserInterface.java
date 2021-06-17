package ar.edu.unq.siteGUI;

import java.util.ArrayList;

import ar.edu.unq.sitioInmueble.IInmueble;
//import ar.edu.unq.sitioInmueble.IUsuario;
import ar.edu.unq.sitioInmueble.IVisualizable;

public interface IGraphicalUserInterface {

	public void mostrarEnPantalla(ArrayList<IInmueble> inmuebles);
	public ArrayList<IInmueble> getInmueblesEnPantalla();
	public void mostrarInformacion(IVisualizable visualizable);
	
}
