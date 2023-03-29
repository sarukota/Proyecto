/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clasesJava;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import pantallasSwing.*;

public class Interfaz extends javax.swing.JFrame {
    //Instancio las diferentes pantallas
    pantallasSwing.PnlAlertas pA = new PnlAlertas();
    pantallasSwing.PnlRegistro pR = new PnlRegistro();
    pantallasSwing.PnlGestion pG = new PnlGestion();
    pantallasSwing.PnlPlano pP;
    pantallasSwing.PnlFactura pF;
    
    Area miArea;
    DefaultTableModel dtmServicios = new DefaultTableModel(); //instancio un modelo de tabla
    ArrayList<Servicio> listaServicios = new ArrayList<>(); //Creo un array para añadir servicios
    Object servicioEscogido; //Creo un objeto para determinar el srv escogido
    ConexionBBDD conexion = new ConexionBBDD();
    Color camposNull = new Color(255,0,51);
    Color gris = new Color(214,219,223);
    
    public Interfaz() {
        initComponents();
        setSize(1300,700); //Da el tamaño a la ventana
        setLocationRelativeTo(null);//Centrar el JFrame
        setResizable(false);
        lblMenu.setSize(300,700);
        pantallas.setSize(1000,700);
        ImageIcon fondo = new ImageIcon("src/main/java/imagenes/wallpaperVan2.jpg");
        lblMenu.setIcon(fondo);
        try {
            ConfigInicial();
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
 
                   
    }
    
    //Método que establece la configuración inicial al acceder al programa
    public void ConfigInicial() throws SQLException{
        String[] nulo = new String[1];
        nulo[0]= "[null]";      
        
            //Si no existen datos en tabla Area de la BD: me lleva a la pantalla infoArea para obligarme a rellenar los datos
            if (Arrays.toString(conexion.selectFromTabla("SELECT* FROM Area", 1)).equals(nulo[0])){
                pantallas.setSelectedIndex(0);
                //Botonera de navegación invisible para que obligue a rellenar los datos primero
                pnlBtnRegistro.setVisible(false);
                pnlBtnGestion.setVisible(false);
                pnlBtnAlertas.setVisible(false);
                pnlBtnMapa.setVisible(false);
                pnlBtnInfo.setVisible(false);
                btnEditarDatos.setVisible(false);
                //Configuro el textArea
                Font fuente = new Font("Rockwell Nova", Font.BOLD, 18);
                Color colorFondoNotas = new Color(0,153,204);
                jTANotas.setEditable(false);
                jTANotas.setBackground(colorFondoNotas);
                jTANotas.setText("\n            ¡Bienvenid@ a FurgoGestion!\n\n            Para empreza vamos a realizar algunas configuraciones.\n\n            Por favor rellene los datos del área.");
                jTANotas.setFont(fuente);
                //jTANotas.setText("\nYa puede comenzara a utilizar la aplicación.");
                //jTANotas.setFont(fuente);
            }else{   
            //Si no existen datos en tabla Area de la BD: me lleva a la pantalla alertas
            
                pantallas.setSelectedIndex(1);
                pnlPantallas.removeAll();
                pnlPantallas.add(pA,BorderLayout.CENTER);
                pnlPantallas.revalidate();
                pnlPantallas.repaint();
            }
           
    }
    
    //Método que establece la configuración inicial del panel InfoArea
    public void configInfoArea(){
        setMdlTblServicios();
        tblServicios.setRowHeight(20);
        dtmServicios.setRowCount(0);
        String selectServicio = "SELECT* FROM servicios;";
        String selectArea = "SELECT* FROM area;";
        JTextField[]tf = {tfDireccion,tfTelefonoArea,tfEmailArea,tfPagWeb,tfNParcelas,tfprecioNoche};  
        try {
            conexion.selectFromTabla(selectServicio, dtmServicios);
            conexion.selectFromTabla(selectArea, tf);
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           btnEditarDatos.setText("Cancelar");
           cambiarFuncionBtn();
           tfOtroServicio.setEnabled(false);
        }
    }
    
    //se instancia una nueva area con los datos de los campos
    public Area crearArea(){
        Area area = new Area();
        area.setDireccion(tfDireccion.getText());
        area.setTelefono((Integer.parseInt(tfTelefonoArea.getText())));
        area.setMail(tfEmailArea.getText());
        area.setWeb(tfPagWeb.getText());
        area.setNumParcelas((Integer.parseInt(tfNParcelas.getText())));
        area.setPrecioNoche((Integer.parseInt(tfprecioNoche.getText())));
        return area;
    }
    
    //Cambia la funcionalidad entre boton "Editar" y "Cancelar"
    public void cambiarFuncionBtn(){
        if ("Editar".equals(btnEditarDatos.getText())){
            //si presiono sobre Editar: cambio texto botón a cancelar, los campos a editables y blancos, aparece boton guardar
            btnEditarDatos.setText("Cancelar");
            editarCamposArea(true,Color.white);
            btnGuardarArea.setVisible(true);
        }else{
            //Si presiono sobre Cancelar: cambio texto botón a editar, los campos a no editables y a color gris, escondo boton guardar
            btnEditarDatos.setText("Editar");
            editarCamposArea(false,gris);
            btnGuardarArea.setVisible(false);
        } 
    }
    
    //Método para habilitar/deshabilitar y los textField de datosArea y cambiar su color.
    public void editarCamposArea(boolean bool, Color color){
        //Edicion textFields
        tfDireccion.setEditable(bool);
        tfTelefonoArea.setEditable(bool);
        tfEmailArea.setEditable(bool);
        tfPagWeb.setEditable(bool);
        tfNParcelas.setEditable(bool);
        tfprecioNoche.setEditable(bool);
        //Color textFields
        tfDireccion.setBackground(color);
        tfTelefonoArea.setBackground(color);
        tfEmailArea.setBackground(color);
        tfPagWeb.setBackground(color);
        tfNParcelas.setBackground(color);
        tfprecioNoche.setBackground(color);
    }
  
    //metodo para definir el modelo de la tabla Servicios
    private void setMdlTblServicios(){
        String [] cabecera = {"Servicio","Precio" };
        dtmServicios.setColumnIdentifiers(cabecera);
        tblServicios.setModel(dtmServicios);
    }
    
    //método para rellenar el arrayList de servicios para luego insertarlos en la tabla.
    private void llenarArrayServicios(){
        String nombre;
        int precio = Integer.parseInt(tfPrecio.getText());
        if (servicioEscogido == "Otro servicio"){ //Si escojo la opción "otro servicio" tendré que rellenar un nombre escogido en el textField de debajo y este se añadirá al array
            nombre = tfOtroServicio.getText();
        }else{ 
            nombre = cbServicios.getSelectedItem().toString();
        }
        Servicio srv = new Servicio(nombre,precio);
        listaServicios.add(srv);
    }
    
    //metodo para mostrar los datos de los servicios en la tabla de la interfaz
    private void llenarTblServicios(){
        Object [] datos = new Object[dtmServicios.getColumnCount()];
        dtmServicios.setRowCount(0);
        for (Servicio srv : listaServicios) {
            datos[0] = srv.getNombre();
            datos[1] = srv.getPrecio();
            dtmServicios.addRow(datos);
        }
        tblServicios.setModel(dtmServicios);
    }
    
    public void eliminarServicios(){
        Object[] opciones = { "ACEPTAR", "CANCELAR" };
        int opcion = JOptionPane.showOptionDialog(null, "Se eliminarán los datos", "Confirmación",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
        System.out.println("opciones "+opciones[opcion] );
        if (opcion == 0){
            System.out.println("has seleccionado aceptar");
            dtmServicios.setRowCount(0);
            listaServicios.clear();
            try {
                conexion.UpdateBd("DELETE FROM servicios;");
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Método que añade los diferentes Jpanel con las pantallas a este Jframe principal
    private void llamarPnl(JPanel pnl){
        pantallas.setSelectedIndex(1);
        pnlPantallas.removeAll();
        pnlPantallas.add(pnl,BorderLayout.CENTER);
        pnlPantallas.revalidate();
        pnlPantallas.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBtnFactura = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pnlBtnRegistro = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnlBtnGestion = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnlBtnMapa = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pnlBtnAlertas = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlBtnInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMenu = new javax.swing.JLabel();
        pantallas = new javax.swing.JTabbedPane();
        pnlDatos = new javax.swing.JPanel();
        pnlDatosArea = new javax.swing.JPanel();
        tfEmailArea = new javax.swing.JTextField();
        tfNParcelas = new javax.swing.JTextField();
        tfPagWeb = new javax.swing.JTextField();
        lblPrecioNoche = new javax.swing.JLabel();
        tfprecioNoche = new javax.swing.JTextField();
        lblRellena = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblTelefono1 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblNPArcelas = new javax.swing.JLabel();
        lblPagWeb = new javax.swing.JLabel();
        tfDireccion = new javax.swing.JTextField();
        tfTelefonoArea = new javax.swing.JTextField();
        btnGuardarArea = new javax.swing.JButton();
        btnEditarDatos = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        lbl€ = new javax.swing.JLabel();
        btnAnadir = new javax.swing.JButton();
        tfOtroServicio = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblServicios = new javax.swing.JTable();
        lblRellena1 = new javax.swing.JLabel();
        cbServicios = new javax.swing.JComboBox<>();
        tfPrecio = new javax.swing.JTextField();
        btnGuardarServicios = new javax.swing.JButton();
        btnEliminarServ = new javax.swing.JButton();
        spNotas = new javax.swing.JScrollPane();
        jTANotas = new javax.swing.JTextArea();
        pnlPantallas = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlBtnFactura.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnFacturaMouseClicked(evt);
            }
        });
        pnlBtnFactura.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("GENERAR FACTURA");
        pnlBtnFactura.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 180, 50));

        getContentPane().add(pnlBtnFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 340, 50));

