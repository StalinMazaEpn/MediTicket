/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;


import com.sun.glass.events.MouseEvent;
import conexiones.Limitar;
import conexiones.Procedimientos;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author josuericardo
 */
public class FrmAdministrador extends javax.swing.JFrame {
    static ResultSet mostrarU;
    static ResultSet mostrarP;
     static ResultSet mostrarT;
    static ResultSet RolUsu;
    static ResultSet buscar;
    /**
     * Creates new form FrmAdministrador
     */
       
       
        /*ArrayList<Rol> roles = new ArrayList<Rol>();
        List<Rol> roles2 = new List<Rol>();
        List<Rol> roles = new ArrayList<Rol>();*/
       static ResultSet res;
       static ResultSet res1;
       static ResultSet res2;
       String fechaNow;
       String rolN;
       int cont;
       int cont2;
       int areaEscogida;
        Date date = new Date();
        DateFormat fechaActual = new SimpleDateFormat("dd/MM/yyyy");
        int rolEscogido;
        
        List<String> roles = new ArrayList<String>();
        
        
    public FrmAdministrador() {
        initComponents();
        limitarr();
        /*Fondo*/
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon uno = new ImageIcon(this.getClass().getResource("../Imagenes/fondo8.png"));
        JLabel fondo = new JLabel();
        fondo.setIcon(uno);
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,uno.getIconWidth(),uno.getIconHeight());
        
       fechaNow = fechaActual.format(date);
       System.out.println("Fecha: "+ fechaNow);
       /* 
       System.out.println("Fecha: "+fechaActual.format(date));
       System.out.println("ElEMENTO ESCOGIGO " + CmbRoles.getSelectedItem().toString().getClass());*/
       res = conexiones.Conexiones.Consulta("Select * from ROL");
       try {
           while(res.next()){
               rolN = res.getString(2);
               System.out.println("rol " + rolN);
               roles.add(rolN);
           }
       } catch(SQLException e) {
       }
      
        System.out.println("Tamaño Lista" + roles.size());
      for(int i=0;i<roles.size();i++)
		{			
                        CmbRoles.addItem(roles.get(i));
                        System.out.println("rol " + roles.get(i));
                        
		}
       
