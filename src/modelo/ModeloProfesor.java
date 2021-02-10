package modelo;

import datos.*;
import java.sql.*;
import java.util.ArrayList;

public class ModeloProfesor {
    
    private long prof_dni;
    private String prof_nombre;
    private String prof_apellido;
    private java.sql.Date prof_fecha_nac;
    private String prof_domicilio;
    private String prof_telefono;
    private ProfesorDAO profesorDAO = new ProfesorDAO();
    
    //----------GETTERS Y SETTERS-----------------------------------------------------------

    public long getProf_dni() {
        return prof_dni;
    }

    public void setProf_dni(long prof_dni) {
        this.prof_dni = prof_dni;
    }

    public String getProf_nombre() {
        return prof_nombre;
    }

    public void setProf_nombre(String prof_nombre) {
        this.prof_nombre = prof_nombre;
    }

    public String getProf_apellido() {
        return prof_apellido;
    }

    public void setProf_apellido(String prof_apellido) {
        this.prof_apellido = prof_apellido;
    }

    public Date getProf_fecha_nac() {
        return prof_fecha_nac;
    }

    public void setProf_fecha_nac(Date prof_fecha_nac) {
        this.prof_fecha_nac = prof_fecha_nac;
    }

    public String getProf_domicilio() {
        return prof_domicilio;
    }

    public void setProf_domicilio(String prof_domicilio) {
        this.prof_domicilio = prof_domicilio;
    }

    public String getProf_telefono() {
        return prof_telefono;
    }

    public void setProf_telefono(String prof_telefono) {
        this.prof_telefono = prof_telefono;
    }
    
    //---------INSERTA UNA PROFESOR EN LA BBDD----------------------------------------
    
    public void insertaProfesorModelo(ModeloProfesor profesor) throws SQLException {
        
        profesorDAO.insertaProfesorDAO(profesor);
        
    }
    
    //---------DEVUELVE TODOS LOS PROFESORES------------------------------------------
    
    public ArrayList<ModeloProfesor> devuelveProfesorModelo() throws SQLException {
        
        ArrayList<ModeloProfesor> profesores = profesorDAO.devuelveProfesorDAO();
        
        return profesores;
        
    }
    
    //---------ELIMINA PROFESOR---------------------------------------------------
    
    public void eliminaProfesorModelo(String id) throws SQLException {
        
        profesorDAO.eliminaProfesorDAO(id);
        
    }
    
    //----------ACTUALIZA PROFESOR-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaProfesorModelo(ModeloProfesor profesor, String cod) throws SQLException {
        
        profesorDAO.actualizaProfesorDAO(profesor, cod);
        
    }
    
}
