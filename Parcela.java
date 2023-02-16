/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectosaracoello;

/**
 *
 * @author sarac
 */
public class Parcela {
    private int numParcela;
	private boolean disponible;
	private boolean conLuz;
	
	public Parcela() {
		
	}
	
	public Parcela(int numParcela, boolean disponible, boolean conLuz) {
		super();
		this.numParcela = numParcela;
		this.disponible = disponible;
		this.conLuz = conLuz;
	}

	public int getNumParcela() {
		return numParcela;
	}
	
	public void setNumParcela(int numParcela) {
		this.numParcela = numParcela;
	}
	
	public boolean isDisponible() {
		return disponible;
	}
	
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	public boolean isConLuz() {
		return conLuz;
	}
	
	public void setConLuz(boolean conLuz) {
		this.conLuz = conLuz;
	}
        
        //Metodo para indicar si una determinada parcela tiene luz
        static boolean parcelasConLuz(int numParcela){
            if (numParcela == 1 || numParcela == 2 || numParcela == 3 || numParcela == 4 || numParcela == 5 || numParcela == 6){
                return true;
            } else {
                return false;
            }        
        }
        
        //Metodo para importar un mapa del area
        
	
	@Override
	public String toString() {
		return "Parcela [numParcela=" + numParcela + ", disponible=" + disponible + ", conLuz=" + conLuz + "]";
	}
}
