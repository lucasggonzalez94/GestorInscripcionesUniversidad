package modelo;

import datos.*;
import java.sql.SQLException;
import java.util.*;

public class ModeloAlumno {
    
    private long dni_alu;
    private String nombre_alu;
    private String apellido_alu;
    private java.sql.Date fecha_alu;
    private String domicilio_alu;
    private String tel_alu;
    private long inscrip_alu;
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    
    //---------GETTERS Y SETTERS------------------------------------------------------

    public long getDni_alu() {
        return dni_alu;
    }

    public void setDni_alu(long dni_alu) {
        this.dni_alu = dni_alu;
    }

    public String getNombre_alu() {
        return nombre_alu;
    }

    public void setNombre_alu(String nombre_alu) {
        this.nombre_alu = nombre_alu;
    }

    public String getApellido_alu() {
        return apellido_alu;
    }

    public void setApellido_alu(String apellido_alu) {
        this.apellido_alu = apellido_alu;
    }

    public java.sql.Date getFecha_alu() {
        return fecha_alu;
    }

    public void setFecha_alu(java.sql.Date fecha_alu) {
        this.fecha_alu = fecha_alu;
    }

    public String getDomicilio_alu() {
        return domicilio_alu;
    }

    public void setDomicilio_alu(String domicilio_alu) {
        this.domicilio_alu = domicilio_alu;
    }

    public String getTel_alu() {
        return tel_alu;
    }

    public void setTel_alu(String tel_alu) {
        this.tel_alu = tel_alu;
    }

    public long getInscrip_alu() {
        return inscrip_alu;
    }

    public void setInscrip_alu(long inscrip_alu) {
        this.inscrip_alu = inscrip_alu;
    }
    
    //----------INSERTA ALUMNO EN LA BBDD-----------------------------------------
    
    public void insertaAlumnoModelo(ModeloAlumno alumno) throws SQLException {
        
        alumnoDAO.insertaAlumnoDAO(alumno);
        
    }
    
    //---------ELIMINA UN ALUMNO---------------------------------------------------
    
    public void eliminaAlumnoModelo(String dni) throws SQLException {
        
        alumnoDAO.eliminaAlumnoDAO(dni);
        
    }
    
    //---------ACTUALIZA ALUMNO----------------------------------------------------
    
    public void actualizaAlumnoModelo(ModeloAlumno alumno, String dni) throws SQLException {
        
        alumnoDAO.actualizaAlumnoDAO(alumno, dni);
        
    }
    
    //----------DEVUELVE TODOS LOS ALUMNOS-----------------------------------------
    
    public ArrayList<ModeloAlumno> devuelveAlumnosModelo() throws SQLException {
        
        ArrayList<ModeloAlumno> alumnos = alumnoDAO.devuelveAlumnosDAO();
        
        return alumnos;
        
    }
    
    //-----------------------------------------------------------------------------
    
}
