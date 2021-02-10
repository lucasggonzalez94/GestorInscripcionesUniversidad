package controlador;

import vista.*;
import modelo.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorAlumno extends WindowAdapter implements ActionListener, MouseListener{
    
    @SuppressWarnings("FieldMayBeFinal")
    private VistaAlumno vistaAlumno;
    private ModeloAlumno alumno;
    private ModeloInscripcion inscrip = new ModeloInscripcion();
    private DefaultTableModel modeloTabla;
    private long codInsc = 0; //SE UTILIZA PARA MODIFICAR EL ALUMNO Y QUE NO SE BORRE EL CODIGO DE INSCRIPCION
    
    public ControladorAlumno(VistaAlumno vistaAlumno, ModeloAlumno alumno) {
        
        this.vistaAlumno=vistaAlumno;
        this.alumno=alumno;
        
        vistaAlumno.setVisible(true);
        
        vistaAlumno.btnElim_Alu.addActionListener(this);
        vistaAlumno.btnInsert_Alu.addActionListener(this);
        vistaAlumno.btnInscribir_Alu.addActionListener(this);
        vistaAlumno.btnLimpiar_Alu.addActionListener(this);
        vistaAlumno.btnModif_Alu.addActionListener(this);
        vistaAlumno.btnSalir_Alu.addActionListener(this);
        vistaAlumno.btnAceptar_Alu.addActionListener(this);
        vistaAlumno.btnAceptar_Alu.setEnabled(false);
        vistaAlumno.tablaAlumnos.addMouseListener(this);
        
        vistaAlumno.addWindowListener(this);
        
    }
    
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource().equals(vistaAlumno.btnInsert_Alu)) { // BOTON INSERTAR
            
            //-----------VALIDACION CAMPOS VACIOS------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            
            if (vistaAlumno.jtDni_Alu.getText().trim().isEmpty() || vistaAlumno.jtNombre_Alu.getText().trim().isEmpty() || vistaAlumno.jtApellido_Alu.getText().trim().isEmpty() || vistaAlumno.jtTel_Alu.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(vistaAlumno, "Los campos con asterisco son obligatorios.");
            
            //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                
            }else {
            
                if (MetodosGenerales.validarNumero(vistaAlumno.jtDni_Alu.getText())) {
                    
                    if (MetodosGenerales.validarNumero(vistaAlumno.jtTel_Alu.getText())) {
                    
                        try {

                            // VALIDACION CAMPOS NUMERICOS

                            //---------TOMA DATOS DE LOS CAMPOS Y CREA OBJETO DE TIPO ALUMNO--------------------------------------

                            alumno=new ModeloAlumno();

                            alumno.setDni_alu(Long.parseLong(vistaAlumno.jtDni_Alu.getText()));
                            alumno.setNombre_alu(vistaAlumno.jtNombre_Alu.getText());
                            alumno.setApellido_alu(vistaAlumno.jtApellido_Alu.getText());
                            alumno.setTel_alu(vistaAlumno.jtTel_Alu.getText());

                            //-------CONVERSION DE TIPOS DATE----------------------------------------------------------------
                            java.util.Date utilDate = vistaAlumno.jtFecha_Alu.getDate();
                            java.sql.Date sqlDate;

                            //--------VALIDACION CAMPOS NO OBLIGATORIOS-----------------------------------------------------

                            if (utilDate != null) {

                                sqlDate = new java.sql.Date(utilDate.getTime());


                            }else {

                                sqlDate = null;

                            }

                            if (String.valueOf(sqlDate) != null || vistaAlumno.jtDomicilio_Alu.getText() != null) {

                                alumno.setFecha_alu(sqlDate);
                                alumno.setDomicilio_alu(vistaAlumno.jtDomicilio_Alu.getText());

                            }else {

                                alumno.setFecha_alu(null);
                                alumno.setTel_alu(null);

                            }

                            //------------------------------------------------------------------------------------------

                            alumno.insertaAlumnoModelo(alumno);

                            MetodosGenerales.limpiarTabla(vistaAlumno.tablaAlumnos);

                            llenarTabla(vistaAlumno.tablaAlumnos);

                            vistaAlumno.jtApellido_Alu.setText(null);
                            vistaAlumno.jtDni_Alu.setText(null);
                            vistaAlumno.jtDomicilio_Alu.setText(null);
                            vistaAlumno.jtFecha_Alu.setDate(null);
                            vistaAlumno.jtNombre_Alu.setText(null);
                            vistaAlumno.jtTel_Alu.setText(null);

                        } catch (SQLException ex) {

                            JOptionPane.showMessageDialog(null, "Ya existe un alumno con el mismo DNI.");

                        }
                    
                    }else {
                        
                        JOptionPane.showMessageDialog(vistaAlumno, "No se pueden ingresar letras ni caracteres extraños en el campo Teléfono.");
                        
                    }
                
                }else {
                    
                    JOptionPane.showMessageDialog(vistaAlumno, "No se pueden ingresar letras ni caracteres extraños en el campo DNI.");
                    
                }
            
            }
            
        }else if (e.getSource().equals(vistaAlumno.btnInscribir_Alu)) { // BOTON INSCRIBIR
            
            VistaInscripcion vInsc = new VistaInscripcion();
                
            inscrip = new ModeloInscripcion();
                
            ControladorInscripcion cInsc = new ControladorInscripcion(vInsc, inscrip);
            
            vistaAlumno.btnInscribir_Alu.setEnabled(false);
            
        }else if (e.getSource().equals(vistaAlumno.btnLimpiar_Alu)) { // BOTON LIMPIAR REINICIA TODOS LOS CAMPOS
            
            vistaAlumno.jtApellido_Alu.setText(null);
            vistaAlumno.jtDni_Alu.setText(null);
            vistaAlumno.jtDomicilio_Alu.setText(null);
            vistaAlumno.jtFecha_Alu.setDate(null);
            vistaAlumno.jtNombre_Alu.setText(null);
            vistaAlumno.jtTel_Alu.setText(null);
            
            vistaAlumno.btnInsert_Alu.setEnabled(true);
            vistaAlumno.btnAceptar_Alu.setEnabled(false);
            
        }else if (e.getSource().equals(vistaAlumno.btnModif_Alu)) { // BOTON MODIFICAR
            
            int seleccion = vistaAlumno.tablaAlumnos.getSelectedRow();
            
            if (seleccion!=-1) {
            
                vistaAlumno.jtDni_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 0)));
                vistaAlumno.jtNombre_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 1)));
                vistaAlumno.jtApellido_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 2)));
                vistaAlumno.jtFecha_Alu.setDate((java.util.Date) vistaAlumno.tablaAlumnos.getValueAt(seleccion, 3));
                vistaAlumno.jtDomicilio_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 4)));
                vistaAlumno.jtTel_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 5)));
                
                vistaAlumno.btnInsert_Alu.setEnabled(false);
                vistaAlumno.btnAceptar_Alu.setEnabled(true);
            
            }else {
                
                JOptionPane.showMessageDialog(vistaAlumno, "Seleccione un registro de la tabla.");
                
            }
            
        }else if (e.getSource().equals(vistaAlumno.btnAceptar_Alu)) { // BOTON ACEPTAR MODIFICACION
            
            if (vistaAlumno.jtDni_Alu.getText().trim().isEmpty() || vistaAlumno.jtNombre_Alu.getText().trim().isEmpty() || vistaAlumno.jtApellido_Alu.getText().trim().isEmpty() || vistaAlumno.jtTel_Alu.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(vistaAlumno, "Los campos con asterisco son obligatorios.");
            
            //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                
            }else {
            
                if (MetodosGenerales.validarNumero(vistaAlumno.jtDni_Alu.getText())) {
                    
                    if (MetodosGenerales.validarNumero(vistaAlumno.jtTel_Alu.getText())) {
                    
                        try {

                            // VALIDACION CAMPOS NUMERICOS

                            //---------CREO OBJETO MODELOALUMNO E INDICO EL REGISTRO SELECCIONADO-------------------------------------------

                            alumno = new ModeloAlumno();
                            int seleccion = vistaAlumno.tablaAlumnos.getSelectedRow();

                            //---------RELLENA OBJETO MODELOALUMNO---------------------------------------------------------------------

                            alumno.setDni_alu(Long.parseLong(vistaAlumno.jtDni_Alu.getText()));
                            alumno.setNombre_alu(vistaAlumno.jtNombre_Alu.getText());
                            alumno.setApellido_alu(vistaAlumno.jtApellido_Alu.getText());
                            alumno.setTel_alu(vistaAlumno.jtTel_Alu.getText());
                            alumno.setInscrip_alu(codInsc);

                            //-------CONVERSION DE TIPOS DATE-------------------------------------------------------------------------

                            java.util.Date utilDate = vistaAlumno.jtFecha_Alu.getDate();
                            java.sql.Date sqlDate;

                            //--------VALIDACION CAMPOS NO OBLIGATORIOS-----------------------------------------------------

                            if (utilDate != null) {

                                sqlDate = new java.sql.Date(utilDate.getTime());


                            }else {

                                sqlDate = null;

                            }

                            if (String.valueOf(sqlDate) != null || vistaAlumno.jtDomicilio_Alu.getText() != null) {

                                alumno.setFecha_alu(sqlDate);
                                alumno.setDomicilio_alu(vistaAlumno.jtDomicilio_Alu.getText());

                            }else {

                                alumno.setFecha_alu(null);
                                alumno.setTel_alu(null);

                            }

                            //--------------------------------------------------------------------------------------------------------------

                            alumno.actualizaAlumnoModelo(alumno, String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 0)));

                            MetodosGenerales.limpiarTabla(vistaAlumno.tablaAlumnos);

                            llenarTabla(vistaAlumno.tablaAlumnos);

                            //---------LIMPIA CAMPOS------------------------------------------------------------------------------------

                            vistaAlumno.jtApellido_Alu.setText(null);
                            vistaAlumno.jtDni_Alu.setText(null);
                            vistaAlumno.jtDomicilio_Alu.setText(null);
                            vistaAlumno.jtFecha_Alu.setDate(null);
                            vistaAlumno.jtNombre_Alu.setText(null);
                            vistaAlumno.jtTel_Alu.setText(null);

                            //-----------------------------------------------------------------------------------------------------------

                            vistaAlumno.btnInsert_Alu.setEnabled(true);
                            vistaAlumno.btnAceptar_Alu.setEnabled(false);

                        } catch (SQLException ex) {

                            ex.printStackTrace();

                        }
                    
                    }else {
                        
                        JOptionPane.showMessageDialog(vistaAlumno, "No se pueden ingresar letras ni caracteres extraños en el campo Teléfono.");
                        
                    }
                
                }else {
                    
                    JOptionPane.showMessageDialog(vistaAlumno, "No se pueden ingresar letras ni caracteres extraños en el campo DNI.");
                    
                }
                
            }
            
        }else if (e.getSource().equals(vistaAlumno.btnElim_Alu)) { // BOTON ELIMINAR
            
            try {
                
                int seleccion = vistaAlumno.tablaAlumnos.getSelectedRow();

                String dni = String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 0));

                alumno.eliminaAlumnoModelo(dni);

                MetodosGenerales.limpiarTabla(vistaAlumno.tablaAlumnos);

                llenarTabla(vistaAlumno.tablaAlumnos);
            
            } catch (SQLException ex) {
                
                ex.printStackTrace();
                
            }
            
        }else if (e.getSource().equals(vistaAlumno.btnSalir_Alu)) { // BOTON SALIR
            
            vistaAlumno.dispose();
            
            vistaAlumno = null;
            alumno = null;
            inscrip = null;
            modeloTabla = null;
            try {
                this.finalize();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            
        }
        
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
        
        vistaAlumno.btnModif_Alu.setEnabled(false);
        vistaAlumno.btnElim_Alu.setEnabled(false);
        
        //-----------------RELLENA LA TABLA------------------------------------------------------------------------------------------

        llenarTabla(vistaAlumno.tablaAlumnos);
            
        //---------------------------------------------------------------------------------------------------------------------------
                
    }
    
    //-----------ACTUALIZAR TABLA CUANDO SE INGRESA NUEVAMENTE A LA VENTANA----------------------------------------------------------
    
    @Override
    public void windowActivated(WindowEvent e) {
        
        MetodosGenerales.limpiarTabla(vistaAlumno.tablaAlumnos);
        llenarTabla(vistaAlumno.tablaAlumnos);
        
    }
    
    //-------------INGRESA DATOS EN LA TABLA----------------------------------------------------------------------------------------
    
    @SuppressWarnings("CallToPrintStackTrace")
    public void llenarTabla(JTable tabla) {
        try {
            
            String[] columnas = {"DNI", "Nombre", "Apellido","Fecha Nac", "Domicilio", "Telefono", "Cod Insc"};
            
            modeloTabla = new DefaultTableModel(null, columnas);
            
            ArrayList<ModeloAlumno> alumnos;
            alumnos = alumno.devuelveAlumnosModelo();
            
            MetodosGenerales.limpiarTabla(vistaAlumno.tablaAlumnos);
            
            Object datos[] = new Object[7];
            
            if (alumnos.size() > 0) {
                
                for (int i = 0; i < alumnos.size(); i++) {
                    
                    datos[0] = alumnos.get(i).getDni_alu();
                    datos[1] = alumnos.get(i).getNombre_alu();
                    datos[2] = alumnos.get(i).getApellido_alu();
                    datos[3] = alumnos.get(i).getFecha_alu();
                    datos[4] = alumnos.get(i).getDomicilio_alu();
                    datos[5] = alumnos.get(i).getTel_alu();
                    
                    if (alumnos.get(i).getInscrip_alu()==0) {
                        
                        datos[6] = null;
                        
                    }else {
                        
                        datos[6] = alumnos.get(i).getInscrip_alu();
                        
                    }
                    
                    modeloTabla.addRow(datos);
                    
                }
                
            }
            
            tabla.setModel(modeloTabla);
            alumnos.clear();
            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            
        }

    }
    
    //----------ACTIVA BOTONES NECESARIOS AL HACER CLICK EN LA TABLA--------------------------------------------------------------------

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource().equals(vistaAlumno.tablaAlumnos)) {
            
            int seleccion = vistaAlumno.tablaAlumnos.getSelectedRow();
            
            vistaAlumno.btnModif_Alu.setEnabled(true);
            vistaAlumno.btnElim_Alu.setEnabled(true);
            vistaAlumno.btnInscribir_Alu.setEnabled(true);
            
        }
        
    }
    
    //-----------AL HACER DOBLE CLICK EN UN REGISTRO SE ACTIVA LA MODIFICACION---------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getClickCount()==2) {
        
            if (e.getSource().equals(vistaAlumno.tablaAlumnos)) {

                vistaAlumno.btnInsert_Alu.setEnabled(false);

                int seleccion = vistaAlumno.tablaAlumnos.getSelectedRow();

                if (seleccion!=-1) {

                    vistaAlumno.jtDni_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 0)));
                    vistaAlumno.jtNombre_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 1)));
                    vistaAlumno.jtApellido_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 2)));
                    vistaAlumno.jtFecha_Alu.setDate((java.util.Date) vistaAlumno.tablaAlumnos.getValueAt(seleccion, 3));
                    vistaAlumno.jtDomicilio_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 4)));
                    vistaAlumno.jtTel_Alu.setText(String.valueOf(vistaAlumno.tablaAlumnos.getValueAt(seleccion, 5)));
                    
                    vistaAlumno.btnAceptar_Alu.setEnabled(true);

                }

            }
        
        }
        
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
