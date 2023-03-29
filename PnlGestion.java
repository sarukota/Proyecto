/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pantallasSwing;

import clasesJava.ConexionBBDD;
import clasesJava.Interfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class PnlGestion extends javax.swing.JPanel {
    
    ConexionBBDD conexion = new ConexionBBDD();
    Object buscarPor;
    Calendar calendario = Calendar.getInstance();
    Date fechaHoy = calendario.getTime();

    public PnlGestion() {
        initComponents();
        setSize(1300,700); //Da el tamaño a la ventana
        setLocation(0,0);
        configInicial();
    }
    //Da un valor inicial al comboBox filtro y crea un ActionListener para el comboBox Seleccion
    private void configInicial(){
        cbFiltro.removeAllItems();
        cbFiltro.addItem("Antiguos");
        cbFiltro.addItem("En area");
        cbFiltro.addItem("Todos");
        cbSeleccion.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0){
                llenarCbFiltro();
            }
        });
    }
    //metodo que rellena el ComboBox Filtro según los valores del ComboBox Seleccion
    public void llenarCbFiltro(){
        if (cbSeleccion.getSelectedItem() == "Por vehiculo" | cbSeleccion.getSelectedItem() == "Por cliente") {
            cbFiltro.removeAllItems();
            cbFiltro.addItem("Antiguos");
            cbFiltro.addItem("En area");
            cbFiltro.addItem("Todos");  
        }
        else if (cbSeleccion.getSelectedItem() == "Por parcela"){
            cbFiltro.removeAllItems();
            cbFiltro.addItem("Ocupadas");
            cbFiltro.addItem("Disponibles");
            cbFiltro.addItem("Todas");
            
        }
    }
    
    //Coloca las cabeceras pasadas por parámetro en la tabla
    public DefaultTableModel SetCabeceras (String[] cabecera){
        DefaultTableModel dtmResult = new DefaultTableModel();
        dtmResult.setColumnIdentifiers(cabecera);
        tblResult.setModel(dtmResult);
        dtmResult.setRowCount(0);
        return dtmResult;
    }
    //metodo para definir el modelo y mostrar los datos de la BD en la tabla del panel de Gestion
    private void llenarTablaBuscar() throws SQLException{
        buscarPor = cbSeleccion.getSelectedItem(); //guardo el tipo de busqueda que está seleccionada en el comboBox
        tblResult.setRowHeight(25);
        DefaultTableModel dtmResult = new DefaultTableModel();
        if (buscarPor == "Por vehiculo"){
            String [] cabecera = {"Matricula","Marca","Modelo","NºOcupantes","NºParcela","Check in","Check out",};
            dtmResult = SetCabeceras(cabecera);
            try {
                if (cbFiltro.getSelectedItem() == "Todos") conexion.selectFromTabla("SELECT matricula, marca, modelo, num_ocupantes, num_parcela, check_in, check_out FROM vehiculos;",dtmResult);
                else if (cbFiltro.getSelectedItem() == "En area") conexion.selectFromTabla("SELECT matricula, marca, modelo, num_ocupantes, num_parcela, check_in, check_out FROM vehiculos WHERE check_out >= '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
                else if (cbFiltro.getSelectedItem() == "Antiguos") conexion.selectFromTabla("SELECT matricula, marca, modelo, num_ocupantes, num_parcela, check_in, check_out FROM vehiculos WHERE check_out < '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else if (buscarPor == "Por cliente"){
            String [] cabecera = {"DNI","Nombre","Apellido 1","Apellido 2","F.nacimiento","Nacionalidad","Telefono","Mail","Matricula auto",};
            dtmResult = SetCabeceras(cabecera);
            String consultaJoin = "SELECT clientes.dni, clientes.nombre, clientes.apellido1, clientes.apellido2,clientes.fecha_nac,clientes.nacionalidad,clientes.telefono,clientes.mail,clientes.matricula FROM clientes INNER JOIN vehiculos ON clientes.matricula = vehiculos.matricula WHERE vehiculos.check_out";
            try {
                if(cbFiltro.getSelectedItem() == "Todos")conexion.selectFromTabla("SELECT dni, nombre, apellido1, apellido2, fecha_nac, nacionalidad, telefono, mail, matricula FROM clientes;",dtmResult);
                else if (cbFiltro.getSelectedItem() == "En area")conexion.selectFromTabla(consultaJoin+" >= '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
                else if (cbFiltro.getSelectedItem() == "Antiguos")conexion.selectFromTabla(consultaJoin+" < '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            String [] cabecera = {"Nº parcela","Disponible",};
            dtmResult = SetCabeceras(cabecera);
            try {
                if (cbFiltro.getSelectedItem() == "Todas") conexion.selectFromTabla("SELECT num_parcela, disponibilidad FROM parcelas;",dtmResult);//devuelve todas las parcelas
                else if (cbFiltro.getSelectedItem() == "Ocupadas") conexion.selectFromTabla("SELECT num_parcela, disponibilidad FROM parcelas where disponibilidad = false;",dtmResult);
                else conexion.selectFromTabla("SELECT num_parcela, disponibilidad FROM parcelas where disponibilidad = true;",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();
        cbSeleccion = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        cbFiltro = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(0, 153, 204));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblResult);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 890, 480));

        cbSeleccion.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        cbSeleccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por vehiculo", "Por cliente", "Por parcela" }));
        cbSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSeleccionActionPerformed(evt);
            }
        });
        add(cbSeleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 120, 30));

        btnBuscar.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, -1, 30));

        add(cbFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 160, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void cbSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSeleccionActionPerformed
        buscarPor = cbSeleccion.getSelectedItem(); //guardo el nombre que está seleccionado cuando presiono btnAnadir

    }//GEN-LAST:event_cbSeleccionActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            llenarTablaBuscar();
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox<String> cbFiltro;
    private javax.swing.JComboBox<String> cbSeleccion;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblResult;
    // End of variables declaration//GEN-END:variables
}
