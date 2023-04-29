/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Area {
    //Variables coincidentes con la BBDD
    private int id;
    private String direccion;
    private int telefono;
    private String mail;
    private String web;
    private int numParcelas;
    private int precioNoche;
    private String pathMapa;
    private File informacion; //convertir a String con el path del fichero
    
    //Variables solo de la clase para ser usadas por el programa
   // private ArrayList<Servicio> Servicios = new ArrayList<>(); //Convertir a Array

    public Area(){
        
    }

    public Area(String direccion, int telefono, String mail, String web, int numParcelas, int precioNoche, String mapa, File informacion) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
        this.web = web;
        this.numParcelas = numParcelas;
        this.precioNoche = precioNoche;
        this.pathMapa = mapa;
        this.informacion = informacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public int getNumParcelas() {
        return numParcelas;
    }

    public void setNumParcelas(int numParcelas) {
        this.numParcelas = numParcelas;
    }

    public int getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(int precioNoche) {
        this.precioNoche = precioNoche;
    }
    
    public String getPathMapa() {
        return pathMapa;
    }

    public void setPathMapa(String mapa) {
        this.pathMapa = mapa;
    }

    public File getInformacion() {
        return informacion;
    }

    public void setInformacion(File informacion) {
        this.informacion = informacion;
    }
    
    public void asignarParcelas(){
        Parcela [] parcela = new Parcela[getNumParcelas()];
        for (int i = 0; i < getNumParcelas(); i++) {
            parcela[i].setNumParcela(i+1);
            parcela[i].setDisponible(true);
        }
        Arrays.toString(parcela);
    }
    
    public String toSQL(){
        String insertArea = "INSERT INTO area (direccion,telefono,mail,web,num_parcelas,precio_noche) VALUES (\""+getDireccion()+"\","+getTelefono()+",\""
        + getMail()+"\",\""+getWeb()+"\","+getNumParcelas()+","+getPrecioNoche()+");";
        return insertArea;
    }

    @Override
    public String toString() {
        return "Area{" + "direccion=" + direccion + ", telefono=" + telefono + ", mail=" + mail + ", web=" + web + ", numParcelas=" + numParcelas + ", precioNoche=" + precioNoche + ", pathMapa=" + pathMapa + ", informacion=" + informacion + '}';
    }

    
}




