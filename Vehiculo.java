/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author sarac
 */
public class Vehiculo {
    private String matricula;
	private String marca;
	private String modelo;
	private boolean luz;
	private int numOcupantes;
	private Date checkIn;
	private Date checkOut;
	private Parcela parcela;
	private Set <Cliente> clientes;
	
	public Vehiculo() {
		
	}
	
	public Vehiculo(String matricula, String marca, String modelo, boolean luz, int numOcupantes, Date check_in,
			Date check_out, Parcela parcela, Set<Cliente> clientes) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.luz = luz;
		this.numOcupantes = numOcupantes;
		this.checkIn = check_in;
		this.checkOut = check_out;
		this.parcela = parcela;
		this.clientes = clientes;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public boolean isLuz() {
		return luz;
	}

	public void setLuz(boolean luz) {
		this.luz = luz;
	}

	public int getNumOcupantes() {
		return numOcupantes;
	}

	public void setNumOcupantes(int numOcupantes) {
		this.numOcupantes = numOcupantes;
	}

	public Date getCheck_in() {
		return checkIn;
	}

	public void setCheck_in(Date check_in) {
		this.checkIn = check_in;
	}

	public Date getCheck_out() {
		return checkOut;
	}

	public void setCheck_out(Date check_out) {
		this.checkOut = check_out;
	}

	public Parcela getParcela() {
		return parcela;
	}

	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}

        public Set<Cliente> getClientes() {
                return clientes;
        }
        
        public void setClientes(Set<Cliente> clientes) {
                this.clientes = clientes;
        }

	@Override
	public String toString() {
		return "Vehiculo [matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", luz=" + luz
				+ ", numOcupantes=" + numOcupantes + ", check_in=" + checkIn + ", check_out=" + checkOut
				+ ", parcela=" + parcela /*+ ", clientes=" + clientes.size() */+ "]";
	}
}
