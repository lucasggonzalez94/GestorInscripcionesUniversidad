package controlador;

import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

public class ControladorMateria extends WindowAdapter implements ActionListener, MouseListener {
    
    VistaMateria vistaMateria;
    ModeloMateria materia;
    DefaultTableModel modeloTabla;
    
    public ControladorMateria(VistaMateria vistaMateria, ModeloMateria materia) {
        
        this.vistaMateria = vistaMateria;
        this.materia = materia;
        
        this.vistaMateria.setVisible(true);
        
        vistaMateria.addWindowListener(this);
        vistaMateria.btnAceptModif_Mat.addActionListener(this);
        vistaMateria.btnElim_Mat.addActionListener(this);
        vistaMateria.btnInsert_Mat.addActionListener(this);
        vistaMateria.btnLimpiar_Mat.addActionListener(this);
        vistaMateria.btnModif_Mat.addActionListener(this);
        vistaMateria.btnSalir_Mat.addActionListener(this);
        vistaMateria.tablaMaterias.addMouseListener(this);
        
        vistaMateria.btnAceptModif_Mat.setEnabled(false);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(vistaMateria.btnInsert_Mat)) { // BOTON INSERTAR
            
            if (!vistaMateria.jtNombre_Mat.getText().trim().isEmpty()) {
                
                try {
                    
                    //--------CREA OBJETO MODELOMATERIA-----------------------------------------------------------------------
                    
                    ModeloMateria materia = new ModeloMateria();

                    materia.setMat_nombre(vistaMateria.jtNombre_Mat.getText());
                    
                    //----------SEPARO CODIGO DE NOMBRE-------------------------------------------------------------------------------
                    
                    String itemSeleccionado = String.valueOf(vistaMateria.CB_DNI_mat.getSelectedItem());
                    
                    long dni;
                    
                    if (vistaMateria.CB_DNI_mat.getSelectedItem() != null) {
                    
                        String [] partes = itemSeleccionado.split("-");

                        dni = Long.parseLong(partes[0]);
                    
                    }else {
                        
                        dni = 0;
                        
                    }
                    
                    //---------------------------------------------------------------------------------------------------------------
                    
                    materia.setMat_prof_dni(dni);
                    
                    //----------------------------------------------------------------------------------------------------------------

                    materia.insertaMateriaModelo(materia);
                    
                    MetodosGenerales.limpiarTabla(vistaMateria.tablaMaterias);

                    llenarTabla(vistaMateria.tablaMaterias);
                    
                    vistaMateria.jtNombre_Mat.setText(null);
                    vistaMateria.CB_DNI_mat.setSelectedIndex(0);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
            }else {
                
                JOptionPane.showMessageDialog(vistaMateria, "Los campos con asterisco son obligatorios.");
                
            }
            
        }else if (e.getSource().equals(vistaMateria.btnLimpiar_Mat)) { // BOTON LIMPIAR
            
            vistaMateria.jtNombre_Mat.setText(null);
            vistaMateria.CB_DNI_mat.setSelectedIndex(0);
            
            vistaMateria.btnAceptModif_Mat.setEnabled(false);
            vistaMateria.btnInsert_Mat.setEnabled(true);
            
        }else if (e.getSource().equals(vistaMateria.btnElim_Mat)) { // BOTON ELIMINAR
            
            try {
                
                int seleccion = vistaMateria.tablaMaterias.getSelectedRow();
                
                String cod = String.valueOf(vistaMateria.tablaMaterias.getValueAt(seleccion, 0));
                
                materia.eliminaMateriaModelo(cod);
                
                MetodosGenerales.limpiarTabla(vistaMateria.tablaMaterias);
                
                llenarTabla(vistaMateria.tablaMaterias);
                
            } catch (SQLException ex) {
                
                JOptionPane.showMessageDialog(null, "No puedes eliminar una materia asociada a un cursado.");
                ex.printStackTrace();
                
            }
            
        }else if (e.getSource().equals(vistaMateria.btnModif_Mat)) { // BOTON MODIFICAR
            
            int seleccion = vistaMateria.tablaMaterias.getSelectedRow();
            
            if (seleccion!=-1) {
            
                try {
                    
                    ModeloProfesor profesor = new ModeloProfesor();
                    
                    ArrayList<ModeloProfesor> profesores = profesor.devuelveProfesorModelo();
                    
                    for (int i = 0; i < profesores.size(); i++) {
                        
                        if (String.valueOf(vistaMateria.tablaMaterias.getValueAt(seleccion, 2)).equals(String.valueOf(profesores.get(i).getProf_dni()))) {
                            
                            profesor = profesores.get(i);
                            
                        }
                        
                    }
                    
                    String item = String.valueOf(profesor.getProf_dni() + "-" + profesor.getProf_nombre() + " " + profesor.getProf_apellido());
                    
                    vistaMateria.jtNombre_Mat.setText(String.valueOf(vistaMateria.tablaMaterias.getValueAt(seleccion, 1)));
                    vistaMateria.CB_DNI_mat.setSelectedItem(item);
                    
                    vistaMateria.btnInsert_Mat.setEnabled(false);
                    
                } catch (SQLException ex) {
                    
                    ex.printStackTrace();
                    
                }
            
            }else {
                
                JOptionPane.showMessageDialog(vistaMateria, "Seleccione un registro de la tabla.");
                
            }
            
            vistaMateria.btnAceptModif_Mat.setEnabled(true);
            
        }else if (e.getSource().equals(vistaMateria.btnAceptModif_Mat)) { // BOTON ACEPTAR MODIFICACION
            
            if (!vistaMateria.jtNombre_Mat.getText().trim().isEmpty()) {
                
                try {

                    materia = new ModeloMateria();
                    int seleccion = vistaMateria.tablaMaterias.getSelectedRow();
                    
                    //--------RELLENA OBJETO MODELOINSCRIPCION------------------------------------------------------------------------

                    materia.setMat_nombre(vistaMateria.jtNombre_Mat.getText());
                    
                    //----------SEPARO CODIGO DE NOMBRE-------------------------------------------------------------------------------
                    
                    String itemSeleccionado = String.valueOf(vistaMateria.CB_DNI_mat.getSelectedItem());
                    
                    long dni;
                    
                    if (vistaMateria.CB_DNI_mat.getSelectedItem() != null) {
                    
                        String [] partes = itemSeleccionado.split("-");

                        dni = Long.parseLong(partes[0]);
                    
                    }else {
                        
                        dni = 0;
                        
                    }
                    
                    //---------------------------------------------------------------------------------------------------------------
                    
                    materia.setMat_prof_dni(dni);
                    
                    //----------------------------------------------------------------------------------------------------------------

                    materia.actualizaMateriaModelo(materia, String.valueOf(vistaMateria.tablaMaterias.getValueAt(seleccion, 0)));

                    MetodosGenerales.limpiarTabla(vistaMateria.tablaMaterias);

                    llenarTabla(vistaMateria.tablaMaterias);
                    
                    vistaMateria.btnAceptModif_Mat.setEnabled(false);
                    vistaMateria.btnInsert_Mat.setEnabled(true);
                    
                    vistaMateria.jtNombre_Mat.setText(null);
                    vistaMateria.CB_DNI_mat.setSelectedIndex(0);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
            }else {
                
                JOptionPane.showMessageDialog(vistaMateria, "Los campos con asterisco son obligatorios.");
                
            }
            
        }else if (e.getSource().equals(vistaMateria.btnSalir_Mat)) { // BOTON SALIR
            
            vistaMateria.dispose();
            
            vistaMateria = null;
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
        
        if (e.getSource().equals(vistaMateria.tablaMaterias)) {
            
            vistaMateria.btnModif_Mat.setEnabled(true);
            vistaMateria.btnElim_Mat.setEnabled(true);
            
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getClickCount()==2) {
        
            if (e.getSource().equals(vistaMateria.tablaMaterias)) {

                vistaMateria.btnInsert_Mat.setEnabled(false);
                vistaMateria.btnAceptModif_Mat.setEnabled(true);

                int seleccion = vistaMateria.tablaMaterias.getSelectedRow();

                if (seleccion!=-1) {
                    
                    try {
                        
                        ModeloProfesor profesor = new ModeloProfesor();
                        
                        ArrayList<ModeloProfesor> profesores = profesor.devuelveProfesorModelo();
                        
                        for (int i = 0; i < profesores.size(); i++) {
                            
                            if (String.valueOf(vistaMateria.tablaMaterias.getValueAt(seleccion, 2)).equals(String.valueOf(profesores.get(i).getProf_dni()))) {
                                
                                profesor = profesores.get(i);
                                
                            }
                            
                        }

                        String item = String.valueOf(profesor.getProf_dni() + "-" + profesor.getProf_nombre() + " " + profesor.getProf_apellido());
                        
                        vistaMateria.jtNombre_Mat.setText(String.valueOf(vistaMateria.tablaMaterias.getValueAt(seleccion, 1)));
                        vistaMateria.CB_DNI_mat.setSelectedItem(item);
                        
                    } catch (SQLException ex) {
                        
                        ex.printStackTrace();
                        
                    }

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
        
        vistaMateria.btnModif_Mat.setEnabled(false);
        vistaMateria.btnElim_Mat.setEnabled(false);
        
        vistaMateria.CB_DNI_mat.removeAllItems();
        
        //-----------------RELLENA EL COMBOBOX---------------------------------------------------------------------------------------
        
        try {
            
            ModeloProfesor profesor = new ModeloProfesor();
            
            ArrayList<ModeloProfesor> profesores = profesor.devuelveProfesorModelo();
            
            for (int i = 0; i < profesores.size(); i++) {
                
                vistaMateria.CB_DNI_mat.addItem(profesores.get(i).getProf_dni() + "-" + profesores.get(i).getProf_nombre() + " " + profesores.get(i).getProf_apellido());
                
            }
            
        //-----------------RELLENA LA TABLA------------------------------------------------------------------------------------------

            llenarTabla(vistaMateria.tablaMaterias);
            
        //---------------------------------------------------------------------------------------------------------------------------
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
        
        try {
            
            vistaMateria.CB_DNI_mat.removeAllItems();
            
            ModeloProfesor profesor = new ModeloProfesor();
            
            ArrayList<ModeloProfesor> profesores = profesor.devuelveProfesorModelo();
            
            for (int i = 0; i < profesores.size(); i++) {
                
                vistaMateria.CB_DNI_mat.addItem(profesores.get(i).getProf_dni() + "-" + profesores.get(i).getProf_nombre() + " " + profesores.get(i).getProf_apellido());
                
            }
            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            
        }
        
    }
    
    //----------LLENA LA TABLA-------------------------------------------------------------------------------------
    
    public void llenarTabla(JTable tabla) throws SQLException {
        String[] columnas = {"Código", "Nombre", "DNI Profesor"};
        modeloTabla = new DefaultTableModel(null, columnas);
        ArrayList<ModeloMateria> materias;
        materias = materia.devuelveMateriasModelo();
        MetodosGenerales.limpiarTabla(vistaMateria.tablaMaterias);
        Object datos[] = new Object[3];
        if (materias.size() > 0) {
            for (int i = 0; i < materias.size(); i++) {
                datos[0] = materias.get(i).getMat_cod();
                datos[1] = materias.get(i).getMat_nombre();
                datos[2] = materias.get(i).getMat_prof_dni();
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        materias.clear();

    }
    
}
