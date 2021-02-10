package modelo;

import datos.*;
import java.sql.*;
import java.util.ArrayList;

public class ModeloInscripcion {
    
    private long insc_cod;
    private long insc_dni_alumno;
    private java.sql.Date insc_fecha;
    private long insc_car_cod;
    private InscripcionDAO inscDAO = new InscripcionDAO();

    //----------GETTERS Y SETTERS--------------------------------------------------------------
    
    public long getInsc_cod() {
        return insc_cod;
    }

    public void setInsc_cod(long insc_cod) {
        this.insc_cod = insc_cod;
    }

    public long getInsc_nombre() {
        return insc_dni_alumno;
    }

    public void setDni_Alumno_Insc(long insc_nombre) {
        this.insc_dni_alumno = insc_nombre;
    }

    public java.sql.Date getInsc_fecha() {
        return insc_fecha;
    }

    public void setInsc_fecha(java.sql.Date insc_fecha) {
        this.insc_fecha = insc_fecha;
    }

    public long getInsc_car_cod() {
        return insc_car_cod;
    }

    public void setInsc_car_cod(long insc_car_cod) {
        this.insc_car_cod = insc_car_cod;
    }
    
    //---------INSERTA UNA INSCRIPCION EN LA BBDD----------------------------------------
    
    public void insertaInscModelo(ModeloInscripcion inscripcion) throws SQLException {
        
        inscDAO.insertaInscDAO(inscripcion);
        
    }
    
    //---------DEVUELVE TODAS LAS INSCRIPCIONES------------------------------------------
    
    public ArrayList<ModeloInscripcion> devuelveInscModelo() throws SQLException {
        
        ArrayList<ModeloInscripcion> inscripciones = inscDAO.devuelveInscDAO();
        
        return inscripciones;
        
    }
    
    //--------AGREGA UNA INSCRIPCION A UN ALUMNO-----------------------------------------
    
    public void inscribeModelo(long cod, String dni) throws SQLException {
        
        inscDAO.inscribeDAO(cod, dni);
        
    }
    
    //---------ELIMINA INSCRIPCION---------------------------------------------------
    
    public void eliminaInscripModelo(String cod) throws SQLException {
        
        inscDAO.eliminaInscripDAO(cod);
        
    }
    
    //----------ACTUALIZA INSCRIPCION-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaInscripcionModelo(ModeloInscripcion insc, String cod) throws SQLException {
        
        inscDAO.actualizaInscripcionDAO(insc, cod);
        
    }
    
}
