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
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import pantallasSwing.*;

/*Contien la interfaz principal, el método Main y el panelDatosArea, donde se ingresan los datos del area y 
los servicios que contiene*/

public class Interfaz extends javax.swing.JFrame {
    //Instancio las diferentes pantallas
    pantallasSwing.PnlAlertas pA = new PnlAlertas();
    pantallasSwing.PnlRegistro pR = new PnlRegistro();
    pantallasSwing.PnlGestion pG = new PnlGestion();
    pantallasSwing.PnlPlano pP;
    pantallasSwing.PnlFactura pF;
    
    private Area miArea;
    private DefaultTableModel dtmServicios = new DefaultTableModel(); //instancio un modelo de tabla
    private ArrayList<Servicio> listaServicios = new ArrayList<>(); //Creo un array para añadir servicios
    private Object servicioEscogido; //Creo un objeto para determinar el srv escogido
    private ConexionBBDD conexion = new ConexionBBDD();
    private Color gris = new Color(214,219,223);
    private Calendar calendario = Calendar.getInstance();
    private java.util.Date fechaHoy = calendario.getTime();
    
    public Interfaz() {
        initComponents();
        setSize(1300,700); //Da el tamaño a la ventana
        setLocationRelativeTo(null);//Centrar el JFrame
        setResizable(false);
        lblMenu.setSize(300,700);
        pantallas.setSize(1000,700);
        ImageIcon fondo = new ImageIcon("src/main/java/imagenes/wallpaperVan2.jpg");
        lblMenu.setIcon(fondo);
        pnlCabecera.setSize(1300,40);
        
        // Botones 
        pnlBtnRegistro.setOpaque(false);
        pnlBtnGestion.setOpaque(false);
        pnlBtnMapa.setOpaque(false);
        pnlBtnAlertas.setOpaque(false);
        pnlBtnInfo.setOpaque(false);
        pnlBtnFactura.setOpaque(false);
        
        try {
            Interfaz.this.ConfigInicial();
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
              
    }
 
    //Método que establece la configuración inicial al acceder al programa según si la BD ya contiene datos o no.
    public void ConfigInicial() throws SQLException{    
        //Si no existen datos en tabla Area de la BD: me lleva a la pantalla infoArea para obligarme a rellenar los datos
        if (conexion.tieneDatos("SELECT* FROM Area") == false){
            primerAcceso();
            tfOtroServicio.setEnabled(false);
            setMdlTblServicios();
        }else{   
        /*Si existen datos en tabla Area de la BD: se liberan las parcelas de los vehiculos que salen el día de hoy 
          si hay datos en la tabla vehiculos y me lleva a la pantalla alertas*/
            if (conexion.tieneDatos("SELECT* FROM vehiculos")){
                conexion.updateBd("UPDATE Parcelas SET disponibilidad = true WHERE num_parcela IN(SELECT num_parcela FROM Vehiculos WHERE check_out < '"+conexion.fechaSQL(fechaHoy)+"');");
                if (conexion.tieneDatos("SELECT* FROM alertas")){
                conexion.updateBd("DELETE FROM alertas WHERE dia_alerta < '"+ conexion.fechaSQL(fechaHoy)+"';");
                }
            }
            llamarPnl(new PnlAlertas());
        }
    }
    
    //Método que configura el primer acceso a la aplicación. Se llamará cuando no haya datos en la tabla Area
    public void primerAcceso(){
        pantallas.setSelectedIndex(0);
        //Botonera de navegación invisible para que obligue a rellenar los datos primero
        pnlBtnRegistro.setVisible(false);
        pnlBtnGestion.setVisible(false);
        pnlBtnAlertas.setVisible(false);
        pnlBtnMapa.setVisible(false);
        pnlBtnInfo.setVisible(false);
        pnlBtnFactura.setVisible(false);
        btnEditarDatos.setVisible(false);
        
        //Configuro el textArea
        Font fuente = new Font("Rockwell Nova", Font.BOLD, 18);
        Color colorFondoNotas = new Color(0,153,204);
        jTANotas.setEditable(false);
        jTANotas.setBackground(colorFondoNotas);
        jTANotas.setText("\n            ¡Bienvenid@ a FurgoGestion!\n\n            Para empreza vamos a realizar algunas configuraciones.\n\n            Por favor rellene los datos del área.");
        jTANotas.setFont(fuente);
    }
    
    //Método que establece la configuración inicial del panel InfoArea
    public void configInfoArea(){
        setMdlTblServicios();
        tblServicios.setRowHeight(20);
        dtmServicios.setRowCount(0);
        String selectServicio = "SELECT* FROM servicios;";
        String selectArea = "SELECT direccion,telefono,mail,web,num_parcelas,precio_noche FROM area;";
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
    
    public boolean comprobacionDatos() {
        
        boolean datosCorrectos = true;
        Pattern patternInteger = Pattern.compile("\\d+");
        
        if (tfDireccion.getText().isBlank()) {
            tfDireccion.setBackground(Color.red);
            datosCorrectos = false;
        }

        String telefono = tfTelefonoArea.getText();
        if (telefono.isBlank()) { // Comprobar el telefono vacío
            tfTelefonoArea.setBackground(Color.red);
            datosCorrectos = false;
        }
        else {
            datosCorrectos = patternInteger.matcher(telefono).matches(); // Comprobar que el telefono es un int
            
            if (!datosCorrectos) {
                tfTelefonoArea.setBackground(Color.red);
            }
        }
        
        String parcelas = tfNParcelas.getText();
        if (parcelas.isBlank()) { // Comprobar si la parcela está vacía
            tfNParcelas.setBackground(Color.red);
            datosCorrectos = false;
        }
        else {
             // Comprobar si la parcela es un int
            if (!patternInteger.matcher(parcelas).matches()) {
                tfNParcelas.setBackground(Color.red);
                datosCorrectos = false;
            }
        }
        
        String precio = tfprecioNoche.getText();
        
        // Comprobar si el precio está vacío
        if (precio.isBlank()) {
            tfprecioNoche.setBackground(Color.red);
            datosCorrectos = false;
        }
        else {
            //Comprobar si el precio es un int
            
            if (!patternInteger.matcher(precio).matches()) {
                tfprecioNoche.setBackground(Color.red);
                datosCorrectos = false;
            }
        }
        
        
        return datosCorrectos;
    }
    
    //se instancia una nueva area con los datos de los campos
    public Area crearArea(){
        Area area = new Area();
        
        if (comprobacionDatos()) { //Si los datos son correctos
            area.setDireccion(tfDireccion.getText());
            area.setTelefono((Integer.parseInt(tfTelefonoArea.getText())));
            area.setMail(tfEmailArea.getText());
            area.setWeb(tfPagWeb.getText());
            area.setNumParcelas((Integer.parseInt(tfNParcelas.getText())));
            area.setPrecioNoche((Integer.parseInt(tfprecioNoche.getText())));
        } else {
            return null;
        }
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
    
    //método que guarda los servicios del arrayList listaServicios en la BD
    public void guardarServicios(){
    int contador = 0;
        for (Servicio srv : listaServicios) {
            String insert = "INSERT INTO servicios (nombre,precio) VALUES (\""+srv.getNombre()+"\","+srv.getPrecio()+");";
            try {
                conexion.updateBd(insert);
                while(contador==0){
                    contador++;
                    JOptionPane.showMessageDialog(this,"Se han actualizado los datos con éxito");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this,"Los servicios no se han podido ayudar.");
            }
        }
    }
    
    //metodo para mostrar los datos de los servicios en la Jtable de la interfaz
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
    
    //Metodo para eliminar los servicios de la tabla y de la BD
    public void eliminarServicios(){
        Object[] opciones = { "ACEPTAR", "CANCELAR" };
        int opcion = JOptionPane.showOptionDialog(null, "Se eliminarán los datos", "Confirmación",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
        System.out.println("opciones "+opciones[opcion] );
        if (opcion == 0){
            System.out.println("has seleccionado aceptar");
            try {
                conexion.updateBd("DELETE FROM servicios;");
                dtmServicios.setRowCount(0);
                listaServicios.clear();
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Método que añade los diferentes Jpanel con las pantallas a este Jframe principal
    public void llamarPnl(JPanel pnl){
        pantallas.setSelectedIndex(1);
        pnlPantallas.removeAll();
        pnl.setSize(1300,700); //Da el tamaño a la ventana
        pnl.setLocation(0,0);
        pnlPantallas.add(pnl,BorderLayout.CENTER);
        pnlPantallas.revalidate();
        pnlPantallas.repaint();
    }
    
    //Metodo para activar el bloc de notas y darle las características
    public void activarBlocNotas(){
        jTANotas.setEditable(true);
        jTANotas.setBackground(Color.white);
        jTANotas.setText("ESCRIBE AQUI TUS NOTAS:");
        Font fuente = new Font("Rockwell Nova",1,14);
        jTANotas.setFont(fuente);
    }
    
    //Método que activa la botonera lateral que da acceso al resto de pantallas
    public void botoneraVisible(){
        pnlBtnRegistro.setVisible(true);
        pnlBtnGestion.setVisible(true);
        pnlBtnAlertas.setVisible(true);
        pnlBtnMapa.setVisible(true);
        pnlBtnInfo.setVisible(true);
        pnlBtnFactura.setVisible(true);
    }
    
    private void insertArea(){
        miArea = crearArea(); //se crea una nueva area
        
        if (miArea != null) {
            try {
                conexion.updateBd(miArea.toSQL());
                //Instancio las parcelas seleccionadas y las meto en la BD
                for (int i = 0; i < miArea.getNumParcelas(); i++) {
                    Parcela parcela = new Parcela (i+1,true);
                    conexion.updateBd(parcela.toSQL());
                }
                JOptionPane.showMessageDialog(this,"Se han actualizado los datos con éxito");
                activarBlocNotas();
                btnEditarDatos.setText("Cancelar");
                cambiarFuncionBtn();
                botoneraVisible();//cambiar esta linea y la siguiente a despues de que se actualice el area
                btnEditarDatos.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this,"No se ha podido insertar el área.");
            }
        }
    }
    
    private void updateArea() throws SQLException{
        Area areaNueva = crearArea();
        String direccion = conexion.selectDato("SELECT direccion from area");
        int parcelasAntiguas = Integer.parseInt(conexion.selectDato("SELECT num_parcelas FROM area"));
        int parcelasNuevas = areaNueva.getNumParcelas();
        int contador = 0;
        //Si las parcelas del area antigua son menos que las de la nueva, las nuevas parcelas se añaden a la BD se cambia el área anterior por la nueva    
        if (parcelasAntiguas < parcelasNuevas){ 
            int sumarParcelas = parcelasNuevas - parcelasAntiguas;
            for (int i = 0; i < sumarParcelas; i++) {
                Parcela parcela = new Parcela (parcelasAntiguas+i+1,true);
                conexion.updateBd(parcela.toSQL());
            }
        //Si las parcelas del area antigua son mas que las de la nueva,las parcelas sobrantes se eliminarán solo si estan vacias
        }else if(parcelasAntiguas > parcelasNuevas){
            int borrarParcelas = parcelasAntiguas - parcelasNuevas;
            for (int i = 0; i < borrarParcelas; i++) { //Si alguna de las parcelas que se vana aeliminar contiene algun vehiculo saltará un panel informativo
                String consulta = "SELECT disponibilidad FROM parcelas WHERE num_parcela = "+(parcelasAntiguas-i)+";";
                int disponible = Integer.parseInt(conexion.selectDato(consulta));
                if (disponible == 0){//Si la disponibilidad de alguna de estas parcelas es 0 no se podrán eliminar
                    contador ++;
                }    
            }
            //Si alguna de las parcelas contiene vehiculos el contador aumenta y salta el siguiente mensaje, si no tienen vehículos se borran
            if (contador > 0){
                JOptionPane.showMessageDialog(this,"Algunas de las parcelas que se quieren eliminar contienen vehículos. Cámbialos de parcela o eliminalos para proceder.");
            }else{
                conexion.updateBd("DELETE FROM parcelas WHERE num_parcela > "+areaNueva.getNumParcelas()+";");
            }
        }
        if (contador <= 0){
            miArea = areaNueva;
            String update = "UPDATE area SET direccion = '"+miArea.getDireccion()+"',telefono ="
                    + miArea.getTelefono()+",mail = '"+miArea.getMail()+"',web = '"+miArea.getWeb()
                    + "',num_parcelas = "+miArea.getNumParcelas()+",precio_noche = "+miArea.getPrecioNoche();
            conexion.updateBd(update);
            JOptionPane.showMessageDialog(this,"Se han actualizado los datos con éxito");
            cambiarFuncionBtn();  
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCabecera = new javax.swing.JPanel();
        lblMinimizar = new javax.swing.JLabel();
        lblCerrar = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
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
        pnlServicios = new javax.swing.JPanel();
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
        jButton1 = new javax.swing.JButton();
        pnlPantallas = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlCabecera.setBackground(new java.awt.Color(212, 72, 168));
        pnlCabecera.setToolTipText("");

        lblMinimizar.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblMinimizar.setForeground(new java.awt.Color(255, 255, 255));
        lblMinimizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinimizar.setText("__");
        lblMinimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizarMouseClicked(evt);
            }
        });

        lblCerrar.setFont(new java.awt.Font("Rockwell Nova", 0, 18)); // NOI18N
        lblCerrar.setForeground(new java.awt.Color(255, 255, 255));
        lblCerrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCerrar.setText("X");
        lblCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Rockwell Nova", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("FurgoGestion");

        javax.swing.GroupLayout pnlCabeceraLayout = new javax.swing.GroupLayout(pnlCabecera);
        pnlCabecera.setLayout(pnlCabeceraLayout);
        pnlCabeceraLayout.setHorizontalGroup(
            pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCabeceraLayout.createSequentialGroup()
                .addContainerGap(697, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(359, 359, 359)
                .addComponent(lblMinimizar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlCabeceraLayout.setVerticalGroup(
            pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addGroup(pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMinimizar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlCabecera, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 30));

        pnlBtnFactura.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnFactura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        getContentPane().add(pnlBtnFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 340, 50));

        pnlBtnRegistro.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnRegistro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, null, null));
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

        getContentPane().add(pnlBtnRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 340, 50));

        pnlBtnGestion.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnGestion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, null, null));
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

        getContentPane().add(pnlBtnGestion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 50));

        pnlBtnMapa.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnMapa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, null, null));
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

        getContentPane().add(pnlBtnMapa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 340, 50));

        pnlBtnAlertas.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnAlertas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, null, null));
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

        getContentPane().add(pnlBtnAlertas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 340, 50));

        pnlBtnInfo.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnInfo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, null, null));
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

        getContentPane().add(pnlBtnInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 340, 50));
        getContentPane().add(lblMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 340, 710));

        pnlDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlDatosArea.setBackground(new java.awt.Color(0, 153, 204));
        pnlDatosArea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tfEmailArea.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        tfEmailArea.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlDatosArea.add(tfEmailArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 230, -1));

        tfNParcelas.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        tfNParcelas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlDatosArea.add(tfNParcelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 230, -1));

        tfPagWeb.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        tfPagWeb.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlDatosArea.add(tfPagWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 230, -1));

        lblPrecioNoche.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblPrecioNoche.setText("€/noche:");
        pnlDatosArea.add(lblPrecioNoche, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        tfprecioNoche.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        tfprecioNoche.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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

        tfDireccion.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        tfDireccion.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlDatosArea.add(tfDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 230, -1));

        tfTelefonoArea.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        tfTelefonoArea.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlDatosArea.add(tfTelefonoArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 230, -1));

        btnGuardarArea.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardarArea.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnGuardarArea.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarArea.setText("Guardar");
        btnGuardarArea.setBorder(null);
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
        btnEditarDatos.setBorder(null);
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

        pnlServicios.setBackground(new java.awt.Color(0, 153, 204));
        pnlServicios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl€.setText("€");
        pnlServicios.add(lbl€, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 20, 20));

        btnAnadir.setBackground(new java.awt.Color(0, 0, 0));
        btnAnadir.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnAnadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAnadir.setText("Añadir servicio");
        btnAnadir.setBorder(null);
        btnAnadir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirActionPerformed(evt);
            }
        });
        pnlServicios.add(btnAnadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 130, 30));

        tfOtroServicio.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        tfOtroServicio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlServicios.add(tfOtroServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 140, 30));

        tblServicios.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
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

        pnlServicios.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 430, 150));

        lblRellena1.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblRellena1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRellena1.setText("Servicios del área con precio extra:");
        pnlServicios.add(lblRellena1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 30, 500, -1));

        cbServicios.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        cbServicios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pasajero extra", "Electricidad", "Llenado/vaciado", "mascota", "Lavadora", "Secadora", "Ficha", "Otro servicio" }));
        cbServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbServiciosActionPerformed(evt);
            }
        });
        pnlServicios.add(cbServicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));

        tfPrecio.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        tfPrecio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlServicios.add(tfPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 60, -1));

        btnGuardarServicios.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardarServicios.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnGuardarServicios.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarServicios.setText("Guardar");
        btnGuardarServicios.setBorder(null);
        btnGuardarServicios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarServiciosActionPerformed(evt);
            }
        });
        pnlServicios.add(btnGuardarServicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 330, 100, 30));

        btnEliminarServ.setBackground(new java.awt.Color(255, 0, 51));
        btnEliminarServ.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnEliminarServ.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarServ.setText("Eliminar servicios");
        btnEliminarServ.setBorder(null);
        btnEliminarServ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarServActionPerformed(evt);
            }
        });
        pnlServicios.add(btnEliminarServ, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 160, 30));

        pnlDatos.add(pnlServicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 510, 390));

        jTANotas.setColumns(20);
        jTANotas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTANotas.setRows(5);
        spNotas.setViewportView(jTANotas);

        pnlDatos.add(spNotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 960, 240));

        jButton1.setText("Guardar");
        pnlDatos.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 630, 90, 40));

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
        pR = new PnlRegistro();
        llamarPnl(pR);
    }//GEN-LAST:event_pnlBtnRegistroMouseClicked

    private void pnlBtnGestionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnGestionMouseClicked
        pG =  new PnlGestion();
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
        miArea = crearArea();
    }//GEN-LAST:event_pnlBtnInfoMouseClicked

    private void pnlBtnFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnFacturaMouseClicked
        pF = new PnlFactura();
        llamarPnl(pF); 
    }//GEN-LAST:event_pnlBtnFacturaMouseClicked

    private void btnEliminarServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarServActionPerformed
        eliminarServicios();
    }//GEN-LAST:event_btnEliminarServActionPerformed

    private void btnGuardarServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarServiciosActionPerformed
        guardarServicios();
    }//GEN-LAST:event_btnGuardarServiciosActionPerformed

    private void cbServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbServiciosActionPerformed
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
        //Al pulsar boton se establece la conexión con la BD y se añaden a la tabla Area los datos del cuestionario
        //Comprobación de campos nulos
        if (comprobacionDatos()) {
            try {
                //si no hay datos en tabla Area, se realiza un insert del Area y sus parcelas en la BD
                if (conexion.tieneDatos("SELECT* FROM area") == false)insertArea();
                //Si existen datos se realiza un update.   
                else updateArea();
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "El área no ha podido insertarse.");
            } 
        }
        else {
            JOptionPane.showMessageDialog(this, "Compruebe los datos.");
        }
        
    }//GEN-LAST:event_btnGuardarAreaActionPerformed

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void lblMinimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseClicked
        setExtendedState(1);
    }//GEN-LAST:event_lblMinimizarMouseClicked
    
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
        ConexionBBDD conexion = new ConexionBBDD();

        try {
            conexion.crearTablas();  
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Fallo al conectar con la base de datos");
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTANotas;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblMinimizar;
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
    private javax.swing.JPanel pnlCabecera;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlDatosArea;
    private javax.swing.JPanel pnlPantallas;
    private javax.swing.JPanel pnlServicios;
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
