package datos;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import modelo.*;

public class InscripcionDAO extends ConexionSQL{
    
    ModeloInscripcion inscripcion;
    PreparedStatement prepStat;
    ArrayList<ModeloInscripcion> inscripciones;
    Statement st;
    
    //-------------AGREGA UNA INSCRIPCION EN LA BBDD------------------------------------------------------------------------------------------------------------------
    
    public void insertaInscDAO(ModeloInscripcion inscripcion) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("INSERT INTO inscripcion (insc_cod, insc_dni_alumno, insc_fecha, insc_car_cod) VALUES (?,?,?,?)");

        prepStat.setLong(1, inscripcion.getInsc_cod());
        prepStat.setLong(2, inscripcion.getInsc_nombre());
        prepStat.setDate(3, inscripcion.getInsc_fecha());
        prepStat.setLong(4, inscripcion.getInsc_car_cod());

        prepStat.execute();

        desconectaBBDD();
        
    }
    
    //----------ELIMINA INSCRIPCION---------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void eliminaInscripDAO(String id) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("DELETE FROM inscripcion WHERE insc_cod=" + id);

        prepStat.execute();

        desconectaBBDD();
            
    }
    
    //--------------DEVUELVE TODAS LAS INSCRIPCIONES-------------------------------------------------------------------------------------------------------------------
    
    public ArrayList<ModeloInscripcion> devuelveInscDAO() throws SQLException {
        
        inscripciones = new ArrayList<>();
        
        conectaBBDD();

        st = getConexion().createStatement();            

        ResultSet rs = st.executeQuery("SELECT * FROM inscripcion");

        while (rs.next()) {

            inscripcion = new ModeloInscripcion();

            inscripcion.setInsc_cod(rs.getLong(1));
            inscripcion.setDni_Alumno_Insc(rs.getLong(2));
            inscripcion.setInsc_fecha(rs.getDate(3));
            inscripcion.setInsc_car_cod(rs.getLong(4));

            inscripciones.add(inscripcion);

        }

        desconectaBBDD();
        
        return inscripciones;
        
    }
    
    //-------------AGREGA UNA INSCRIPCION A UN ALUMNO-------------------------------------------------------------------------------------------------------------------
    
    public void inscribeDAO(long cod, String dni) throws SQLException {
        
        conectaBBDD();

        Statement st = getConexion().createStatement();

        st.execute("UPDATE alumno SET alu_insc_cod=" + cod + " WHERE alu_dni=" + dni);
        
    }
    
    //----------ACTUALIZA INSCRIPCION-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaInscripcionDAO(ModeloInscripcion insc, String cod) throws SQLException {
        
            conectaBBDD();
            
            prepStat = getConexion().prepareStatement("UPDATE inscripcion SET insc_cod=?, insc_dni_alumno=?, insc_fecha=?, insc_car_cod=? WHERE insc_cod=" + cod);
            
            prepStat.setLong(1, insc.getInsc_cod());
            prepStat.setLong(2, insc.getInsc_nombre());
            prepStat.setDate(3, insc.getInsc_fecha());
            prepStat.setLong(4, insc.getInsc_car_cod());
            
            prepStat.execute();
            
            desconectaBBDD();
        
    }
    
}
