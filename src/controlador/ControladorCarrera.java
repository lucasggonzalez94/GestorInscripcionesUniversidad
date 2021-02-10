package controlador;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import vista.*;

public class ControladorCarrera extends WindowAdapter implements ActionListener, MouseListener {
    
    VistaCarrera vistaCar;
    ModeloCarrera carrera;
    DefaultTableModel modeloTabla;
    
    public ControladorCarrera(VistaCarrera vistaCar, ModeloCarrera carrera) {
        
        this.vistaCar = vistaCar;
        this.carrera = carrera;
        
        vistaCar.setVisible(true);
        
        vistaCar.addWindowListener(this);
        
        vistaCar.btnAceptModif_Car.addActionListener(this);
        vistaCar.btnElim_Car.addActionListener(this);
        vistaCar.btnInsert_Car.addActionListener(this);
        vistaCar.btnLimpiar_Car.addActionListener(this);
        vistaCar.btnModif_Car.addActionListener(this);
        vistaCar.btnSalir_Car.addActionListener(this);
        vistaCar.tablaCarreras.addMouseListener(this);
        
        vistaCar.btnAceptModif_Car.setEnabled(false);
        
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(vistaCar.btnInsert_Car)) { // BOTON INSERTAR
            
            if (!vistaCar.jtNombre_Car.getText().trim().isEmpty()) {
                
                if (MetodosGenerales.validarNumero(vistaCar.jtDuracion.getText())) { // VALIDACION CAMPOS NUMERICOS
                
                    try {

                        //---------CREAR OBJETO MODELOCARRERA---------------------------------------------------------------

                        carrera = new ModeloCarrera();

                        carrera.setCar_nombre(vistaCar.jtNombre_Car.getText());
                        
                        if (vistaCar.jtDuracion.getText() != null) {
                        
                            carrera.setCar_duracion(Integer.parseInt(vistaCar.jtDuracion.getText()));
                            
                        }else {
                            
                            carrera.setCar_duracion(0);
                            
                        }

                        //--------------------------------------------------------------------------------------------------

                        carrera.insertaCarreraModelo(carrera);

                        MetodosGenerales.limpiarTabla(vistaCar.tablaCarreras);

                        llenarTabla(vistaCar.tablaCarreras);
                        
                        vistaCar.jtNombre_Car.setText(null);
                        vistaCar.jtDuracion.setText(null);

                    } catch (SQLException ex) {
                        
                        ex.printStackTrace();
                        
                    }
                
                }else {
                    
                    JOptionPane.showMessageDialog(vistaCar, "No se pueden ingresar caracteres extraños en el campo duración.");
                    
                }
                
            }else {
                
                JOptionPane.showMessageDialog(vistaCar, "Los campos con asterisco son obligatorios.");
                
            }
            
        }else if (e.getSource().equals(vistaCar.btnLimpiar_Car)) { // BOTON LIMPIAR
            
            vistaCar.jtNombre_Car.setText(null);
            vistaCar.jtDuracion.setText(null);
            
            vistaCar.btnAceptModif_Car.setEnabled(false);
            vistaCar.btnInsert_Car.setEnabled(true);
            
        }else if (e.getSource().equals(vistaCar.btnElim_Car)) { // BOTON ELIMINAR
            
            try {
                
                int seleccion = vistaCar.tablaCarreras.getSelectedRow();
                
                String dni = String.valueOf(vistaCar.tablaCarreras.getValueAt(seleccion, 0));
                
                carrera.eliminaInscripModelo(dni);
                
                MetodosGenerales.limpiarTabla(vistaCar.tablaCarreras);
                
                llenarTabla(vistaCar.tablaCarreras);
                
            } catch (SQLException ex) {
                
                ex.printStackTrace();
                
            }
            
        }else if (e.getSource().equals(vistaCar.btnModif_Car)) { // BOTON MODIFICAR
            
            int seleccion = vistaCar.tablaCarreras.getSelectedRow();
            
            if (seleccion!=-1) {
            
            vistaCar.jtNombre_Car.setText(String.valueOf(vistaCar.tablaCarreras.getValueAt(seleccion, 1)));
            vistaCar.jtDuracion.setText(String.valueOf(vistaCar.tablaCarreras.getValueAt(seleccion, 2)));
            
            vistaCar.btnInsert_Car.setEnabled(false);
            
            }else {
                
                JOptionPane.showMessageDialog(vistaCar, "Seleccione un registro de la tabla.");
                
                vistaCar.btnInsert_Car.setEnabled(true);
                
            }
            
            vistaCar.btnAceptModif_Car.setEnabled(true);
            
        }else if (e.getSource().equals(vistaCar.btnAceptModif_Car)) { // BOTON ACEPTAR MODIFICACION
            
            vistaCar.btnInsert_Car.setEnabled(true);
            
            if (vistaCar.jtNombre_Car.getText() != null) {
                
                if (MetodosGenerales.validarNumero(vistaCar.jtDuracion.getText())) { // VALIDACION CAMPOS NUMERICOS
                
                    try {

                        carrera = new ModeloCarrera();
                        int seleccion = vistaCar.tablaCarreras.getSelectedRow();

                        //--------RELLENA OBJETO MODELOCARRERA------------------------------------------------------------------------

                        carrera.setCar_cod(Long.parseLong(String.valueOf(vistaCar.tablaCarreras.getValueAt(seleccion, 0))));
                        carrera.setCar_nombre(vistaCar.jtNombre_Car.getText());
                        
                        if (vistaCar.jtDuracion.getText() != null) {
                        
                            carrera.setCar_duracion(Integer.parseInt(vistaCar.jtDuracion.getText()));
                            
                        }else {
                            
                            carrera.setCar_duracion(0);
                            
                        }

                        //------------------------------------------------------------------------------------------------------------

                        carrera.actualizaCarreraModelo(carrera, String.valueOf(vistaCar.tablaCarreras.getValueAt(seleccion, 0)));

                        MetodosGenerales.limpiarTabla(vistaCar.tablaCarreras);

                        llenarTabla(vistaCar.tablaCarreras);
                        
                        vistaCar.jtNombre_Car.setText(null);
                        vistaCar.jtDuracion.setText(null);
                        
                        vistaCar.btnAceptModif_Car.setEnabled(false);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                
                }else {
                    
                    JOptionPane.showMessageDialog(vistaCar, "No se pueden ingresar caracteres extraños en el campo duración.");
                    
                }
                
            }else {
                
                JOptionPane.showMessageDialog(vistaCar, "Los campos con asterisco son obligatorios.");
                
            }
            
        }else if (e.getSource().equals(vistaCar.btnSalir_Car)) { // BOTON SALIR
            
            vistaCar.dispose();
            
            vistaCar = null;
            carrera = null;
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
        
        vistaCar.btnModif_Car.setEnabled(false);
        vistaCar.btnElim_Car.setEnabled(false);
        
        try {
            
            llenarTabla(vistaCar.tablaCarreras);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    //----------LLENA LA TABLA-------------------------------------------------------------------------------------
    
    public void llenarTabla(JTable tabla) throws SQLException {
        String[] columnas = {"Código", "Nombre", "Duración"};
        modeloTabla = new DefaultTableModel(null, columnas);
        ArrayList<ModeloCarrera> carreras;
        carreras = carrera.devuelveCarrerasModelo();
        MetodosGenerales.limpiarTabla(vistaCar.tablaCarreras);
        Object datos[] = new Object[4];
        if (carreras.size() > 0) {
            for (int i = 0; i < carreras.size(); i++) {
                datos[0] = carreras.get(i).getCar_cod();
                datos[1] = carreras.get(i).getCar_nombre();
                datos[2] = carreras.get(i).getCar_duracion();
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        carreras.clear();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource().equals(vistaCar.tablaCarreras)) {
            
            vistaCar.btnModif_Car.setEnabled(true);
            vistaCar.btnElim_Car.setEnabled(true);
            
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getClickCount()==2) {
        
            if (e.getSource().equals(vistaCar.tablaCarreras)) {

                vistaCar.btnInsert_Car.setEnabled(false);

                int seleccion = vistaCar.tablaCarreras.getSelectedRow();
            
                if (seleccion!=-1) {
            
                    vistaCar.jtNombre_Car.setText(String.valueOf(vistaCar.tablaCarreras.getValueAt(seleccion, 1)));
                    vistaCar.jtDuracion.setText(String.valueOf(vistaCar.tablaCarreras.getValueAt(seleccion, 2)));
            
                }else {
                
                    JOptionPane.showMessageDialog(vistaCar, "Seleccione un registro de la tabla.");
                
                }
                
                vistaCar.btnAceptModif_Car.setEnabled(true);

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
