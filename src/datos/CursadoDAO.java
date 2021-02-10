package datos;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.*;

public class CursadoDAO extends ConexionSQL{
    
    ModeloCursado cursado;
    PreparedStatement prepStat;
    ArrayList<ModeloCursado> cursados;
    Statement st;
    
    //-------------AGREGA UN CURSADO EN LA BBDD------------------------------------------------------------------------------------------------------------------
    
    public void insertaCursadoDAO(ModeloCursado cursado) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("INSERT INTO cursado (cur_alu_dni, cur_mat_cod, cur_nota) VALUES (?,?,?)");

        prepStat.setLong(1, cursado.getCur_alu_dni());
        prepStat.setLong(2, cursado.getCur_mat_cod());

        if (cursado.getCur_nota() != 0) {

            prepStat.setInt(3, cursado.getCur_nota());

        }else {

            prepStat.setObject(3, null);

        }

        prepStat.execute();

        desconectaBBDD();
        
    }
    
    //----------ELIMINA CURSADO---------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void eliminaCursadoDAO(String dni, String cod) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("DELETE FROM cursado WHERE cur_alu_dni=" + dni + " AND cur_mat_cod=" + cod);

        prepStat.execute();

        desconectaBBDD();
            
    }
    
    //--------------DEVUELVE TODOS LOS CURSADOS-------------------------------------------------------------------------------------------------------------------
    
    public ArrayList<ModeloCursado> devuelveCursadoDAO() throws SQLException {
        
        cursados = new ArrayList<>();
        
        conectaBBDD();

        st = getConexion().createStatement();            

        ResultSet rs = st.executeQuery("SELECT * FROM cursado");

        while (rs.next()) {

            cursado = new ModeloCursado();

            cursado.setCur_alu_dni(rs.getLong(1));
            cursado.setCur_mat_cod(rs.getLong(2));
            cursado.setCur_nota(rs.getInt(3));

            cursados.add(cursado);

        }

        desconectaBBDD();
        
        return cursados;
        
    }
    
    //----------ACTUALIZA CURSADO-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaCursadoDAO(ModeloCursado cursado, String dni, String cod) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("UPDATE cursado SET cur_alu_dni=?, cur_mat_cod=?, cur_nota=? WHERE cur_alu_dni=" + dni + " AND cur_mat_cod=" + cod);

        prepStat.setLong(1, cursado.getCur_alu_dni());
        prepStat.setLong(2, cursado.getCur_mat_cod());
        prepStat.setInt(3, cursado.getCur_nota());

        prepStat.execute();

        desconectaBBDD();
        
    }
    
}
