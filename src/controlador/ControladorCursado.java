package controlador;

import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.*;

public class ControladorCursado extends WindowAdapter implements ActionListener, MouseListener{
    
    VistaCursado vistaCursado;
    ModeloCursado cursado;
    ModeloAlumno alumno;
    ModeloMateria materia;
    DefaultTableModel modeloTabla;
    
    public ControladorCursado(VistaCursado vistaCursado, ModeloCursado cursado) {
        
        this.vistaCursado = vistaCursado;
        this.cursado = cursado;
        
        vistaCursado.setVisible(true);
        
        vistaCursado.addWindowListener(this);
        vistaCursado.btnAceptModif_Curs.addActionListener(this);
        vistaCursado.btnElim_Curs.addActionListener(this);
        vistaCursado.btnInsert_Curs.addActionListener(this);
        vistaCursado.btnLimpiar_Curs.addActionListener(this);
        vistaCursado.btnModif_Curs.addActionListener(this);
        vistaCursado.btnSalir_Curs.addActionListener(this);
        vistaCursado.tablaCursado.addMouseListener(this);
        
        vistaCursado.btnAceptModif_Curs.setEnabled(false);
        
    }
    
    //----------LLENA LA TABLA-------------------------------------------------------------------------------------
    
    public void llenarTabla(JTable tabla) throws SQLException {
        String[] columnas = {"DNI Alumno", "Codigo Materia","Nota"};
        modeloTabla = new DefaultTableModel(null, columnas);
        ArrayList<ModeloCursado> cursados;
        cursados = cursado.devuelveCursadoModelo();
        MetodosGenerales.limpiarTabla(vistaCursado.tablaCursado);
        Object datos[] = new Object[3];
        if (cursados.size() > 0) {
            for (int i = 0; i < cursados.size(); i++) {
                datos[0] = cursados.get(i).getCur_alu_dni();
                datos[1] = cursados.get(i).getCur_mat_cod();
                datos[2] = cursados.get(i).getCur_nota();
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        cursados.clear();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vistaCursado.btnInsert_Curs)) { // BOTON INSERTAR
            
            if (vistaCursado.cb_DNIAlu_Curs.getSelectedItem() != null || vistaCursado.cb_CodMat_Curs.getSelectedItem() != null) {
                
                int nota = 0;
                
                if (!vistaCursado.jtNota_Curs.getText().equals("")) {
                    
                    nota = Integer.parseInt(vistaCursado.jtNota_Curs.getText());
                    
                }
                
                if (MetodosGenerales.validarNumero(String.valueOf(nota))) { // VALIDACION CAMPOS NUMERICOS
                
                    try {

                        //--------CREAB OBJETO MODELOCURSADO-----------------------------------------------------------------------

                        cursado = new ModeloCursado();
                        
                        //----------SEPARO CODIGO DE ALUMNO-------------------------------------------------------------------------------

                        String itemSeleccionado = String.valueOf(vistaCursado.cb_DNIAlu_Curs.getSelectedItem());
                        
                        if (itemSeleccionado.equals("null")) {
                            
                            JOptionPane.showMessageDialog(vistaCursado, "Elija un alumno.");
                            
                            return;
                            
                        }

                        String [] partes = itemSeleccionado.split("-");

                        long dni = Long.parseLong(partes[0]);

                        //---------------------------------------------------------------------------------------------------------------

                        cursado.setCur_alu_dni(dni);

                        //----------SEPARO CODIGO DE MATERIA-------------------------------------------------------------------------------

                        itemSeleccionado = String.valueOf(vistaCursado.cb_CodMat_Curs.getSelectedItem());

                        partes = itemSeleccionado.split("-");

                        long cod = Long.parseLong(partes[0]);

                        //---------------------------------------------------------------------------------------------------------------
                        
                        ArrayList<ModeloCursado> cursados = cursado.devuelveCursadoModelo();
                        
                        for (int i = 0; i < cursados.size(); i++) {
                            
                            if (dni == cursados.get(i).getCur_alu_dni() && cod == cursados.get(i).getCur_mat_cod()) {
                                
                                JOptionPane.showMessageDialog(vistaCursado, "Ya existe un cursado con el alumno y materia seleccionados.");
                                
                                return;
                                
                            }
                            
                        }

                        cursado.setCur_mat_cod(cod);
                        
                        cursado.setCur_nota(nota);

                        cursado.insertaCursadoModelo(cursado);

                        MetodosGenerales.limpiarTabla(vistaCursado.tablaCursado);

                        llenarTabla(vistaCursado.tablaCursado);
                        
                        vistaCursado.cb_DNIAlu_Curs.setSelectedIndex(0);
                        vistaCursado.cb_CodMat_Curs.setSelectedIndex(0);
                        vistaCursado.jtNota_Curs.setText(null);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                
                }else {
                    
                    JOptionPane.showMessageDialog(vistaCursado, "No se pueden ingresar caracteres extraños en el campo nota.");
                    
                }
                
            }else {
                
                JOptionPane.showMessageDialog(vistaCursado, "Los campos con asterisco son obligatorios.");
                
            }
            
        }else if (e.getSource().equals(vistaCursado.btnLimpiar_Curs)) { // BOTON LIMPIAR
            
            vistaCursado.cb_DNIAlu_Curs.setSelectedIndex(0);
            vistaCursado.cb_CodMat_Curs.setSelectedIndex(0);
            vistaCursado.jtNota_Curs.setText(null);
            
            vistaCursado.btnAceptModif_Curs.setEnabled(false);
            vistaCursado.btnInsert_Curs.setEnabled(true);
            
        }else if (e.getSource().equals(vistaCursado.btnElim_Curs)) { // BOTON ELIMINAR
            
            try {
                
                int seleccion = vistaCursado.tablaCursado.getSelectedRow();
                
                String dni = String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 0));
                String cod = String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 1));
                
