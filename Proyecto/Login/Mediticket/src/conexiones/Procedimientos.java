
package conexiones;

import java.sql.CallableStatement;
import java.sql.SQLException;


public class Procedimientos {
    
    
    public static void RegUsuario(String a, String b, String c, String d, String e, String f, String g, String h) throws SQLException {
            CallableStatement entrada = Conexiones.getConexion().prepareCall("{Call RegUsuario(?,?,?,?,?,?,?,?)}");
            entrada.setString(1, a);
            entrada.setString(2, b);
            entrada.setString(3, c);
            entrada.setString(4, d);
            entrada.setString(5, e);
            entrada.setString(6, f);
            entrada.setString(7, g);
            entrada.setString(8, h);
            entrada.execute();
            
    
    }
    
    
    
    public static void EntradaRolUsuario(int a, String b, String c) throws SQLException {
            CallableStatement entrada = Conexiones.getConexion().prepareCall("{Call EntradaRolUsuario(?,?,?)}");
            entrada.setInt(1, a);
            entrada.setString(2, b);
            entrada.setString(3, c);           
            entrada.execute();
            
    
    }
    

    public static void EliminarRolUsuario(String a)throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call EliminarRolUsuario(?)}");
        entrada.setString(1, a);
        entrada.execute();
    }
    
    public static void EliminarUsuario(String a)throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call EliminarUsuario(?)}");
        entrada.setString(1, a);
        entrada.execute();
    }
    
        public static void EncontrarUsuario(String a)throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call EncontrarUsuario(?)}");
        entrada.setString(1, a);
        entrada.execute();
    }
    
    
      /*  public static void EliminarRolUsuarioInt(int a)throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call EliminarRolUsuarioInt(?)}");
        entrada.setInt(1, a);
        entrada.execute();
    }
    
    public static void EliminarUsuarioInt(int a)throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call EliminarUsuarioInt(?)}");
        entrada.setInt(1, a);
        entrada.execute();
    }*/
    public static void BuscarArticulo(int a)throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call BuscarArticulo(?)}");
        entrada.setInt(1, a);
        entrada.execute();
    }
    
    
    public static void BuscarTodosRoles()throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call BuscarTodosRoles()}");        
        entrada.execute();
    }
    
    
        public static void RegPaciente(String a, String b, String c, String d, String e) throws SQLException {
            CallableStatement entrada = Conexiones.getConexion().prepareCall("{Call RegPaciente(?,?,?,?,?)}");
            entrada.setString(1, a);
            entrada.setString(2, b);
            entrada.setString(3, c);
            entrada.setString(4, d);
            entrada.setString(5, e);
            entrada.execute();
            
    
    }
        
        public static void EliminarPaciente(String a)throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call EliminarPaciente(?)}");
        entrada.setString(1, a);
        entrada.execute();
    }
    
        public static void EncontrarPaciente(String a)throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call EncontrarPaciente(?)}");
        entrada.setString(1, a);
        entrada.execute();
    }
    
            
        public static void AsignarTurno(int a, int b, String c, String d, String e) throws SQLException {
            CallableStatement entrada = Conexiones.getConexion().prepareCall("{Call AsignarTurno(?,?,?,?,?)}");
            entrada.setInt(1, a);
            entrada.setInt(2, b);
            entrada.setString(3, c);
            entrada.setString(4, d);
            entrada.setString(5, e);
            entrada.execute();
           
    }
        
        
        public static void RegistrarHistorial(String a, String b, String c, String d, String e)throws SQLException{
        CallableStatement entrada = Conexiones.getConexion().prepareCall("{call IngresarHistorial(?,?,?,?,?}");
        entrada.setString(1, a);
        entrada.setString(2, b);
        entrada.setString(3, c); 
        entrada.setString(4, d);
        entrada.setString(5,d);
        entrada.execute();
    }
        
        
    
}
