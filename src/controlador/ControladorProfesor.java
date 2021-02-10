package controlador;

import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.*;

public class ControladorProfesor extends WindowAdapter implements ActionListener, MouseListener {
    
    VistaProfesor vistaProf;
    ModeloProfesor profesor;
    DefaultTableModel modeloTabla;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorProfesor(VistaProfesor vistaProf, ModeloProfesor profesor) {
        
        this.vistaProf = vistaProf;
        this.profesor = profesor;
        
        vistaProf.setVisible(true);
        
        vistaProf.addWindowListener(this);
        
        vistaProf.btnAceptModif_Prof.addActionListener(this);
        vistaProf.btnElim_Prof.addActionListener(this);
        vistaProf.btnInsertProf.addActionListener(this);
        vistaProf.btnLimpiar_Prof.addActionListener(this);
        vistaProf.btnModif_Prof.addActionListener(this);
        vistaProf.btnSalir_Prof.addActionListener(this);
        vistaProf.tablaProfesores.addMouseListener(this);
        
        vistaProf.btnAceptModif_Prof.setEnabled(false);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(vistaProf.btnInsertProf)) { // BOTON INSERTAR
            
            if (!vistaProf.jtDNI_Prof.getText().trim().isEmpty() || !vistaProf.jtNombre_Prof.getText().trim().isEmpty() || !vistaProf.jtApellido_Prof.getText().trim().isEmpty() || !vistaProf.jtTel_Prof.getText().trim().isEmpty()) {
                
                if (MetodosGenerales.validarNumero(vistaProf.jtDNI_Prof.getText())) { // VALIDACION CAMPOS NUMERICOS
                    
                    if (MetodosGenerales.validarNumero(vistaProf.jtTel_Prof.getText())) {
                
                        try {

                            //---------CREA OBJETO PROFESOR---------------------------------------------------------------------------

                            ModeloProfesor profesor = new ModeloProfesor();

                            profesor.setProf_dni(Long.parseLong(vistaProf.jtDNI_Prof.getText()));
                            profesor.setProf_nombre(vistaProf.jtNombre_Prof.getText());
                            profesor.setProf_apellido(vistaProf.jtApellido_Prof.getText());

                            //--------CONVERSION DE TIPOS DATE----------------------------------------------------------------------------

                            java.util.Date utilDate = vistaProf.jtFecha_Prof.getDate();
                            java.sql.Date sqlDate;

                            if (utilDate != null) {

                                sqlDate = new java.sql.Date(utilDate.getTime());


                            }else {

                                sqlDate = null;

                            }

                            //---------------------------------------------------------------------------------------------------------------

                            if (String.valueOf(sqlDate) != null || vistaProf.jtDomic_Prof.getText() != null) {

                                profesor.setProf_fecha_nac(sqlDate);
                                profesor.setProf_domicilio(vistaProf.jtDomic_Prof.getText());

                            }else {

                                profesor.setProf_fecha_nac(null);
                                profesor.setProf_domicilio(null);

                            }

                            profesor.setProf_telefono(vistaProf.jtTel_Prof.getText());

                            //----------------------------------------------------------------------------------------------------------

                            profesor.insertaProfesorModelo(profesor);

                            MetodosGenerales.limpiarTabla(vistaProf.tablaProfesores);

                            llenarTabla(vistaProf.tablaProfesores);

                            vistaProf.jtDNI_Prof.setText(null);
                            vistaProf.jtNombre_Prof.setText(null);
                            vistaProf.jtApellido_Prof.setText(null);
                            vistaProf.jtFecha_Prof.setDate(null);
                            vistaProf.jtDomic_Prof.setText(null);
                            vistaProf.jtTel_Prof.setText(null);

                        } catch (SQLException ex) {

                            JOptionPane.showMessageDialog(null, "DNI existente.");

                        }
                    
                    }else {
                        
                        JOptionPane.showMessageDialog(vistaProf, "No se pueden ingresar caracteres extraños en el campo teléfono.");
                        
                    }
                
                }else {
                    
                    JOptionPane.showMessageDialog(vistaProf, "No se pueden ingresar caracteres extraños en el campo DNI.");
                    
                }
                
            }else {
                
                JOptionPane.showMessageDialog(vistaProf, "Los campos con asterisco son obligatorios.");
                
            }
            
        }else if (e.getSource().equals(vistaProf.btnLimpiar_Prof)) { // BOTON LIMPIAR
            
            vistaProf.jtDNI_Prof.setText(null);
            vistaProf.jtNombre_Prof.setText(null);
            vistaProf.jtApellido_Prof.setText(null);
            vistaProf.jtFecha_Prof.setDate(null);
            vistaProf.jtDomic_Prof.setText(null);
            vistaProf.jtTel_Prof.setText(null);
            
            vistaProf.btnInsertProf.setEnabled(true);
            vistaProf.btnAceptModif_Prof.setEnabled(false);
            
        }else if (e.getSource().equals(vistaProf.btnElim_Prof)) { // BOTON ELIMINAR
            
            try {
                
                int seleccion = vistaProf.tablaProfesores.getSelectedRow();
                
                String dni = String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 0));
                
