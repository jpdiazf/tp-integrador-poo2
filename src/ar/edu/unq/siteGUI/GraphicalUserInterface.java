package ar.edu.unq.siteGUI;

import java.util.ArrayList;

import clases.Inmueble;
import interfaces.IVisualizable;


public class GraphicalUserInterface implements IGraphicalUserInterface {

	private ArrayList<Inmueble> visualizablesEnPantalla;
	

	public void mostrarEnPantalla(ArrayList<Inmueble> inmuebles) {
		this.visualizablesEnPantalla = inmuebles;
	}
	
	public ArrayList<Inmueble> getInmueblesEnPantalla() {
		return this.visualizablesEnPantalla;
	}
	
	public void mostrarInformacion(IVisualizable visualizable) {
		//visualizable.mostrarInformacion();
	}
	
}
