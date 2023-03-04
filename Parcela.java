/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

public class Parcela {
    private int numParcela;
    private boolean disponible;
	
    public Parcela() {
		
    }
	
    public Parcela(int numParcela, boolean disponible) {
	super();
	this.numParcela = numParcela;
	this.disponible = disponible;
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
 
    @Override
    public String toString() {
	return "Parcela [numParcela=" + numParcela + ", disponible=" + disponible + "]";
    }
}
