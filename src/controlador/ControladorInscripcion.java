package controlador;

import vista.*;
import modelo.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorInscripcion extends WindowAdapter implements ActionListener, MouseListener {
    
    VistaInscripcion vistaInsc;
    ModeloInscripcion insc;
    ModeloAlumno alumno;
    DefaultTableModel modeloTabla;
    
    public ControladorInscripcion(VistaInscripcion vistaInsc, ModeloInscripcion insc) {
        
        this.vistaInsc = vistaInsc;
        this.insc = insc;
        
        vistaInsc.setVisible(true);
        
        vistaInsc.addWindowListener(this);
        vistaInsc.btnLimpiar_Inscrip.addActionListener(this);
        vistaInsc.btnElim_Inscrip.addActionListener(this);
        vistaInsc.btnModif_Inscrip.addActionListener(this);
        vistaInsc.btnSalir_Inscrip.addActionListener(this);
        vistaInsc.btnInscribir_Insc.addActionListener(this);
        vistaInsc.btnAceptarMod_Insc.addActionListener(this);
        vistaInsc.tablaInscrip.addMouseListener(this);
        
        vistaInsc.btnAceptarMod_Insc.setEnabled(false);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(vistaInsc.btnInscribir_Insc)) { // BOTON INSCRIBIR
            
            try {
            
                alumno = new ModeloAlumno();

                ArrayList<ModeloAlumno> alumnos = alumno.devuelveAlumnosModelo();

                if (vistaInsc.jtFecha_Inscrip.getDate() != null) {

                    try {

                        //--------CREAB OBJETO MODELOINSCRIPCION-----------------------------------------------------------------------

                        ModeloInscripcion inscripcion = new ModeloInscripcion();

                        inscripcion.setInsc_cod(Long.parseLong(vistaInsc.jtCod_insc.getText()));

                        //----------SEPARO CODIGO DE ALUMNO-------------------------------------------------------------------------------

                        String itemSeleccionado = String.valueOf(vistaInsc.cbAlumno_Insc.getSelectedItem());

                        String [] partes = itemSeleccionado.split("-");

                        long dni = Long.parseLong(partes[0]);

                        //---------VALIDACION PARA ALUMNO QUE YA ESTA INSCRIPTO------------------------------------------------------------

                        for (int i = 0; i < alumnos.size(); i++) {

                            if (dni == alumnos.get(i).getDni_alu()) {

                                alumno = alumnos.get(i);

                            }

                        }

                        if (alumno.getInscrip_alu() > 0) {

                            JOptionPane.showMessageDialog(vistaInsc, "El alumno seleccionado ya esta inscripto.");

                            return;

                        }

                        //-------------------------------------------------------------------------------------------------------------

                        inscripcion.setDni_Alumno_Insc(dni);

                        //--------CONVERSION DE TIPOS DATE----------------------------------------------------------------------------

                        java.util.Date utilDate = vistaInsc.jtFecha_Inscrip.getDate();
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                        //---------------------------------------------------------------------------------------------------------------

                        inscripcion.setInsc_fecha(sqlDate);

                        //----------SEPARO CODIGO DE NOMBRE-------------------------------------------------------------------------------

                        itemSeleccionado = String.valueOf(vistaInsc.cb_Cod_Car_Inscrip.getSelectedItem());

                        partes = itemSeleccionado.split("-");

                        long codigo = Long.parseLong(partes[0]);

                        //---------------------------------------------------------------------------------------------------------------

                        inscripcion.setInsc_car_cod(codigo);

                        inscripcion.insertaInscModelo(inscripcion);

                        inscripcion.inscribeModelo(Long.parseLong(vistaInsc.jtCod_insc.getText()), String.valueOf(dni));

                        MetodosGenerales.limpiarTabla(vistaInsc.tablaInscrip);

                        llenarTabla(vistaInsc.tablaInscrip);

                        vistaInsc.cbAlumno_Insc.setSelectedIndex(0);
                        vistaInsc.jtFecha_Inscrip.setDate(null);
                        vistaInsc.cb_Cod_Car_Inscrip.setSelectedIndex(0);
                        
                        //----------------INSERTA ID EN EL CAMPO--------------------------------------------------------------------------------------
        
                        if (vistaInsc.tablaInscrip.getRowCount()==0) {

                            vistaInsc.jtCod_insc.setText("1");

                        }else {

                            int ultimoReg = (vistaInsc.tablaInscrip.getRowCount())-1;

                            long ultimoID = (long) vistaInsc.tablaInscrip.getValueAt(ultimoReg, 0);

                            ultimoID++;

                            long idNuevo = ultimoID;

                            vistaInsc.jtCod_insc.setText(String.valueOf(idNuevo));

                        }

                        //---------------------------------------------------------------------------------------------------------------------------

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }else {

                    JOptionPane.showMessageDialog(vistaInsc, "Los campos con asterisco son obligatorios.");

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        }else if (e.getSource().equals(vistaInsc.btnLimpiar_Inscrip)) { // BOTON LIMPIAR
            
            vistaInsc.cbAlumno_Insc.setSelectedIndex(0);
            vistaInsc.jtFecha_Inscrip.setDate(null);
            vistaInsc.cb_Cod_Car_Inscrip.setSelectedIndex(0);
            
            vistaInsc.btnAceptarMod_Insc.setEnabled(false);
            vistaInsc.btnInscribir_Insc.setEnabled(true);
            
        }else if (e.getSource().equals(vistaInsc.btnElim_Inscrip)) { // BOTON ELIMINAR
            
            try {
                
                int seleccion = vistaInsc.tablaInscrip.getSelectedRow();
                
                String dni = String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 0));
                
                insc.eliminaInscripModelo(dni);
                
                MetodosGenerales.limpiarTabla(vistaInsc.tablaInscrip);
                
                llenarTabla(vistaInsc.tablaInscrip);
                
            } catch (SQLException ex) {
                
                JOptionPane.showMessageDialog(null, "No puedes eliminar una inscripcion asociada a un alumno.");
                ex.printStackTrace();
                
            }
            
        }else if (e.getSource().equals(vistaInsc.btnModif_Inscrip)) { // BOTON MODIFICAR
            
            int seleccion = vistaInsc.tablaInscrip.getSelectedRow();
            
            if (seleccion!=-1) {
            
            try {
                
                alumno = new ModeloAlumno();
                
                ArrayList<ModeloAlumno> alumnos = alumno.devuelveAlumnosModelo();
                
                for (int i = 0; i < alumnos.size(); i++) {
                    
                    if (String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 1)).equals(String.valueOf(alumnos.get(i).getDni_alu()))) {
                        
                        alumno = alumnos.get(i);
                        
                    }
                    
                }

                String item = String.valueOf(alumno.getDni_alu() + "-" + alumno.getNombre_alu() + " " + alumno.getApellido_alu());
                
                vistaInsc.jtCod_insc.setText(String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 0)));
                vistaInsc.cbAlumno_Insc.setSelectedItem(item);
                
                //-------CONVERSION DE STRING A DATE--------------------------------------------------------------------------
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date parsed = null;
                try {
                    parsed = sdf.parse(String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 2)));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                java.sql.Date fecha = new java.sql.Date(parsed.getTime());
                
                //------------------------------------------------------------------------------------------------------------
                
                vistaInsc.jtFecha_Inscrip.setDate(fecha);
                vistaInsc.cb_Cod_Car_Inscrip.setSelectedItem(Long.parseLong(String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 3))));
                
                vistaInsc.btnAceptarMod_Insc.setEnabled(true);
                vistaInsc.btnInscribir_Insc.setEnabled(false);
                
            } catch (SQLException ex) {
                
                ex.printStackTrace();
                    
            }
            
            }else {
                
                JOptionPane.showMessageDialog(vistaInsc, "Seleccione un registro de la tabla.");
                
            }
            
        }else if (e.getSource().equals(vistaInsc.btnAceptarMod_Insc)) { // BOTON ACEPTAR MODIFICACION
            
            if (!vistaInsc.jtFecha_Inscrip.getDate().equals(null)) {
                
                try {

                    insc = new ModeloInscripcion();
                    int seleccion = vistaInsc.tablaInscrip.getSelectedRow();
                    
                    //--------RELLENA OBJETO MODELOINSCRIPCION------------------------------------------------------------------------

                    insc.setInsc_cod(Long.parseLong(vistaInsc.jtCod_insc.getText()));
                    
                    //----------SEPARO CODIGO DE NOMBRE-------------------------------------------------------------------------------
                    
                    String itemSeleccionado = String.valueOf(vistaInsc.cbAlumno_Insc.getSelectedItem());
                    
                    String [] partes = itemSeleccionado.split("-");
                    
                    long dni = Long.parseLong(partes[0]);
                    
                    //---------------------------------------------------------------------------------------------------------------
                    
                    insc.setDni_Alumno_Insc(dni);
                    
                    //--------CONVERSION DE TIPOS DATE----------------------------------------------------------------------------
                    
                    java.util.Date utilDate = vistaInsc.jtFecha_Inscrip.getDate();
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    
                    //---------------------------------------------------------------------------------------------------------------
                    
                    insc.setInsc_fecha(sqlDate);
                    
                    //----------SEPARO CODIGO DE NOMBRE-------------------------------------------------------------------------------
                    
                    itemSeleccionado = String.valueOf(vistaInsc.cb_Cod_Car_Inscrip.getSelectedItem());
                    
                    partes = itemSeleccionado.split("-");
                    
                    long codigo = Long.parseLong(partes[0]);
                    
                    //---------------------------------------------------------------------------------------------------------------
                    
                    insc.setInsc_car_cod(codigo);

                    insc.actualizaInscripcionModelo(insc, String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 0)));

                    MetodosGenerales.limpiarTabla(vistaInsc.tablaInscrip);

                    llenarTabla(vistaInsc.tablaInscrip);
                    
                    vistaInsc.btnAceptarMod_Insc.setEnabled(false);
                    vistaInsc.btnInscribir_Insc.setEnabled(true);
                    
                    vistaInsc.cbAlumno_Insc.setSelectedIndex(0);
                    vistaInsc.jtFecha_Inscrip.setDate(null);
                    vistaInsc.cb_Cod_Car_Inscrip.setSelectedIndex(0);
                    
                    //----------------INSERTA ID EN EL CAMPO--------------------------------------------------------------------------------------
        
                    if (vistaInsc.tablaInscrip.getRowCount()==0) {

                        vistaInsc.jtCod_insc.setText("1");

                    }else {

                        int ultimoReg = (vistaInsc.tablaInscrip.getRowCount())-1;

                        long ultimoID = (long) vistaInsc.tablaInscrip.getValueAt(ultimoReg, 0);

                        ultimoID++;

                        long idNuevo = ultimoID;

                        vistaInsc.jtCod_insc.setText(String.valueOf(idNuevo));

                    }

                    //---------------------------------------------------------------------------------------------------------------------------

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
            }else {
                
                JOptionPane.showMessageDialog(vistaInsc, "Los campos con asterisco son obligatorios.");
                
            }
            
            //----------------INSERTA CODIGO EN EL CAMPO--------------------------------------------------------------------------------------
        
            int ultimoReg = (vistaInsc.tablaInscrip.getRowCount())-1;

            long ultimoID = (long) vistaInsc.tablaInscrip.getValueAt(ultimoReg, 0);

            ultimoID++;

            long idNuevo = ultimoID;

            vistaInsc.jtCod_insc.setText(String.valueOf(idNuevo));
            
        }else if (e.getSource().equals(vistaInsc.btnSalir_Inscrip)) { // BOTON SALIR
            
            vistaInsc.dispose();
            
            vistaInsc = null;
            insc = null;
            alumno = null;
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
        
        vistaInsc.btnModif_Inscrip.setEnabled(false);
        vistaInsc.btnElim_Inscrip.setEnabled(false);
        
        vistaInsc.cbAlumno_Insc.removeAllItems();
        vistaInsc.cb_Cod_Car_Inscrip.removeAllItems();
        
        try {
            
            //-----------------RELLENA EL COMBOBOX CARRERAS---------------------------------------------------------------------------------------
            
            ModeloCarrera carrera = new ModeloCarrera();
            
            ArrayList<ModeloCarrera> carreras = carrera.devuelveCarrerasModelo();
            
            for (int i = 0; i < carreras.size(); i++) {
                
                vistaInsc.cb_Cod_Car_Inscrip.addItem(carreras.get(i).getCar_cod() + "-" + carreras.get(i).getCar_nombre());
                
            }
            
            //-------------RELLENA EL COMBOBOX DE ALUMNOS-----------------------------------------------------------------------------
            
            alumno = new ModeloAlumno();
            
            ArrayList<ModeloAlumno> alumnos = alumno.devuelveAlumnosModelo();
            
            for (int i = 0; i < alumnos.size(); i++) {
                
                vistaInsc.cbAlumno_Insc.addItem(alumnos.get(i).getDni_alu() + "-" + alumnos.get(i).getNombre_alu() + " " + alumnos.get(i).getApellido_alu());
                
            }
            
            if (alumno != null) {
                
                vistaInsc.cbAlumno_Insc.setSelectedItem(alumno.getDni_alu() + "-" + alumno.getNombre_alu() + " " + alumno.getApellido_alu());
                
            }
            
        //-----------------RELLENA LA TABLA------------------------------------------------------------------------------------------

            llenarTabla(vistaInsc.tablaInscrip);
            
        //---------------------------------------------------------------------------------------------------------------------------
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //----------------INSERTA ID EN EL CAMPO--------------------------------------------------------------------------------------
        
        if (vistaInsc.tablaInscrip.getRowCount()==0) {
            
            vistaInsc.jtCod_insc.setText("1");
            
        }else {
        
            int ultimoReg = (vistaInsc.tablaInscrip.getRowCount())-1;

            long ultimoID = (long) vistaInsc.tablaInscrip.getValueAt(ultimoReg, 0);

            ultimoID++;

            long idNuevo = ultimoID;

            vistaInsc.jtCod_insc.setText(String.valueOf(idNuevo));
        
        }
        
        //---------------------------------------------------------------------------------------------------------------------------
        
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
        
        try {
            
            vistaInsc.cbAlumno_Insc.removeAllItems();
            vistaInsc.cb_Cod_Car_Inscrip.removeAllItems();
            
            //-----------------RELLENA EL COMBOBOX CARRERAS---------------------------------------------------------------------------------------
            
            ModeloCarrera carrera = new ModeloCarrera();
            
            ArrayList<ModeloCarrera> carreras = carrera.devuelveCarrerasModelo();
            
            for (int i = 0; i < carreras.size(); i++) {
                
                vistaInsc.cb_Cod_Car_Inscrip.addItem(carreras.get(i).getCar_cod() + "-" + carreras.get(i).getCar_nombre());
                
            }
            
            //-------------RELLENA EL COMBOBOX DE ALUMNOS-----------------------------------------------------------------------------
            
            alumno = new ModeloAlumno();
            
            ArrayList<ModeloAlumno> alumnos = alumno.devuelveAlumnosModelo();
            
            for (int i = 0; i < alumnos.size(); i++) {
                
                vistaInsc.cbAlumno_Insc.addItem(alumnos.get(i).getDni_alu() + "-" + alumnos.get(i).getNombre_alu() + " " + alumnos.get(i).getApellido_alu());
                
            }
            
            if (alumno != null) {
                
                vistaInsc.cbAlumno_Insc.setSelectedItem(alumno.getDni_alu() + "-" + alumno.getNombre_alu() + " " + alumno.getApellido_alu());
                
            }
            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            
        }
        
    }
    
    //----------LLENA LA TABLA-------------------------------------------------------------------------------------
    
    public void llenarTabla(JTable tabla) throws SQLException {
        String[] columnas = {"Código", "DNI Alumno", "Fecha","Id Carrera"};
        modeloTabla = new DefaultTableModel(null, columnas);
        ArrayList<ModeloInscripcion> inscripciones;
        inscripciones = insc.devuelveInscModelo();
        MetodosGenerales.limpiarTabla(vistaInsc.tablaInscrip);
        Object datos[] = new Object[4];
        if (inscripciones.size() > 0) {
            for (int i = 0; i < inscripciones.size(); i++) {
                datos[0] = inscripciones.get(i).getInsc_cod();
                datos[1] = inscripciones.get(i).getInsc_nombre();
                datos[2] = inscripciones.get(i).getInsc_fecha();
                datos[3] = inscripciones.get(i).getInsc_car_cod();
                modeloTabla.addRow(datos);
            }
        }

        tabla.setModel(modeloTabla);
        inscripciones.clear();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource().equals(vistaInsc.tablaInscrip)) {
            
            vistaInsc.btnModif_Inscrip.setEnabled(true);
            vistaInsc.btnElim_Inscrip.setEnabled(true);
            
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getClickCount()==2) {
        
            if (e.getSource().equals(vistaInsc.tablaInscrip)) {

            try {
                
                vistaInsc.btnInscribir_Insc.setEnabled(false);

                int seleccion = vistaInsc.tablaInscrip.getSelectedRow();
                
                alumno = new ModeloAlumno();
                
                ArrayList<ModeloAlumno> alumnos = alumno.devuelveAlumnosModelo();
                
                for (int i = 0; i < alumnos.size(); i++) {
                    
                    if (String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 1)).equals(String.valueOf(alumnos.get(i).getDni_alu()))) {
                        
                        alumno = alumnos.get(i);
                        
                    }
                    
                }

                String item = String.valueOf(alumno.getDni_alu() + "-" + alumno.getNombre_alu() + " " + alumno.getApellido_alu());
                
                vistaInsc.jtCod_insc.setText(String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 0)));
                vistaInsc.cbAlumno_Insc.setSelectedItem(item);
                
                //-------CONVERSION DE STRING A DATE--------------------------------------------------------------------------
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date parsed = null;
                try {
                    parsed = sdf.parse(String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 2)));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                java.sql.Date fecha = new java.sql.Date(parsed.getTime());
                
                //------------------------------------------------------------------------------------------------------------
                
                vistaInsc.jtFecha_Inscrip.setDate(fecha);
                vistaInsc.cb_Cod_Car_Inscrip.setSelectedItem(Long.parseLong(String.valueOf(vistaInsc.tablaInscrip.getValueAt(seleccion, 3))));
                
                vistaInsc.btnAceptarMod_Insc.setEnabled(true);
                
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
    
}
