package datos;

import java.util.*;
import modelo.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class AlumnoDAO extends ConexionSQL {
    
    private ArrayList<ModeloAlumno> alumnos;
    private ModeloAlumno alumno;
    private PreparedStatement prepStat;
    private Statement stat;
    
    //----------AGREGA ALUMNO EN LA BBDD------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void insertaAlumnoDAO(ModeloAlumno alumno) throws SQLException {
        
        conectaBBDD();

        prepStat = getConexion().prepareStatement("INSERT INTO alumno (alu_dni, alu_nombre, alu_apellido, alu_fec_nac, alu_domicilio, alu_telefono, alu_insc_cod) VALUES (?,?,?,?,?,?,?)");

        prepStat.setLong(1, alumno.getDni_alu());
        prepStat.setString(2, alumno.getNombre_alu());
        prepStat.setString(3, alumno.getApellido_alu());
        prepStat.setString(6, alumno.getTel_alu());

        //------------VALIDACION PARA CAMPOS NO OBLIGATORIOS-------------------------------

        long nInsc = 0;
        java.sql.Date fecha = null;
        String domicilio = null;

        if (alumno.getInscrip_alu() != 0) {

            nInsc = alumno.getInscrip_alu();

        }

        if (alumno.getFecha_alu() != null) {

            fecha = alumno.getFecha_alu();

        }

        if (alumno.getDomicilio_alu() != null) {

            domicilio = alumno.getDomicilio_alu();

        }

        if (nInsc != 0) {

            prepStat.setLong(7, nInsc);

        }else {

            prepStat.setObject(7, null);

        }

        prepStat.setDate(4, fecha);
        prepStat.setString(5, domicilio);

        //------------------------------------------------------------------------------

        prepStat.execute();

        desconectaBBDD();
        
    }
    
    //----------ELIMINA ALUMNO---------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void eliminaAlumnoDAO(String dni) throws SQLException {
        
        conectaBBDD();
            
        prepStat = getConexion().prepareStatement("DELETE FROM alumno WHERE alu_dni=" + dni);

        prepStat.execute();

        desconectaBBDD();
            
    }
    
    //----------ACTUALIZA ALUMNO-----------------------------------------------------------------------------------------------------------------------------------------------------------
    
    public void actualizaAlumnoDAO(ModeloAlumno alumno, String dni) throws SQLException {
        
        conectaBBDD();
            
        prepStat = getConexion().prepareStatement("UPDATE alumno SET alu_dni=?, alu_nombre=?, alu_apellido=?, alu_fec_nac=?, alu_domicilio=?, alu_telefono=?, alu_insc_cod=? WHERE alu_dni=" + dni);

        prepStat.setLong(1, alumno.getDni_alu());
        prepStat.setString(2, alumno.getNombre_alu());
        prepStat.setString(3, alumno.getApellido_alu());
        prepStat.setDate(4, alumno.getFecha_alu());
        prepStat.setString(5, alumno.getDomicilio_alu());
        prepStat.setString(6, alumno.getTel_alu());
        if(alumno.getInscrip_alu()!=0) {
            prepStat.setLong(7, alumno.getInscrip_alu());
        }else {
            prepStat.setObject(7, null);
        }

        prepStat.execute();

        desconectaBBDD();
        
    }
    
    //----------DEVUELVE TODOS LOS ALUMNOS-----------------------------------------------------------------------------------------------------------------------------------------------
    
    public ArrayList<ModeloAlumno> devuelveAlumnosDAO() throws SQLException {
        
        alumnos = new ArrayList<>();
        
        conectaBBDD();

        stat = getConexion().createStatement();

        ResultSet rs = stat.executeQuery("SELECT * FROM alumno");

        while (rs.next()) {

            alumno = new ModeloAlumno();

            alumno.setDni_alu(rs.getLong(1));
            alumno.setNombre_alu(rs.getString(2));
            alumno.setApellido_alu(rs.getString(3));
            alumno.setFecha_alu(rs.getDate(4));
            alumno.setDomicilio_alu(rs.getString(5));
            alumno.setTel_alu(rs.getString(6));
            alumno.setInscrip_alu(rs.getLong(7));

            alumnos.add(alumno);

        }

        desconectaBBDD();
        
        return alumnos;
        
    }
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
}
