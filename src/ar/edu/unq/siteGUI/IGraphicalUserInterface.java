package ar.edu.unq.siteGUI;

import java.util.ArrayList;

import clases.Inmueble;
import interfaces.IVisualizable;


public interface IGraphicalUserInterface {

	public void mostrarEnPantalla(ArrayList<Inmueble> inmuebles);
	public ArrayList<Inmueble> getInmueblesEnPantalla();
	public void mostrarInformacion(IVisualizable visualizable);
	
}
