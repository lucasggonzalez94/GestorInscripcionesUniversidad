package datos;

import java.sql.*;
import javax.swing.JOptionPane;

public class ConexionSQL {
    
    private Connection conexion = null;

    public void conectaBBDD() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/gonzalez_lucas_2020", "root",""); // CONEXION CON LA BASE DE DATOS
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "No ha sido posible conectar con la base de datos.");
            
        }
    }
    
    public void desconectaBBDD() {
        
        try {
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
}
