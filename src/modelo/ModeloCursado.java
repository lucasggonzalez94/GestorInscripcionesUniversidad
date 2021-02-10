package modelo;

import datos.CursadoDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModeloCursado {
    
    private long cur_alu_dni;
    private long cur_mat_cod;
    private int cur_nota;
    private CursadoDAO curDAO = new CursadoDAO();
    
    //----------GETTERS Y SETTERS-----------------------------------------------------------

    public long getCur_alu_dni() {
        return cur_alu_dni;
    }

    public void setCur_alu_dni(long cur_alu_dni) {
        this.cur_alu_dni = cur_alu_dni;
    }

    public long getCur_mat_cod() {
        return cur_mat_cod;
    }

    public void setCur_mat_cod(long cur_mat_cod) {
        this.cur_mat_cod = cur_mat_cod;
    }

    public int getCur_nota() {
        return cur_nota;
    }

    public void setCur_nota(int cur_nota) {
        this.cur_nota = cur_nota;
    }
    
    //---------INSERTA UN CURSADO EN LA BBDD----------------------------------------
    
    public void insertaCursadoModelo(ModeloCursado cursado) throws SQLException {
        
        curDAO.insertaCursadoDAO(cursado);
        
    }
    
    //---------DEVUELVE TODOS LOS CURSADO------------------------------------------
    
    public ArrayList<ModeloCursado> devuelveCursadoModelo() throws SQLException {
        
        ArrayList<ModeloCursado> cursados = curDAO.devuelveCursadoDAO();
        
        return cursados;
        
    }
    
    //---------ELIMINA CURSADO---------------------------------------------------
    
    public void eliminaCursadoModelo(String dni, String cod) throws SQLException {
        
        curDAO.eliminaCursadoDAO(dni, cod);
        
    }
    
    //----------ACTUALIZA CURSADO-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaCursadoModelo(ModeloCursado cursado, String dni, String cod) throws SQLException {
        
        curDAO.actualizaCursadoDAO(cursado, dni, cod);
        
    }
    
}