        cargarUsuario();
        cargarRolUsuario();
        cargarPaciente();
        cargarTurno();
   
    }
    
    
    public void cargarTurno() {
        DefaultTableModel tabla = (DefaultTableModel) tbturno.getModel();
        tabla.setRowCount(0);
        mostrarT = conexiones.Conexiones.Consulta("select * from TURNO");
        try{
            while(mostrarT.next()){
                Vector U = new Vector();
                U.add(mostrarT.getString(1));
                U.add(mostrarT.getString(2));
                U.add(mostrarT.getString(3));
                U.add(mostrarT.getString(4));
                U.add(mostrarT.getString(5));
                tabla.addRow(U);
                tbturno.setModel(tabla);
            }
        }catch(SQLException e){}
        
    }
     private void LimpiarTextoTurno() {
    
             idturno.setText("");
             
             cedPacienteTur.setText("");
             cedUsuarioTur.setText("");
             fechaTur.setText("");
             
             idturno.requestFocus();
            
             cedPacienteTur.requestFocus();
             cedUsuarioTur.requestFocus();
             fechaTur.requestFocus();
         
    }
    public void limitarr(){
        txtCedula.setDocument(new Limitar(txtCedula,10));
        txtcedulaP.setDocument(new Limitar(txtcedulaP, 10));
    }
    public void cargarUsuario() {
        DefaultTableModel tabla = (DefaultTableModel) tbUsuarios.getModel();
        tabla.setRowCount(0);
        mostrarU = conexiones.Conexiones.Consulta("select * from USUARIO");
        try{
            while(mostrarU.next()){
                Vector U = new Vector();
                String pass = mostrarU.getString(5);
                char array[]= pass.toCharArray();
                for(int i =0; i<array.length;i++){
                array[i]=(char)(array[i]+(char)5);
                }
                String encriptado = String.valueOf(array);
                
                /*desencriptar
                char arrayD[]= encriptado.toCharArray();
                for(int i =0; i<arrayD.length;i++){
                    arrayD[i]= (char)(arrayD[i]+(char)5);
                }
                String desencriptado = String.valueOf(arrayD);
                */
                U.add(mostrarU.getString(1));
                U.add(mostrarU.getString(2));
                U.add(mostrarU.getString(3));
                U.add(mostrarU.getString(4));
                U.add(encriptado);
                U.add(mostrarU.getString(6));
                U.add(mostrarU.getString(7));
                U.add(mostrarU.getString(8));
                tabla.addRow(U);
                tbUsuarios.setModel(tabla);
            }
        }catch(SQLException e){}
        
    }
    
        public void cargarRolUsuario() {
        DefaultTableModel tabla1 = (DefaultTableModel) tbrolusuarios.getModel();
        tabla1.setRowCount(0);
        RolUsu = conexiones.Conexiones.Consulta("select * from ROLUSUARIO");
        try{
            while(RolUsu.next()){
                Vector U = new Vector();
                U.add(RolUsu.getString(1));
                U.add(RolUsu.getString(2));
                U.add(RolUsu.getString(3));

                tabla1.addRow(U);
                tbrolusuarios.setModel(tabla1);
            }
        }catch(SQLException e){}
        
      
    }
        
    public void cargarPaciente() {
        DefaultTableModel tabla = (DefaultTableModel) tbpaciente.getModel();
        tabla.setRowCount(0);
        mostrarP = conexiones.Conexiones.Consulta("select * from PACIENTE");
        try{
            while(mostrarP.next()){
                Vector U = new Vector();
                U.add(mostrarP.getString(1));
                U.add(mostrarP.getString(2));
                U.add(mostrarP.getString(3));
                U.add(mostrarP.getString(4));
                U.add(mostrarP.getString(5));
                tabla.addRow(U);
                tbpaciente.setModel(tabla);
            }
        }catch(SQLException e){}
        
    }
    
       public boolean validadorDeCedula(String cedula) {
                boolean cedulaCorrecta = false;

                try {

                if (cedula.length() == 10) // ConstantesApp.LongitudCedula
                {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                // Coeficientes de validación cédula
                // El decimo digito se lo considera dígito verificador
                 int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
                 int verificador = Integer.parseInt(cedula.substring(9,10));
                 int suma = 0;
                 int digito = 0;
                for (int i = 0; i < (cedula.length() - 1); i++) {
                 digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
                 suma += ((digito % 10) + (digito / 10));
                }

                if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                 cedulaCorrecta = true;
                }
                else if ((10 - (suma % 10)) == verificador) {
                 cedulaCorrecta = true;
                } else {
                 cedulaCorrecta = false;
                }
                } else {
                cedulaCorrecta = false;
                }
                } else {
                cedulaCorrecta = false;
                }
                } catch (NumberFormatException nfe) {
                cedulaCorrecta = false;
                } catch (Exception err) {
                System.out.println("Una excepcion ocurrio en el proceso de validadcion");
                cedulaCorrecta = false;
                }

                if (!cedulaCorrecta) {
                    
                    System.out.println("La Cédula ingresada es Incorrecta");
                } else {
                    System.out.println("La Cédula ingresada es correcta");
                }
                return cedulaCorrecta;
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        CmbRoles = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtEspecialidad = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnmodificar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btnregresar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtestado = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUsuarios = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbrolusuarios = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnmodificar2 = new javax.swing.JButton();
        btneliminar2 = new javax.swing.JButton();
        btnguardar2 = new javax.swing.JButton();
        btneditar2 = new javax.swing.JButton();
        btnsalir2 = new javax.swing.JButton();
        btnregresar2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtcedulaP = new javax.swing.JTextField();
        txtnombreP = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtapellidoP = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txttelefonoP = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtdireccionP = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbpaciente = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        idturno = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cedPacienteTur = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        cedUsuarioTur = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        fechaTur = new javax.swing.JTextField();
        btnAsignarTurno = new javax.swing.JButton();
        cmbareas = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbturno = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Administrador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Fax", 0, 12))); // NOI18N
        jPanel1.setOpaque(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel1.setText("Nombres:");

        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel4.setText("Cédula:");

        jLabel3.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel3.setText("Dirección:");

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel2.setText("Apellidos:");

        jLabel5.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel5.setText("Contraseña:");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel6.setText("Telefono: ");

        jLabel10.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel10.setText("Rol:");

        jLabel11.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel11.setText("Especialidad:");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setOpaque(false);

        btnmodificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/editar_converted.png"))); // NOI18N
        btnmodificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar_converted (1).png"))); // NOI18N
        btneliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnguardar.setFont(new java.awt.Font("Lucida Fax", 1, 12)); // NOI18N
        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar_converted.png"))); // NOI18N
        btnguardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/btbuscar.png"))); // NOI18N
        btneditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        btnregresar.setFont(new java.awt.Font("Lucida Fax", 1, 12)); // NOI18N
        btnregresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/inicio_converted.png"))); // NOI18N
        btnregresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnregresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresarActionPerformed(evt);
            }
        });

        btnsalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir (1).png"))); // NOI18N
        btnsalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btneditar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnregresar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btneliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnmodificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnguardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btneditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnregresar)
                    .addComponent(btnsalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel19.setText("Estado");

        txtestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtestadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(26, 26, 26)
                        .addComponent(txtNombre))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(23, 23, 23)
                        .addComponent(txtApellido))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(11, 11, 11)
                        .addComponent(txtPassword))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(22, 22, 22)
                        .addComponent(txtTelefono))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(22, 22, 22)
                        .addComponent(txtDireccion))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CmbRoles, 0, 229, Short.MAX_VALUE)
                            .addComponent(txtCedula)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEspecialidad)
                            .addComponent(txtestado))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(CmbRoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtestado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);

        tbUsuarios.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        tbUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Telefono", "Contraseña", "Especialidad", "Direccion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbUsuariosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbUsuarios);

        tbrolusuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cedula", "Rol", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbrolusuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setViewportView(tbrolusuarios);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("USUARIOS", jPanel1);
        jPanel1.getAccessibleContext().setAccessibleName("");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));
        jPanel5.setOpaque(false);

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setText("Registrar Paciente");

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setOpaque(false);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setOpaque(false);

        btnmodificar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/editar_converted.png"))); // NOI18N
        btnmodificar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmodificar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificar2ActionPerformed(evt);
            }
        });

        btneliminar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar_converted (1).png"))); // NOI18N
        btneliminar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btneliminar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar2ActionPerformed(evt);
            }
        });

        btnguardar2.setFont(new java.awt.Font("Lucida Fax", 1, 12)); // NOI18N
        btnguardar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar_converted.png"))); // NOI18N
        btnguardar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnguardar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar2ActionPerformed(evt);
            }
        });

        btneditar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/btbuscar.png"))); // NOI18N
        btneditar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btneditar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btneditar2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnguardar2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmodificar2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminar2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btneliminar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnmodificar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnguardar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btneditar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnsalir2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir (1).png"))); // NOI18N
        btnsalir2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsalir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalir2ActionPerformed(evt);
            }
        });

        btnregresar2.setFont(new java.awt.Font("Lucida Fax", 1, 12)); // NOI18N
        btnregresar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/inicio_converted.png"))); // NOI18N
        btnregresar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnregresar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresar2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel8.setText("Cédula: ");

        jLabel12.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel12.setText("Nombre: ");

        txtcedulaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcedulaPActionPerformed(evt);
            }
        });
        txtcedulaP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcedulaPKeyPressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel13.setText("Apellido: ");

        jLabel14.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel14.setText("Teléfono:");

        jLabel15.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        jLabel15.setText("Dirección: ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(btnregresar2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnsalir2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtdireccionP, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtnombreP, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtcedulaP)
                            .addComponent(txtapellidoP, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txttelefonoP, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(24, 24, 24))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtcedulaP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtnombreP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtapellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txttelefonoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtdireccionP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnsalir2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnregresar2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(48, 48, 48))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setOpaque(false);

        tbpaciente.setFont(new java.awt.Font("Lucida Fax", 0, 12)); // NOI18N
        tbpaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cédula", "Nombre", "Apellido", "Teléfono", "Dirección"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbpaciente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbpaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbpacienteMouseClicked(evt);
            }
        });
        tbpaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbpacienteKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tbpaciente);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(394, 394, 394))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43))
        );

        jTabbedPane1.addTab("PACIENTES", jPanel5);

        jPanel11.setOpaque(false);

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setOpaque(false);

        jLabel20.setText("ID");

        jLabel21.setText(" Area");

        jLabel22.setText("Cedula Paciente");

        jLabel23.setText("Cedula Usuario");

        jLabel24.setText("Fecha Asignacion");

        btnAsignarTurno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/turno2.png"))); // NOI18N
        btnAsignarTurno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAsignarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarTurnoActionPerformed(evt);
            }
        });

        cmbareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbareasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(btnAsignarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel23))
                    .addComponent(jLabel24)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20)))
                .addGap(58, 58, 58)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cedUsuarioTur, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                    .addComponent(cedPacienteTur)
                    .addComponent(idturno)
                    .addComponent(fechaTur)
                    .addComponent(cmbareas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(idturno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cmbareas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cedPacienteTur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cedUsuarioTur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(fechaTur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(btnAsignarTurno)
                .addContainerGap())
        );

        tbturno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "IDAREA", "CEDULA PACIENTE", "CEDULA USUARIO", "FECHA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbturno);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(501, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 58, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Turno", jPanel7);

        jLabel29.setFont(new java.awt.Font("Lucida Fax", 0, 36)); // NOI18N
        jLabel29.setText("MediTicket");

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/epn_converted.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Lucida Fax", 0, 18)); // NOI18N
        jLabel17.setText("ADMINISTRADOR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(347, 347, 347))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(371, 371, 371))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbUsuariosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbUsuariosKeyPressed

        // TODO add your handling code here:
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            int dato = tbUsuarios.getSelectedRow();
            if(dato >= 0){
                txtCedula.setText(tbUsuarios.getValueAt(dato, 0).toString());
                txtNombre.setText(tbUsuarios.getValueAt(dato, 1).toString());
                txtApellido.setText(tbUsuarios.getValueAt(dato, 2).toString());
                txtTelefono.setText(tbUsuarios.getValueAt(dato, 3).toString());
                txtPassword.setText(tbUsuarios.getValueAt(dato, 4).toString());
                txtEspecialidad.setText(tbUsuarios.getValueAt(dato, 5).toString());
                txtDireccion.setText(tbUsuarios.getValueAt(dato, 6).toString());

            }
        }
    }//GEN-LAST:event_tbUsuariosKeyPressed

    private void btnregresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresarActionPerformed
        // TODO add your handling code here:
        FrmLogin ingreso = new FrmLogin();
        ingreso.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnregresarActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnsalirActionPerformed

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        if(txtCedula.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Para buscar un usuario, debe ingresar la cedula", "Error",JOptionPane.ERROR_MESSAGE);
            txtCedula.setText("");
            txtCedula.requestFocus();
        }else{
            try{
                String cedula= txtCedula.getText();
                Procedimientos.EncontrarUsuario(cedula);
                txtCedula.setText("");
                txtApellido.setText("");
                txtNombre.setText("");
                txtEspecialidad.setText("");
                txtDireccion.setText("");
                txtPassword.setText("");
                txtTelefono.setText("");

                txtCedula.requestFocus();
                txtApellido.requestFocus();
                txtNombre.requestFocus();
                txtEspecialidad.requestFocus();
                txtDireccion.requestFocus();
                txtPassword.requestFocus();
                txtTelefono.requestFocus();

                buscar= conexiones.Conexiones.Consulta("select * from USUARIO");
                while(buscar.next()){
                    if(buscar.getString(1).equals(cedula)){

                        JOptionPane.showMessageDialog(null, "datos encontrados");
                        txtCedula.setText(buscar.getString(1));
                        txtNombre.setText(buscar.getString(2));
                        txtApellido.setText(buscar.getString(3));
                        txtTelefono.setText(buscar.getString(4));
                        txtPassword.setText(buscar.getString(5));
                        txtEspecialidad.setText(buscar.getString(6));
                        txtDireccion.setText(buscar.getString(7));

                    }
                }
                //JOptionPane.showMessageDialog(null, "No se encontro ningun resultado");

            }catch(SQLException e){
            }
        }
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        /*

        int rolEscogido;

        rolEscogido = CmbRoles.getSelectedIndex() + 1;

        */
        
        
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtDireccion.getText().isEmpty()
            || txtCedula.getText().isEmpty() || txtPassword.getText().isEmpty() || txtTelefono.getText().isEmpty()
            || txtEspecialidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Porfavor llene todos los campos","Informacion",JOptionPane.INFORMATION_MESSAGE);

            LimpiarTexto();

        } else {
            try {
                res = conexiones.Conexiones.Consulta("Select COUNT(CEDULAUSUARIO) from USUARIO where CEDULAUSUARIO='" + txtCedula.getText() + "'");
                try {
                    while(res.next()){
                        cont = res.getInt(1);
                    }
                } catch(SQLException e) {
                }

                if(cont >=1) {

                    JOptionPane.showMessageDialog(rootPane,"Este usuario ya existe","Informacion",JOptionPane.ERROR_MESSAGE);
                } else if ( validadorDeCedula(txtCedula.getText()) ) {
                        JOptionPane.showMessageDialog(rootPane,"CEDULA CORRECTA","informacion",JOptionPane.INFORMATION_MESSAGE);
                        Procedimientos.RegUsuario(txtCedula.getText(),txtNombre.getText(), txtApellido.getText(),txtTelefono.getText(),
                        txtPassword.getText(),txtEspecialidad.getText() ,txtDireccion.getText(),txtestado.getText());
                        JOptionPane.showMessageDialog(rootPane,"Datos Ingresados Correctamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
                    
                        } else {
       
                            JOptionPane.showMessageDialog(rootPane,"CEDULA INCORRECTA","Error",JOptionPane.ERROR_MESSAGE);
                    }
                        
                    
                    
                    
                    
                    
                
            } catch(SQLException e) {
            }
        }

        rolEscogido = CmbRoles.getSelectedIndex() + 1;
        System.out.println("ElEMENTO ID " + rolEscogido);

        try {            
                res = conexiones.Conexiones.Consulta("Select COUNT(CEDULAUSUARIO) from ROLUSUARIO where CEDULAUSUARIO='" + txtCedula.getText() +"'AND IDROL='"+rolEscogido+"'");
                
                try {
                        while(res.next()){
                            cont = res.getInt(1);
                    }
                } catch(SQLException e) {
                }
                
                if(cont >=1) {
                    
                        JOptionPane.showMessageDialog(rootPane,"Este elemento ya existe","Informacion",JOptionPane.ERROR_MESSAGE);
                    } else {
                                                                    
                        Procedimientos.EntradaRolUsuario(rolEscogido,txtCedula.getText(), fechaNow);                     
                        JOptionPane.showMessageDialog(rootPane,"Datos Rol Ingresados Correctamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
                    }             
                                
          } catch(SQLException e) {
            
            
         }
        cargarUsuario();
        cargarRolUsuario();
        LimpiarTexto();

        /*try {

            clienteController.create(rolUser);

            JOptionPane.showMessageDialog(rootPane,"Usuarion Ingresado con "
                +"exito","Informacion",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane,ex.getMessage()
                ,"Error",JOptionPane.ERROR_MESSAGE);
        }*/
        /*
        /*

        traerlo del logon

        */
        
        
        
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        // TODO add your handling code here:
        int comprobar = JOptionPane.showConfirmDialog(this, "Seguro quieres cambiar el estado de este usuario a desactivo?","Pregunta", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        String cedul = txtCedula.getText();
        if(comprobar == JOptionPane.YES_OPTION){
        
        try{
            PreparedStatement pps = conexiones.Conexiones.getConexion().prepareStatement("UPDATE USUARIO SET CEDULAUSUARIO= '"+
                txtCedula.getText()  + "', NOMBRE= '"+ txtNombre.getText() + "', APELLIDO ='"+ txtApellido.getText() + "',TELEFONO = '"+txtTelefono.getText()+
                "',PASSWORD = '"+txtPassword.getText()+"', ESPECIALIDAD = '"+txtEspecialidad.getText()+ "',DIRECCION = '"+txtDireccion.getText() + "',ESTADO = '"+"D"+
                "'WHERE USUARIO.CEDULAUSUARIO = '"+txtCedula.getText()+"'");
            pps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Datos modificados correctamente");
            cargarUsuario();
            LimpiarTexto();

        }catch(SQLException e){

        }
        }
        /*int comprobar = JOptionPane.showConfirmDialog(this, "Seguro quieres cambiar el estado de este usuario a desactivo?","Pregunta", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        String cedul = txtCedula.getText();
        if(comprobar == JOptionPane.YES_OPTION){
            try{
                Procedimientos.EliminarRolUsuario(cedul);
                Procedimientos.EliminarUsuario(cedul);
                cargarUsuario();
                cargarRolUsuario();
                LimpiarTexto();
            }catch(SQLException e){
            }

        }*/

        /* //CON ID DE TABLA
        int row = tbUsuarios.getSelectedRow();
        int comprobar = JOptionPane.showConfirmDialog(this, "Seguro quieres eliminar este usuario?","Pregunta", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

        if(comprobar == JOptionPane.YES_OPTION){
            try{
                Procedimientos.EliminarRolUsuarioInt(Integer.parseInt(tbrolusuarios.getValueAt(row, 0).toString()));
                Procedimientos.EliminarUsuarioInt(Integer.parseInt(tbUsuarios.getValueAt(row, 0).toString()));
                cargarUsuario();
                cargarRolUsuario();
            }catch(SQLException e){
            }

        }
        */
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed

        try{
            PreparedStatement pps = conexiones.Conexiones.getConexion().prepareStatement("UPDATE USUARIO SET CEDULAUSUARIO= '"+
                txtCedula.getText()  + "', NOMBRE= '"+ txtNombre.getText() + "', APELLIDO ='"+ txtApellido.getText() + "',TELEFONO = '"+txtTelefono.getText()+
                "',PASSWORD = '"+txtPassword.getText()+"', ESPECIALIDAD = '"+txtEspecialidad.getText()+ "',DIRECCION = '"+txtDireccion.getText() + "',ESTADO = '"+txtestado.getText()+
                "'WHERE USUARIO.CEDULAUSUARIO = '"+txtCedula.getText()+"'");
            pps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos modificados correctamente");
            cargarUsuario();
            LimpiarTexto();

        }catch(SQLException e){

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void txtCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            if(txtCedula.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Para buscar un usuario, debe ingresar la cedula", "Error",JOptionPane.ERROR_MESSAGE);
                txtCedula.setText("");
                txtCedula.requestFocus();
            }else{
                try{
                    String cedula= txtCedula.getText();
                    Procedimientos.EncontrarUsuario(cedula);
                    txtCedula.setText("");
                    txtApellido.setText("");
                    txtNombre.setText("");
                    txtEspecialidad.setText("");
                    txtDireccion.setText("");
                    txtPassword.setText("");
                    txtTelefono.setText("");

                    txtCedula.requestFocus();
                    txtApellido.requestFocus();
                    txtNombre.requestFocus();
                    txtEspecialidad.requestFocus();
                    txtDireccion.requestFocus();
                    txtPassword.requestFocus();
                    txtTelefono.requestFocus();

                    buscar= conexiones.Conexiones.Consulta("select * from USUARIO");
                    while(buscar.next()){
                        if(buscar.getString(1).equals(cedula)){

                            JOptionPane.showMessageDialog(null, "datos encontrados");
                            txtCedula.setText(buscar.getString(1));
                            txtNombre.setText(buscar.getString(2));
                            txtApellido.setText(buscar.getString(3));
                            txtTelefono.setText(buscar.getString(4));
                            txtPassword.setText(buscar.getString(5));
                            txtEspecialidad.setText(buscar.getString(6));
                            txtDireccion.setText(buscar.getString(7));

                        }
                    }
                    //JOptionPane.showMessageDialog(null, "No se encontro ningun resultado");

                }catch(SQLException e){
                }
            }

        }
    }//GEN-LAST:event_txtCedulaKeyPressed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtcedulaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcedulaPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcedulaPActionPerformed

    private void btnmodificar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificar2ActionPerformed
        // TODO add your handling code here:
        
        try{
            PreparedStatement pps = conexiones.Conexiones.getConexion().prepareStatement("UPDATE PACIENTE SET CEDULAPACIENTE= '"+
                txtcedulaP.getText()  + "', NOMBRE= '"+ txtnombreP.getText() + "', APELLIDO ='"+ txtapellidoP.getText() + "',TELEFONO = '"+txttelefonoP.getText()+
                 "',DIRECCION = '"+txtdireccionP.getText() +
                "'WHERE PACIENTE.CEDULAPACIENTE = '"+txtcedulaP.getText()+"'");
            pps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos modificados correctamente");
            cargarPaciente();
            LimpiarTextoPaciente();
        }catch(SQLException e){

        }
    }//GEN-LAST:event_btnmodificar2ActionPerformed

    private void btneliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar2ActionPerformed
        // TODO add your handling code here:
        
        int comprobar = JOptionPane.showConfirmDialog(this, "Seguro quieres eliminar este paciente?","Pregunta", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        String cedul = txtcedulaP.getText();
        if(comprobar == JOptionPane.YES_OPTION){
            try{
                Procedimientos.EliminarPaciente(cedul);
                cargarPaciente();
                LimpiarTextoPaciente();
            }catch(SQLException e){
            }

        }
    }//GEN-LAST:event_btneliminar2ActionPerformed

    private void btnguardar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar2ActionPerformed
        // TODO add your handling code here:
           if (txtcedulaP.getText().isEmpty() || txtnombreP.getText().isEmpty() || txtapellidoP.getText().isEmpty()
            || txttelefonoP.getText().isEmpty() || txtdireccionP.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Porfavor llene todos los campos","Informacion",JOptionPane.INFORMATION_MESSAGE);

            LimpiarTextoPaciente();

        } else {
            try {
                res1 = conexiones.Conexiones.Consulta("Select COUNT(NOMBRE) from PACIENTE where NOMBRE='" + txtnombreP.getText() + "'");
                try {
                    while(res1.next()){
                        cont = res1.getInt(1);
                    }
                } catch(SQLException e) {
                }

                if(cont >=1) {

                    JOptionPane.showMessageDialog(rootPane,"Este usuario ya existe","Informacion",JOptionPane.ERROR_MESSAGE);
                } else {
                    Procedimientos.RegPaciente(txtcedulaP.getText(),txtnombreP.getText(), txtapellidoP.getText(),txttelefonoP.getText(),
                       txtdireccionP.getText());
                    JOptionPane.showMessageDialog(rootPane,"Datos Ingresados Correctamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
                    cargarPaciente();
                }
            } catch(SQLException e) {
            }
        }
      
    }//GEN-LAST:event_btnguardar2ActionPerformed

    private void btneditar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditar2ActionPerformed
        // TODO add your handling code here:
        if(txtcedulaP.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Para buscar un usuario, debe ingresar la cedula", "Error",JOptionPane.ERROR_MESSAGE);
            txtCedula.setText("");
            txtCedula.requestFocus();
        }else{
            try{
                String cedula= txtcedulaP.getText();
                Procedimientos.EncontrarPaciente(cedula);
                txtcedulaP.setText("");
                txtapellidoP.setText("");
                txtnombreP.setText("");
                txtdireccionP.setText("");
                txttelefonoP.setText("");

                txtcedulaP.requestFocus();
                txtapellidoP.requestFocus();
                txtnombreP.requestFocus();
                txtdireccionP.requestFocus();
                txttelefonoP.requestFocus();

                buscar= conexiones.Conexiones.Consulta("select * from PACIENTE");
                while(buscar.next()){
                    if(buscar.getString(1).equals(cedula)){

                        JOptionPane.showMessageDialog(null, "datos encontrados");
                        txtcedulaP.setText(buscar.getString(1));
                        txtnombreP.setText(buscar.getString(2));
                        txtapellidoP.setText(buscar.getString(3));
                        txttelefonoP.setText(buscar.getString(4));
                        txtdireccionP.setText(buscar.getString(5));

                    }
                }
                //JOptionPane.showMessageDialog(null, "No se encontro ningun resultado");

            }catch(SQLException e){
            }
        }
    }//GEN-LAST:event_btneditar2ActionPerformed

    private void btnsalir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalir2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnsalir2ActionPerformed

    private void btnregresar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresar2ActionPerformed
        // TODO add your handling code here:
        FrmLogin ingreso = new FrmLogin();
        ingreso.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnregresar2ActionPerformed

    private void txtcedulaPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcedulaPKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            if(txtcedulaP.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Para buscar un usuario, debe ingresar la cedula", "Error",JOptionPane.ERROR_MESSAGE);
            txtCedula.setText("");
            txtCedula.requestFocus();
        }else{
            try{
                String cedula= txtcedulaP.getText();
                Procedimientos.EncontrarPaciente(cedula);
                txtcedulaP.setText("");
                txtapellidoP.setText("");
                txtnombreP.setText("");
                txtdireccionP.setText("");
                txttelefonoP.setText("");

                txtcedulaP.requestFocus();
                txtapellidoP.requestFocus();
                txtnombreP.requestFocus();
                txtdireccionP.requestFocus();
                txttelefonoP.requestFocus();

                buscar= conexiones.Conexiones.Consulta("select * from PACIENTE");
                while(buscar.next()){
                    if(buscar.getString(1).equals(cedula)){

                        JOptionPane.showMessageDialog(null, "datos encontrados");
                        txtcedulaP.setText(buscar.getString(1));
                        txtnombreP.setText(buscar.getString(2));
                        txtapellidoP.setText(buscar.getString(3));
                        txttelefonoP.setText(buscar.getString(4));
                        txtdireccionP.setText(buscar.getString(5));

                    }
                }
                //JOptionPane.showMessageDialog(null, "No se encontro ningun resultado");

            }catch(SQLException e){
            }
        }
    }                        
        
        
        
    }//GEN-LAST:event_txtcedulaPKeyPressed

    private void tbpacienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbpacienteKeyPressed
        // TODO add your handling code here:
          // TODO add your handling code here:
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            int dat = tbpaciente.getSelectedRow();
            if(dat >= 0){
                
                txtcedulaP.setText(tbpaciente.getValueAt(dat, 0).toString());
                txtnombreP.setText(tbpaciente.getValueAt(dat, 1).toString());
                txtapellidoP.setText(tbpaciente.getValueAt(dat, 2).toString());
                txttelefonoP.setText(tbpaciente.getValueAt(dat, 3).toString());
                txtdireccionP.setText(tbpaciente.getValueAt(dat, 4).toString());

            }
        }
    }//GEN-LAST:event_tbpacienteKeyPressed

    private void tbpacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbpacienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbpacienteMouseClicked

    private void txtestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtestadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtestadoActionPerformed

    private void btnAsignarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarTurnoActionPerformed
        // TODO add your handling code here:

        if (idturno.getText().isEmpty() ||  cedPacienteTur.getText().isEmpty()
            || cedUsuarioTur.getText().isEmpty() || fechaTur.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Porfavor llene todos los campos","Informacion",JOptionPane.INFORMATION_MESSAGE);

            //LimpiarTextoPaciente();

        } else {
            try {
                res2 = conexiones.Conexiones.Consulta("Select COUNT(FECHAASIGNACION) from TURNO where FECHAASIGNACION='" + fechaTur.getText() + "'");
                try {
                    while(res2.next()){
                        cont2 = res2.getInt(1);
                    }
                } catch(SQLException e) {
                }
                areaEscogida = cmbareas.getSelectedIndex() + 1;
                System.out.println("ElEMENTO ID " + areaEscogida);

                if(cont2 >=1) {

                    JOptionPane.showMessageDialog(rootPane,"Ya existe un turno","Informacion",JOptionPane.ERROR_MESSAGE);
                } else {
                    Procedimientos.AsignarTurno(Integer.parseInt(idturno.getText()),areaEscogida, cedPacienteTur.getText(),cedUsuarioTur.getText(),
                        fechaTur.getText());
                    JOptionPane.showMessageDialog(rootPane,"TURNO ASIGNADO","Informacion",JOptionPane.INFORMATION_MESSAGE);
                    cargarTurno();
                    LimpiarTextoTurno();
                    Procedimientos.RegistrarHistorial(idturno.getText(), cedPacienteTur.getText(), "c", "d", "e");

                }
            } catch(SQLException e) {
            }
        }
    }//GEN-LAST:event_btnAsignarTurnoActionPerformed

    private void cmbareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbareasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbareasActionPerformed

    
    private void LimpiarTexto() {
    
             txtNombre.setText("");
             txtApellido.setText("");
             txtDireccion.setText("");
             txtCedula.setText("");
             txtPassword.setText("");
             txtTelefono.setText("");
             txtEspecialidad.setText("");
             
             txtNombre.requestFocus();
             txtApellido.requestFocus();
             txtDireccion.requestFocus();
             txtCedula.requestFocus();
             txtPassword.requestFocus();
             txtTelefono.requestFocus();
             txtEspecialidad.requestFocus();
    
    
    
    }
    
        private void LimpiarTextoPaciente() {
    
             txtnombreP.setText("");
             txtapellidoP.setText("");
             txtdireccionP.setText("");
             txtcedulaP.setText("");
             txttelefonoP.setText("");
             
             txtnombreP.requestFocus();
             txtapellidoP.requestFocus();
             txtdireccionP.requestFocus();
             txtcedulaP.requestFocus();
             
             txttelefonoP.requestFocus();
          
    
    }
    
    
    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(FrmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAdministrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CmbRoles;
    private javax.swing.JButton btnAsignarTurno;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneditar2;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btneliminar2;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnguardar2;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnmodificar2;
    private javax.swing.JButton btnregresar;
    private javax.swing.JButton btnregresar2;
    private javax.swing.JButton btnsalir;
    private javax.swing.JButton btnsalir2;
    private javax.swing.JTextField cedPacienteTur;
    private javax.swing.JTextField cedUsuarioTur;
    private javax.swing.JComboBox<String> cmbareas;
    private javax.swing.JTextField fechaTur;
    private javax.swing.JTextField idturno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbUsuarios;
    private javax.swing.JTable tbpaciente;
    private javax.swing.JTable tbrolusuarios;
    private javax.swing.JTable tbturno;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtapellidoP;
    private javax.swing.JTextField txtcedulaP;
    private javax.swing.JTextField txtdireccionP;
    private javax.swing.JTextField txtestado;
    private javax.swing.JTextField txtnombreP;
    private javax.swing.JTextField txttelefonoP;
    // End of variables declaration//GEN-END:variables
}