                cursado.eliminaCursadoModelo(dni, cod);
                
                MetodosGenerales.limpiarTabla(vistaCursado.tablaCursado);
                
                llenarTabla(vistaCursado.tablaCursado);
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        }else if (e.getSource().equals(vistaCursado.btnModif_Curs)) { // BOTON MODIFICAR
            
            //----------SELECCIONA ALUMNO EN EL COMBOBOX-------------------------------------------------------------------------------------------------
            
            int seleccion = vistaCursado.tablaCursado.getSelectedRow();
            
            if (seleccion!=-1) {
            
                try {
                    
                    alumno = new ModeloAlumno();
                    
                    ArrayList<ModeloAlumno> alumnos = alumno.devuelveAlumnosModelo();
                    
                    for (int i = 0; i < alumnos.size(); i++) {
                        
                        if (String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 0)).equals(String.valueOf(alumnos.get(i).getDni_alu()))) {
                            
                            alumno = alumnos.get(i);
                            
                        }
                        
                    }
                    
                    String itemAlumno = String.valueOf(alumno.getDni_alu() + "-" + alumno.getNombre_alu() + " " + alumno.getApellido_alu());
                    
                    //----------SELECCIONA MATERIA EN EL COMBOBOX-------------------------------------------------------------------------------------------------
                    
                    materia = new ModeloMateria();
                    
                    ArrayList<ModeloMateria> materias = materia.devuelveMateriasModelo();
                    
                    for (int i = 0; i < materias.size(); i++) {
                        
                        if (String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 1)).equals(String.valueOf(materias.get(i).getMat_cod()))) {
                            
                            materia = materias.get(i);
                            
                        }
                        
                    }
                    
                    String itemMateria = String.valueOf(materia.getMat_cod() + "-" + materia.getMat_nombre());
                    
                    //------------------------------------------------------------------------------------------------------------------------------------
                    
                    vistaCursado.cb_DNIAlu_Curs.setSelectedItem(itemAlumno);
                    vistaCursado.cb_CodMat_Curs.setSelectedItem(itemMateria);
                    vistaCursado.jtNota_Curs.setText(String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 2)));
                    
                    vistaCursado.btnAceptModif_Curs.setEnabled(true);
                    vistaCursado.btnInsert_Curs.setEnabled(false);
                    
                } catch (SQLException ex) {
                    
                    ex.printStackTrace();
                    
                }
            
            }else {
                
                JOptionPane.showMessageDialog(vistaCursado, "Seleccione un registro de la tabla.");
                
            }
            
        }else if (e.getSource().equals(vistaCursado.btnAceptModif_Curs)) { // BOTON ACEPTAR MODIFICACION
            
            if (vistaCursado.cb_DNIAlu_Curs.getSelectedItem() != null || vistaCursado.cb_CodMat_Curs.getSelectedItem() != null) {
                
                if (MetodosGenerales.validarNumero(vistaCursado.jtNota_Curs.getText())) { // VALIDACION CAMPOS NUMERICOS
                
                    try {

                        cursado = new ModeloCursado();
                        int seleccion = vistaCursado.tablaCursado.getSelectedRow();

                        //--------RELLENA OBJETO MODELOINSCRIPCION------------------------------------------------------------------------

                        //----------SEPARO CODIGO DE ALUMNO-------------------------------------------------------------------------------

                        String itemSeleccionado = String.valueOf(vistaCursado.cb_DNIAlu_Curs.getSelectedItem());

                        String [] partes = itemSeleccionado.split("-");

                        long dni = Long.parseLong(partes[0]);

                        //---------------------------------------------------------------------------------------------------------------

                        cursado.setCur_alu_dni(dni);

                        //----------SEPARO CODIGO DE MATERIA-------------------------------------------------------------------------------

                        itemSeleccionado = String.valueOf(vistaCursado.cb_CodMat_Curs.getSelectedItem());

                        partes = itemSeleccionado.split("-");

                        long cod = Long.parseLong(partes[0]);

                        //---------------------------------------------------------------------------------------------------------------

                        cursado.setCur_mat_cod(cod);
                        
                        if (vistaCursado.jtNota_Curs.getText() != null) {
                        
                            cursado.setCur_nota(Integer.parseInt(vistaCursado.jtNota_Curs.getText()));
                        
                        }else {
                            
                            cursado.setCur_nota(0);
                            
                        }

                        //----------------------------------------------------------------------------------------------------------------

                        cursado.actualizaCursadoModelo(cursado, String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 0)), String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 1)));

                        MetodosGenerales.limpiarTabla(vistaCursado.tablaCursado);

                        llenarTabla(vistaCursado.tablaCursado);
                        
                        vistaCursado.btnAceptModif_Curs.setEnabled(false);
                        vistaCursado.btnInsert_Curs.setEnabled(true);
                        
                        vistaCursado.cb_DNIAlu_Curs.setSelectedIndex(0);
                        vistaCursado.cb_CodMat_Curs.setSelectedIndex(0);
                        vistaCursado.jtNota_Curs.setText(null);

                    } catch (SQLException ex) {
                        
                        ex.printStackTrace();
                        
                    }
                
                }else {
                    
                    JOptionPane.showMessageDialog(vistaCursado, "No se pueden ingresar caracteres extraños en el campo nota.");
                    
                }
                
            }else {
                
                JOptionPane.showMessageDialog(vistaCursado, "Los campos con asterisco son obligatorios.");
                
            }
            
        }else if (e.getSource().equals(vistaCursado.btnSalir_Curs)) { // BOTON SALIR
            
            vistaCursado.dispose();
            
            vistaCursado = null;
            cursado = null;
            alumno = null;
            materia = null;
            modeloTabla = null;
            try {
                this.finalize();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource().equals(vistaCursado.tablaCursado)) {
            
            vistaCursado.btnModif_Curs.setEnabled(true);
            vistaCursado.btnElim_Curs.setEnabled(true);
            
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getClickCount()==2) {
        
            if (e.getSource().equals(vistaCursado.tablaCursado)) {

                try {
                    
                    vistaCursado.btnInsert_Curs.setEnabled(false);
                    
                    //----------SELECCIONA ALUMNO EN EL COMBOBOX-------------------------------------------------------------------------------------------------
                    
                    int seleccion = vistaCursado.tablaCursado.getSelectedRow();
                    
                    alumno = new ModeloAlumno();
                    
                    ArrayList<ModeloAlumno> alumnos = alumno.devuelveAlumnosModelo();
                    
                    for (int i = 0; i < alumnos.size(); i++) {
                        
                        if (String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 0)).equals(String.valueOf(alumnos.get(i).getDni_alu()))) {
                            
                            alumno = alumnos.get(i);
                            
                        }
                        
                    }
                    
                    String itemAlumno = String.valueOf(alumno.getDni_alu() + "-" + alumno.getNombre_alu() + " " + alumno.getApellido_alu());
                    
                    //----------SELECCIONA MATERIA EN EL COMBOBOX-------------------------------------------------------------------------------------------------
                    
                    materia = new ModeloMateria();
                    
                    ArrayList<ModeloMateria> materias = materia.devuelveMateriasModelo();
                    
                    for (int i = 0; i < materias.size(); i++) {
                        
                        if (String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 1)).equals(String.valueOf(materias.get(i).getMat_cod()))) {
                            
                            materia = materias.get(i);
                            
                        }
                        
                    }
                    
                    String itemMateria = String.valueOf(materia.getMat_cod() + "-" + materia.getMat_nombre());
                    
                    //------------------------------------------------------------------------------------------------------------------------------------
                    
                    vistaCursado.cb_DNIAlu_Curs.setSelectedItem(itemAlumno);
                    vistaCursado.cb_CodMat_Curs.setSelectedItem(itemMateria);
                    vistaCursado.jtNota_Curs.setText(String.valueOf(vistaCursado.tablaCursado.getValueAt(seleccion, 2)));
                    
                    vistaCursado.btnAceptModif_Curs.setEnabled(true);
                    
                } catch (SQLException ex) {
                    
                    ex.printStackTrace();
                    
                }

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
    
    @Override
    public void windowOpened(WindowEvent e) {
        
        vistaCursado.btnModif_Curs.setEnabled(false);
        vistaCursado.btnElim_Curs.setEnabled(false);
        
        vistaCursado.cb_CodMat_Curs.removeAllItems();
        vistaCursado.cb_DNIAlu_Curs.removeAllItems();
        
        try {
            
            //-----------------RELLENA EL COMBOBOX DE ALUMNOS---------------------------------------------------------------------------------------
            
            alumno = new ModeloAlumno();
            
            ArrayList<ModeloAlumno> alumnos = alumno.devuelveAlumnosModelo();
            
            for (int i = 0; i < alumnos.size(); i++) {
                
                vistaCursado.cb_DNIAlu_Curs.addItem(alumnos.get(i).getDni_alu() + "-" + alumnos.get(i).getNombre_alu() + " " + alumnos.get(i).getApellido_alu());
                
            }
            
            vistaCursado.cb_DNIAlu_Curs.setSelectedItem(null);
            
            //-------------RELLENA EL COMBOBOX DE MATERIAS-----------------------------------------------------------------------------
            
            materia = new ModeloMateria();
            
            ArrayList<ModeloMateria> materias = materia.devuelveMateriasModelo();
            
            for (int i = 0; i < materias.size(); i++) {
                
                vistaCursado.cb_CodMat_Curs.addItem(materias.get(i).getMat_cod() + "-" + materias.get(i).getMat_nombre());
                
            }
            
        //-----------------RELLENA LA TABLA------------------------------------------------------------------------------------------

            llenarTabla(vistaCursado.tablaCursado);
            
        //---------------------------------------------------------------------------------------------------------------------------
            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            
        }
        
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
        
        try {
            
            vistaCursado.cb_CodMat_Curs.removeAllItems();
            vistaCursado.cb_DNIAlu_Curs.removeAllItems();
            
            //-----------------RELLENA EL COMBOBOX DE ALUMNOS---------------------------------------------------------------------------------------
            
            alumno = new ModeloAlumno();
            
            ArrayList<ModeloAlumno> alumnos = alumno.devuelveAlumnosModelo();
            
            for (int i = 0; i < alumnos.size(); i++) {
                
                vistaCursado.cb_DNIAlu_Curs.addItem(alumnos.get(i).getDni_alu() + "-" + alumnos.get(i).getNombre_alu() + " " + alumnos.get(i).getApellido_alu());
                
            }
            
            vistaCursado.cb_DNIAlu_Curs.setSelectedItem(null);
            
            //-------------RELLENA EL COMBOBOX DE MATERIAS-----------------------------------------------------------------------------
            
            materia = new ModeloMateria();
            
            ArrayList<ModeloMateria> materias = materia.devuelveMateriasModelo();
            
            for (int i = 0; i < materias.size(); i++) {
                
                vistaCursado.cb_CodMat_Curs.addItem(materias.get(i).getMat_cod() + "-" + materias.get(i).getMat_nombre());
                
            }
            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            
        }
        
    }
    
}
