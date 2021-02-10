package datos;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import modelo.*;

public class CarreraDAO extends ConexionSQL{
    
    ModeloCarrera carrera;
    PreparedStatement prepStat;
    ArrayList<ModeloCarrera> carreras;
    Statement st;
    
    //-------------AGREGA UNA CARRERA EN LA BBDD------------------------------------------------------------------------------------------------------------------
    
    public void insertaCarreraDAO(ModeloCarrera carrera) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("INSERT INTO carrera (car_cod, car_nombre, car_duracion) VALUES (?,?,?)");

        prepStat.setLong(1, carrera.getCar_cod());
        prepStat.setString(2, carrera.getCar_nombre());

        if(carrera.getCar_duracion()!=0) {

            prepStat.setInt(3, carrera.getCar_duracion());

        }else {

            prepStat.setObject(3, null);

        }

        prepStat.execute();

        desconectaBBDD();
        
    }
    
    //----------ELIMINA CARRERA---------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void eliminaCarreraDAO(String cod) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("DELETE FROM carrera WHERE car_cod=" + cod);

        prepStat.execute();

        desconectaBBDD();
        
    }
    
    //--------------DEVUELVE TODAS LAS INSCRIPCIONES-------------------------------------------------------------------------------------------------------------------
    
    public ArrayList<ModeloCarrera> devuelveCarrerasDAO() throws SQLException {
        
        carreras = new ArrayList<>();
        
        conectaBBDD();

        st = getConexion().createStatement();            

        ResultSet rs = st.executeQuery("SELECT * FROM carrera");

        while (rs.next()) {

            carrera = new ModeloCarrera();

            carrera.setCar_cod(rs.getLong(1));
            carrera.setCar_nombre(rs.getString(2));
            carrera.setCar_duracion(rs.getInt(3));

            carreras.add(carrera);

        }

        desconectaBBDD();
        
        return carreras;
        
    }
    
    //----------ACTUALIZA CARRERA-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaCarreraDAO(ModeloCarrera carrera, String cod) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("UPDATE carrera SET car_cod=?, car_nombre=?, car_duracion=? WHERE car_cod=" + cod);

        prepStat.setLong(1, carrera.getCar_cod());
        prepStat.setString(2, carrera.getCar_nombre());
        prepStat.setInt(3, carrera.getCar_duracion());

        prepStat.execute();

        desconectaBBDD();
        
    }
    
}
