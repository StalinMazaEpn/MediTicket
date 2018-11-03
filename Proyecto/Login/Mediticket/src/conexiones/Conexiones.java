/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;




/**
 *
 * @author STALIN
 */
public class Conexiones {
    
    /**
     * @return the ingreso
     */
    public static boolean isIngreso() {
        return ingreso;
    }

    /**
     * @param aIngreso the ingreso to set
     */
    public static void setIngreso(boolean aIngreso) {
        ingreso = aIngreso;
    }

    
    static Connection contacto = null;
    static String stringDeConexion;
    private static boolean ingreso;
    
    public Conexiones(String stringDeConexion){
        this.stringDeConexion = stringDeConexion;
    }
    public Conexiones(){
    }
    
    public static Connection getConexion() {
    
    
        String urlConexion = "jdbc:sqlserver://"+stringDeConexion+":49846;databaseName=MEDITICKET2";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            setIngreso(true);
        
        }catch(ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"No se pudo establecer la conexion..Revise el Driver" + e.getMessage() , "ErrorMessage",JOptionPane.ERROR_MESSAGE);
            setIngreso(false);
        }
        
        
        try {
            setIngreso(true);
            contacto = DriverManager.getConnection(urlConexion,"dave","dave");
        }catch(SQLException e){
        
            JOptionPane.showMessageDialog(null,"Error de Conexion" + e.getMessage() , "ErrorMessage",JOptionPane.ERROR_MESSAGE);
            setIngreso(false);
        }

        return contacto;
    }
    
    
    public static ResultSet Consulta(String consulta){
    
    Connection con = getConexion();
    Statement declara;
    
    try {
        declara = con.createStatement();
        ResultSet respuesta = declara.executeQuery(consulta);
        return respuesta;
    
    }catch (SQLException e){
        JOptionPane.showMessageDialog(null,"Error de Conexion" + e.getMessage() , "ErrorMessage",JOptionPane.ERROR_MESSAGE);
    
    }
    return null;
    }
      
}
