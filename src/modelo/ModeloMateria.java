package modelo;

import datos.*;
import java.sql.*;
import java.util.ArrayList;

public class ModeloMateria {
    
    private long mat_cod;
    private String mat_nombre;
    private long mat_prof_dni;
    private MateriaDAO materiaDAO= new MateriaDAO();
    
    //----------GETTERS Y SETTERS--------------------------------------------------------------

    public String getMat_nombre() {
        return mat_nombre;
    }

    public void setMat_nombre(String mat_nombre) {
        this.mat_nombre = mat_nombre;
    }

    public long getMat_prof_dni() {
        return mat_prof_dni;
    }

    public void setMat_prof_dni(long mat_prof_dni) {
        this.mat_prof_dni = mat_prof_dni;
    }

    public long getMat_cod() {
        return mat_cod;
    }

    public void setMat_cod(long mat_cod) {
        this.mat_cod = mat_cod;
    }
    
    //---------INSERTA UNA INSCRIPCION EN LA BBDD----------------------------------------
    
    public void insertaMateriaModelo(ModeloMateria materia) throws SQLException {
        
        materiaDAO.insertaMateriaDAO(materia);
        
    }
    
    //---------DEVUELVE TODAS LAS INSCRIPCIONES------------------------------------------
    
    public ArrayList<ModeloMateria> devuelveMateriasModelo() throws SQLException {
        
        ArrayList<ModeloMateria> materias = materiaDAO.devuelveMateriaDAO();
        
        return materias;
        
    }
    
    //---------ELIMINA INSCRIPCION---------------------------------------------------
    
    public void eliminaMateriaModelo(String cod) throws SQLException {
        
        materiaDAO.eliminaMateriaDAO(cod);
        
    }
    
    //----------ACTUALIZA INSCRIPCION-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaMateriaModelo(ModeloMateria materia, String cod) throws SQLException {
        
        materiaDAO.actualizaMateriaDAO(materia, cod);
        
    }
    
}
