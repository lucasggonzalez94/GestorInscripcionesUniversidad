package datos;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.*;

public class ProfesorDAO extends ConexionSQL{
    
    ModeloProfesor profesor;
    PreparedStatement prepStat;
    ArrayList<ModeloProfesor> profesores;
    Statement st;
    
    //-------------AGREGA PROFESOR EN LA BBDD------------------------------------------------------------------------------------------------------------------
    
    public void insertaProfesorDAO(ModeloProfesor profesor) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("INSERT INTO profesor (prof_dni, prof_nombre, prof_apellido, prof_fec_nac, prof_domicilio, prof_telefono) VALUES (?,?,?,?,?,?)");

        prepStat.setLong(1, profesor.getProf_dni());
        prepStat.setString(2, profesor.getProf_nombre());
        prepStat.setString(3, profesor.getProf_apellido());

        java.sql.Date fecha = null;
        String domicilio = null;

        if(profesor.getProf_fecha_nac() != null) {

            fecha = profesor.getProf_fecha_nac();

        }

        if (profesor.getProf_domicilio() != null) {

            domicilio = profesor.getProf_domicilio();

        }

        prepStat.setDate(4, fecha);
        prepStat.setString(5, domicilio);
        prepStat.setString(6, profesor.getProf_telefono());

        prepStat.execute();

        desconectaBBDD();
        
    }
    
    //----------ELIMINA PROFESOR---------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void eliminaProfesorDAO(String dni) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("DELETE FROM profesor WHERE prof_dni=" + dni);

        prepStat.execute();

        desconectaBBDD();
    }
    
    //--------------DEVUELVE TODOS LOS PROFESORES-------------------------------------------------------------------------------------------------------------------
    
    public ArrayList<ModeloProfesor> devuelveProfesorDAO() throws SQLException {
        
        profesores = new ArrayList<>();
        
        conectaBBDD();

        st = getConexion().createStatement();            

        ResultSet rs = st.executeQuery("SELECT * FROM profesor");

        while (rs.next()) {

            profesor = new ModeloProfesor();

            profesor.setProf_dni(rs.getLong(1));
            profesor.setProf_nombre(rs.getString(2));
            profesor.setProf_apellido(rs.getString(3));
            profesor.setProf_fecha_nac(rs.getDate(4));
            profesor.setProf_domicilio(rs.getString(5));
            profesor.setProf_telefono(rs.getString(6));

            profesores.add(profesor);

        }

        desconectaBBDD();
        
        return profesores;
        
    }
    
    //----------ACTUALIZA PROFESOR-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaProfesorDAO(ModeloProfesor profesor, String dni) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("UPDATE profesor SET prof_dni=?, prof_nombre=?, prof_apellido=?, prof_fec_nac=?, prof_domicilio=?, prof_telefono=? WHERE prof_dni=" + dni);

        prepStat.setLong(1, profesor.getProf_dni());
        prepStat.setString(2, profesor.getProf_nombre());
        prepStat.setString(3, profesor.getProf_apellido());
        prepStat.setDate(4, profesor.getProf_fecha_nac());
        prepStat.setString(5, profesor.getProf_domicilio());
        prepStat.setString(6, profesor.getProf_telefono());

        prepStat.execute();

        desconectaBBDD();
        
    }
    
}
