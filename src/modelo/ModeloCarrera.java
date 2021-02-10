package modelo;

import datos.CarreraDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModeloCarrera {
    
    private long car_cod;
    private String car_nombre;
    private int car_duracion;
    private CarreraDAO carDAO = new CarreraDAO();
    
    //----------GETTERS Y SETTERS-----------------------------------------------------------

    public long getCar_cod() {
        return car_cod;
    }

    public void setCar_cod(long car_cod) {
        this.car_cod = car_cod;
    }

    public String getCar_nombre() {
        return car_nombre;
    }

    public void setCar_nombre(String car_nombre) {
        this.car_nombre = car_nombre;
    }

    public int getCar_duracion() {
        return car_duracion;
    }

    public void setCar_duracion(int car_duracion) {
        this.car_duracion = car_duracion;
    }
    
    //---------INSERTA UNA INSCRIPCION EN LA BBDD----------------------------------------
    
    public void insertaCarreraModelo(ModeloCarrera carrera) throws SQLException {
        
        carDAO.insertaCarreraDAO(carrera);
        
    }
    
    //---------DEVUELVE TODAS LAS INSCRIPCIONES------------------------------------------
    
    public ArrayList<ModeloCarrera> devuelveCarrerasModelo() throws SQLException {
        
        ArrayList<ModeloCarrera> carreras = carDAO.devuelveCarrerasDAO();
        
        return carreras;
        
    }
    
    //---------ELIMINA INSCRIPCION---------------------------------------------------
    
    public void eliminaInscripModelo(String cod) throws SQLException {
        
        carDAO.eliminaCarreraDAO(cod);
        
    }
    
    //----------ACTUALIZA INSCRIPCION-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaCarreraModelo(ModeloCarrera carrera, String cod) throws SQLException {
        
        carDAO.actualizaCarreraDAO(carrera, cod);
        
    }
    
}