                profesor.eliminaProfesorModelo(dni);
                
                MetodosGenerales.limpiarTabla(vistaProf.tablaProfesores);
                
                llenarTabla(vistaProf.tablaProfesores);
                
            } catch (SQLException ex) {
                
                JOptionPane.showMessageDialog(null, "No puedes eliminar un profesor asociado a una materia.");
                
            }
            
        }else if (e.getSource().equals(vistaProf.btnModif_Prof)) { // BOTON MODIFICAR
            
            int seleccion = vistaProf.tablaProfesores.getSelectedRow();
            
            if (seleccion!=-1) {
            
            vistaProf.jtDNI_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 0)));
            vistaProf.jtNombre_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 1)));
            vistaProf.jtApellido_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 2)));
            vistaProf.jtFecha_Prof.setDate((java.util.Date) vistaProf.tablaProfesores.getValueAt(seleccion, 3));
            vistaProf.jtDomic_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 4)));
            vistaProf.jtTel_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 5)));
            
            vistaProf.btnInsertProf.setEnabled(false);
            
            }else {
                
                JOptionPane.showMessageDialog(vistaProf, "Seleccione un registro de la tabla.");
                
            }
            
            vistaProf.btnAceptModif_Prof.setEnabled(true);
            
        }else if (e.getSource().equals(vistaProf.btnAceptModif_Prof)) { // BOTON ACEPTAR MODIFICACION
            
            vistaProf.btnInsertProf.setEnabled(true);
            
            if (!vistaProf.jtDNI_Prof.getText().trim().isEmpty() || !vistaProf.jtNombre_Prof.getText().trim().isEmpty() || !vistaProf.jtApellido_Prof.getText().trim().isEmpty() || !vistaProf.jtTel_Prof.getText().trim().isEmpty()) {
                
                if (MetodosGenerales.validarNumero(vistaProf.jtDNI_Prof.getText())) { // VALIDACION CAMPOS NUMERICOS
                    
                    if (MetodosGenerales.validarNumero(vistaProf.jtTel_Prof.getText())) {
                
                        try {

                            profesor = new ModeloProfesor();
                            int seleccion = vistaProf.tablaProfesores.getSelectedRow();

                            //--------RELLENA OBJETO MODELOPROFESOR------------------------------------------------------------------------

                            profesor.setProf_dni(Long.parseLong(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 0))));
                            profesor.setProf_nombre(vistaProf.jtNombre_Prof.getText());
                            profesor.setProf_apellido(vistaProf.jtApellido_Prof.getText());

                            //--------CONVERSION DE TIPOS DATE----------------------------------------------------------------------------

                            java.util.Date utilDate = vistaProf.jtFecha_Prof.getDate();
                            java.sql.Date sqlDate;

                            if (utilDate != null) {

                                sqlDate = new java.sql.Date(utilDate.getTime());


                            }else {

                                sqlDate = null;

                            }

                            //---------------------------------------------------------------------------------------------------------------

                            if (String.valueOf(sqlDate) != null || vistaProf.jtDomic_Prof.getText() != null) {

                                profesor.setProf_fecha_nac(sqlDate);
                                profesor.setProf_domicilio(vistaProf.jtDomic_Prof.getText());

                            }else {

                                profesor.setProf_fecha_nac(null);
                                profesor.setProf_domicilio(null);

                            }

                            profesor.setProf_telefono(vistaProf.jtTel_Prof.getText());

                            //------------------------------------------------------------------------------------------------------------

                            profesor.actualizaProfesorModelo(profesor, String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 0)));

                            MetodosGenerales.limpiarTabla(vistaProf.tablaProfesores);

                            llenarTabla(vistaProf.tablaProfesores);

                            vistaProf.btnAceptModif_Prof.setEnabled(false);

                            vistaProf.jtDNI_Prof.setText(null);
                            vistaProf.jtNombre_Prof.setText(null);
                            vistaProf.jtApellido_Prof.setText(null);
                            vistaProf.jtFecha_Prof.setDate(null);
                            vistaProf.jtDomic_Prof.setText(null);
                            vistaProf.jtTel_Prof.setText(null);

                        } catch (SQLException ex) {

                            JOptionPane.showMessageDialog(null, "DNI existente.");
                            ex.printStackTrace();

                        }
                
                    }else {
                        
                        JOptionPane.showMessageDialog(vistaProf, "No se pueden ingresar letras ni caracteres extraños en el campo Teléfono.");
                        
                    }
                
                }else {
                    
                    JOptionPane.showMessageDialog(vistaProf, "No se pueden ingresar letras ni caracteres extraños en el campo DNI.");
                    
                }
                
            }else {
                
                JOptionPane.showMessageDialog(vistaProf, "Los campos con asterisco son obligatorios.");
                
            }
            
        }else if (e.getSource().equals(vistaProf.btnSalir_Prof)) { // BOTON SALIR
            
            vistaProf.dispose();
            
            vistaProf = null;
            profesor = null;
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
        
        vistaProf.btnModif_Prof.setEnabled(false);
        vistaProf.btnElim_Prof.setEnabled(false);
        
        try {
            
            llenarTabla(vistaProf.tablaProfesores);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    //----------LLENA LA TABLA-------------------------------------------------------------------------------------
    
    public void llenarTabla(JTable tabla) throws SQLException {
        String[] columnas = {"DNI", "Nombre", "Apellido", "Fecha Nacimiento", "Domicilio", "Telefono"};
        modeloTabla = new DefaultTableModel(null, columnas);
        ArrayList<ModeloProfesor> profesores;
        profesores = profesor.devuelveProfesorModelo();
        MetodosGenerales.limpiarTabla(vistaProf.tablaProfesores);
        Object datos[] = new Object[6];
        if (profesores.size() > 0) {
            for (int i = 0; i < profesores.size(); i++) {
                
                datos[0] = profesores.get(i).getProf_dni();
                datos[1] = profesores.get(i).getProf_nombre();
                datos[2] = profesores.get(i).getProf_apellido();
                datos[3] = profesores.get(i).getProf_fecha_nac();
                datos[4] = profesores.get(i).getProf_domicilio();
                datos[5] = profesores.get(i).getProf_telefono();
                
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        profesores.clear();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource().equals(vistaProf.tablaProfesores)) {
            
            vistaProf.btnModif_Prof.setEnabled(true);
            vistaProf.btnElim_Prof.setEnabled(true);
            
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getClickCount()==2) {
        
            if (e.getSource().equals(vistaProf.tablaProfesores)) {

                vistaProf.btnInsertProf.setEnabled(false);

                int seleccion = vistaProf.tablaProfesores.getSelectedRow();
            
            if (seleccion!=-1) {
            
            vistaProf.jtDNI_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 0)));
            vistaProf.jtNombre_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 1)));
            vistaProf.jtApellido_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 2)));
            
            //-------CONVERSION DE STRING A DATE--------------------------------------------------------------------------
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = null;
            try {
                parsed = sdf.parse(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 3)));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            java.sql.Date fecha = new java.sql.Date(parsed.getTime());
            
            //------------------------------------------------------------------------------------------------------------
            
            vistaProf.jtFecha_Prof.setDate(fecha);
            vistaProf.jtDomic_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 4)));
            vistaProf.jtTel_Prof.setText(String.valueOf(vistaProf.tablaProfesores.getValueAt(seleccion, 5)));
            
            }else {
                
                JOptionPane.showMessageDialog(vistaProf, "Seleccione un registro de la tabla.");
                
            }
            
            vistaProf.btnAceptModif_Prof.setEnabled(true);

            }
        
        }
        
    }

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
