package datos;

import modelo.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.*;

public class MateriaDAO extends ConexionSQL{
    
    ModeloMateria materia;
    PreparedStatement prepStat;
    ArrayList<ModeloMateria> materias;
    Statement st;
    
    //-------------AGREGA UNA MATERIA EN LA BBDD------------------------------------------------------------------------------------------------------------------
    
    public void insertaMateriaDAO(ModeloMateria materia) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("INSERT INTO materia (mat_nombre, mat_prof_dni) VALUES (?,?)");

        prepStat.setString(1, materia.getMat_nombre());

        if(materia.getMat_prof_dni()!=0) {

            prepStat.setLong(2, materia.getMat_prof_dni());

        }else {

            prepStat.setObject(2, null);

        }

        prepStat.execute();

        desconectaBBDD();
        
    }
    
    //----------ELIMINA MATERIA---------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void eliminaMateriaDAO(String cod) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("DELETE FROM materia WHERE mat_cod=" + cod);

        prepStat.execute();

        desconectaBBDD();
    }
    
    //--------------DEVUELVE TODAS LAS MATERIAS-------------------------------------------------------------------------------------------------------------------
    
    public ArrayList<ModeloMateria> devuelveMateriaDAO() throws SQLException {
        
        materias = new ArrayList<>();
        
        conectaBBDD();

        st = getConexion().createStatement();            

        ResultSet rs = st.executeQuery("SELECT * FROM materia");

        while (rs.next()) {

            materia = new ModeloMateria();

            materia.setMat_cod(rs.getLong(1));
            materia.setMat_nombre(rs.getString(2));
            materia.setMat_prof_dni(rs.getLong(3));

            materias.add(materia);

        }

        desconectaBBDD();
        
        return materias;
        
    }
    
    //----------ACTUALIZA MATERIA-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaMateriaDAO(ModeloMateria materia, String cod) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("UPDATE materia SET mat_nombre=?, mat_prof_dni=? WHERE mat_cod=" + cod);

        prepStat.setString(1, materia.getMat_nombre());
        prepStat.setLong(2, materia.getMat_prof_dni());

        prepStat.execute();

        desconectaBBDD();
        
    }
    
}