        pnlBtnRegistro.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnRegistroMouseClicked(evt);
            }
        });
        pnlBtnRegistro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("REGISTRO");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnRegistro.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 90, 50));

        getContentPane().add(pnlBtnRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 340, 50));

        pnlBtnGestion.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnGestion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnGestion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnGestionMouseClicked(evt);
            }
        });
        pnlBtnGestion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("GESTIÓN");
        pnlBtnGestion.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 90, 50));

        getContentPane().add(pnlBtnGestion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 340, 50));

        pnlBtnMapa.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnMapa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnMapa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnMapaMouseClicked(evt);
            }
        });
        pnlBtnMapa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("MAPA");
        pnlBtnMapa.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 90, 50));

        getContentPane().add(pnlBtnMapa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 340, 50));

        pnlBtnAlertas.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnAlertas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnAlertas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnAlertasMouseClicked(evt);
            }
        });
        pnlBtnAlertas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ALERTAS");
        pnlBtnAlertas.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 90, 50));

        getContentPane().add(pnlBtnAlertas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 340, 50));

        pnlBtnInfo.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnInfoMouseClicked(evt);
            }
        });
        pnlBtnInfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INFORMACIÓN");
        pnlBtnInfo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 140, 50));

        getContentPane().add(pnlBtnInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 340, 50));
        getContentPane().add(lblMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 700));

        pnlDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlDatosArea.setBackground(new java.awt.Color(0, 153, 204));
        pnlDatosArea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnlDatosArea.add(tfEmailArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 230, -1));
        pnlDatosArea.add(tfNParcelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 230, -1));
        pnlDatosArea.add(tfPagWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 230, -1));

        lblPrecioNoche.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblPrecioNoche.setText("€/noche:");
        pnlDatosArea.add(lblPrecioNoche, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));
        pnlDatosArea.add(tfprecioNoche, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 230, -1));

        lblRellena.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblRellena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRellena.setText("Datos del área:");
        pnlDatosArea.add(lblRellena, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 450, -1));

        lblDireccion.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblDireccion.setText("Dirección:");
        pnlDatosArea.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        lblTelefono1.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblTelefono1.setText("Teléfono:");
        pnlDatosArea.add(lblTelefono1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        lblEmail.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblEmail.setText("E-mail:");
        pnlDatosArea.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        lblNPArcelas.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblNPArcelas.setText("Nº parcelas:");
        pnlDatosArea.add(lblNPArcelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

        lblPagWeb.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblPagWeb.setText("Página web:");
        pnlDatosArea.add(lblPagWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));
        pnlDatosArea.add(tfDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 230, -1));
        pnlDatosArea.add(tfTelefonoArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 230, -1));

        btnGuardarArea.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardarArea.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnGuardarArea.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarArea.setText("Guardar");
        btnGuardarArea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarAreaActionPerformed(evt);
            }
        });
        pnlDatosArea.add(btnGuardarArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 330, 100, 30));

        btnEditarDatos.setBackground(new java.awt.Color(0, 0, 0));
        btnEditarDatos.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnEditarDatos.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarDatos.setText("Editar");
        btnEditarDatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarDatosActionPerformed(evt);
            }
        });
        pnlDatosArea.add(btnEditarDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 100, 30));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnlDatosArea.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, -7, 20, 400));

        pnlDatos.add(pnlDatosArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 390));

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl€.setText("€");
        jPanel1.add(lbl€, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 20, 20));

        btnAnadir.setBackground(new java.awt.Color(0, 0, 0));
        btnAnadir.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnAnadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAnadir.setText("Añadir servicio");
        btnAnadir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 130, 30));
        jPanel1.add(tfOtroServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 140, 30));

        tblServicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblServicios);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 430, 150));

        lblRellena1.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblRellena1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRellena1.setText("Servicios del área con precio extra:");
        jPanel1.add(lblRellena1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 30, 500, -1));

        cbServicios.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        cbServicios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pasajero extra", "Electricidad", "Llenado/vaciado", "mascota", "Lavadora", "Secadora", "Ficha", "Otro servicio" }));
        cbServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbServiciosActionPerformed(evt);
            }
        });
        jPanel1.add(cbServicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));

        tfPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPrecioActionPerformed(evt);
            }
        });
        jPanel1.add(tfPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 60, -1));

        btnGuardarServicios.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardarServicios.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnGuardarServicios.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarServicios.setText("Guardar");
        btnGuardarServicios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarServiciosActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardarServicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 330, 100, 30));

        btnEliminarServ.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminarServ.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnEliminarServ.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarServ.setText("Eliminar servicios");
        btnEliminarServ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarServActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminarServ, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 160, 30));

        pnlDatos.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 500, 390));

        jTANotas.setColumns(20);
        jTANotas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTANotas.setRows(5);
        spNotas.setViewportView(jTANotas);

        pnlDatos.add(spNotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 950, 280));

        pantallas.addTab("InfoArea", pnlDatos);

        javax.swing.GroupLayout pnlPantallasLayout = new javax.swing.GroupLayout(pnlPantallas);
        pnlPantallas.setLayout(pnlPantallasLayout);
        pnlPantallasLayout.setHorizontalGroup(
            pnlPantallasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        pnlPantallasLayout.setVerticalGroup(
            pnlPantallasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 747, Short.MAX_VALUE)
        );

        pantallas.addTab("Pantallas", pnlPantallas);

        getContentPane().add(pantallas, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 0, 1110, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlBtnRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRegistroMouseClicked
        llamarPnl(pR);
    }//GEN-LAST:event_pnlBtnRegistroMouseClicked

    private void pnlBtnGestionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnGestionMouseClicked
        llamarPnl(pG);
    }//GEN-LAST:event_pnlBtnGestionMouseClicked

    private void pnlBtnMapaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnMapaMouseClicked
        pP = new PnlPlano();
        llamarPnl(pP);
    }//GEN-LAST:event_pnlBtnMapaMouseClicked

    private void pnlBtnAlertasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAlertasMouseClicked
        pA = new PnlAlertas();
        llamarPnl(pA);  
    }//GEN-LAST:event_pnlBtnAlertasMouseClicked

    private void pnlBtnInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnInfoMouseClicked
        pantallas.setSelectedIndex(0);
        configInfoArea();
    }//GEN-LAST:event_pnlBtnInfoMouseClicked

    private void pnlBtnFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnFacturaMouseClicked
        pF = new PnlFactura();
        llamarPnl(pF); 
    }//GEN-LAST:event_pnlBtnFacturaMouseClicked

    private void btnEliminarServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarServActionPerformed
        eliminarServicios();
    }//GEN-LAST:event_btnEliminarServActionPerformed

    private void btnGuardarServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarServiciosActionPerformed
        int contador1 = 0;
        for (Servicio srv : listaServicios) {
            String insert = "INSERT INTO servicios (nombre,precio) VALUES (\""+srv.getNombre()+"\","+srv.getPrecio()+");";
            try {
                conexion.UpdateBd(insert);
                while(contador1==0){
                    contador1++;
                    JOptionPane.showMessageDialog(null,"Se han actualizado los datos con éxito");
                }

            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null,"Ha ocurrido un error al insertar los datos");
            }

        }
        miArea.setServicios(listaServicios);
    }//GEN-LAST:event_btnGuardarServiciosActionPerformed

    private void tfPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPrecioActionPerformed

    private void cbServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbServiciosActionPerformed
        // TODO add your handling code here:
        servicioEscogido = cbServicios.getSelectedItem(); //guardo el nombre que está seleccionado cuando presiono btnAnadir
        if (servicioEscogido == "Otro servicio"){
            tfOtroServicio.setEnabled(true);
        }else{
            tfOtroServicio.setEnabled(false);
        }
    }//GEN-LAST:event_cbServiciosActionPerformed

    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        llenarArrayServicios();
        llenarTblServicios();
    }//GEN-LAST:event_btnAnadirActionPerformed

    private void btnEditarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarDatosActionPerformed
        cambiarFuncionBtn();
    }//GEN-LAST:event_btnEditarDatosActionPerformed

    private void btnGuardarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarAreaActionPerformed
        /* Al pulsar boton se establece la conexión con la BD y se añaden a la tabla Area los datos del cuestionario
        Ademas se pasa a la siguiente pantalla*/
        //Añadir funciones: si la tabla Area está vacia hacer insert, si no esta vacia hacer update de área
        if(tfDireccion.getText().length()==0|tfNParcelas.getText().length()==0|tfprecioNoche.getText().length()==0){
            tfDireccion.setBackground(camposNull);
            tfNParcelas.setBackground(camposNull);
            tfprecioNoche.setBackground(camposNull);
            JOptionPane.showMessageDialog(null,"Por favor rellene los campos obligatorios");
        }else{
            pnlBtnRegistro.setVisible(true);
            pnlBtnGestion.setVisible(true);
            pnlBtnAlertas.setVisible(true);
            pnlBtnMapa.setVisible(true);
            pnlBtnInfo.setVisible(true);
            btnEditarDatos.setVisible(true);

            tfDireccion.setBackground(Color.white);
            tfNParcelas.setBackground(Color.white);
            tfprecioNoche.setBackground(Color.white);
            miArea = crearArea();
            try {
                //conexion.UpdateBd("DELETE FROM area;");
                conexion.UpdateBd(miArea.toSQL());
                for (int i = 0; i < miArea.getNumParcelas(); i++) {
                    Parcela parcela = new Parcela (i+1,true);
                    conexion.UpdateBd(parcela.toSQL());
                    jTANotas.setEditable(true);
                    jTANotas.setBackground(Color.white);
                    jTANotas.setText("ESCRIBE AQUI TUS NOTAS:");
                    Font fuente = new Font("Rockwell Nova",1,14);
                    jTANotas.setFont(fuente);

                }
                JOptionPane.showMessageDialog(null,"Se han actualizado los datos con éxito");
                editarCamposArea(false,gris);
                btnGuardarArea.setVisible(false);
                btnEditarDatos.setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnGuardarAreaActionPerformed

    
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        //Se crea la BD con las tablas correspondientes
        ConexionBBDD connect = new ConexionBBDD();
        try {
            connect.crearTablas();
            System.out.println("Se ha conectado correctamente a la BD");  
        } catch (SQLException ex) {
            System.err.println("No se ha podido conectar al servidor SQL");
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Corre la interfaz
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadir;
    private javax.swing.JButton btnEditarDatos;
    private javax.swing.JButton btnEliminarServ;
    private javax.swing.JButton btnGuardarArea;
    private javax.swing.JButton btnGuardarServicios;
    private javax.swing.JComboBox<String> cbServicios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTANotas;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblNPArcelas;
    private javax.swing.JLabel lblPagWeb;
    private javax.swing.JLabel lblPrecioNoche;
    private javax.swing.JLabel lblRellena;
    private javax.swing.JLabel lblRellena1;
    private javax.swing.JLabel lblTelefono1;
    private javax.swing.JLabel lbl€;
    private javax.swing.JTabbedPane pantallas;
    private javax.swing.JPanel pnlBtnAlertas;
    private javax.swing.JPanel pnlBtnFactura;
    private javax.swing.JPanel pnlBtnGestion;
    private javax.swing.JPanel pnlBtnInfo;
    private javax.swing.JPanel pnlBtnMapa;
    private javax.swing.JPanel pnlBtnRegistro;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlDatosArea;
    private javax.swing.JPanel pnlPantallas;
    private javax.swing.JScrollPane spNotas;
    private javax.swing.JTable tblServicios;
    private javax.swing.JTextField tfDireccion;
    private javax.swing.JTextField tfEmailArea;
    private javax.swing.JTextField tfNParcelas;
    private javax.swing.JTextField tfOtroServicio;
    private javax.swing.JTextField tfPagWeb;
    private javax.swing.JTextField tfPrecio;
    private javax.swing.JTextField tfTelefonoArea;
    private javax.swing.JTextField tfprecioNoche;
    // End of variables declaration//GEN-END:variables
}
