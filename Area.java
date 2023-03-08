/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.io.File;
import java.util.ArrayList;
import javax.swing.Icon;


public class Area {
    
    private String direccion;
    private int telefono;
    private String mail;
    private String web;
    private int numParcelas;
    private int precioNoche;
    ArrayList<Servicio> Servicios = new ArrayList<>();
    private Icon mapa;
    private File informacion;

    public Area(){
        
    }

    public Area(String direccion, int telefono, String mail, String web, int numParcelas, int precioNoche, Icon mapa, File informacion) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
        this.web = web;
        this.numParcelas = numParcelas;
        this.precioNoche = precioNoche;
        this.mapa = mapa;
        this.informacion = informacion;
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

    public ArrayList<Servicio> getServicios() {
        return Servicios;
    }

    public void setServicios(ArrayList<Servicio> Servicios) {
        this.Servicios = Servicios;
    }

    public Icon getMapa() {
        return mapa;
    }

    public void setMapa(Icon mapa) {
        this.mapa = mapa;
    }

    public File getInformacion() {
        return informacion;
    }

    public void setInformacion(File informacion) {
        this.informacion = informacion;
    }
    
    public String toSQL(){
        String insertArea = "INSERT INTO area (direccion,telefono,mail,web,num_parcelas,precio_noche) VALUES (\""+getDireccion()+"\","+getTelefono()+",\""
        + getMail()+"\",\""+getWeb()+"\","+getNumParcelas()+","+getPrecioNoche()+");";
        return insertArea;
    }

    @Override
    public String toString() {
        return "Area{" + "direccion=" + direccion + ", telefono=" + telefono + ", mail=" + mail + ", web=" + web + ", numParcelas=" + numParcelas + ", precioNoche=" + precioNoche + ", Servicios=" + Servicios + ", mapa=" + mapa + ", informacion=" + informacion + '}';
    }

    
    
    
    
    
}




