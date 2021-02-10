package controlador;

import java.awt.event.*;
import vista.*;
import modelo.*;

public class ControladorMenu implements ActionListener{
    
    private VistaMenu menu;
    
    public ControladorMenu(VistaMenu menu) {
        
        this.menu=menu;
        
        menu.setVisible(true);
        
        menu.btnAlumnos.addActionListener(this);
        menu.btnInscripciones.addActionListener(this);
        menu.btnCursado.addActionListener(this);
        menu.btnMaterias.addActionListener(this);
        menu.btnProfesores.addActionListener(this);
        menu.btnCarreras.addActionListener(this);
        menu.btnSalir.addActionListener(this);
        
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(menu.btnAlumnos)) {
            
            VistaAlumno vistaAlumno = new VistaAlumno();
            ModeloAlumno alumno = new ModeloAlumno();
            ControladorAlumno contAlu = new ControladorAlumno(vistaAlumno,alumno);
            
            vistaAlumno = null;
            alumno = null;
            
        }else if (e.getSource().equals(menu.btnInscripciones)) {
            
            VistaInscripcion vInscripcion = new VistaInscripcion();
            ModeloInscripcion mInscripcion = new ModeloInscripcion();
            ControladorInscripcion cInsc = new ControladorInscripcion(vInscripcion, mInscripcion);
            
            vInscripcion = null;
            mInscripcion = null;
            
        }else if (e.getSource().equals(menu.btnCursado)) {
            
            VistaCursado vCursado = new VistaCursado();
            ModeloCursado mCursado = new ModeloCursado();
            ControladorCursado contCurs = new ControladorCursado(vCursado, mCursado);
            
            vCursado = null;
            mCursado = null;
            
        }else if (e.getSource().equals(menu.btnMaterias)) {
            
            VistaMateria vMateria = new VistaMateria();
            ModeloMateria mMateria = new ModeloMateria();
            ControladorMateria cMateria = new ControladorMateria(vMateria, mMateria);
            
            vMateria = null;
            mMateria = null;
            
        }else if (e.getSource().equals(menu.btnProfesores)) {
            
            VistaProfesor vProfesor = new VistaProfesor();
            ModeloProfesor mProfesor = new ModeloProfesor();
            ControladorProfesor cProfesor = new ControladorProfesor(vProfesor, mProfesor);
            
            vProfesor = null;
            mProfesor = null;
            
        }else if (e.getSource().equals(menu.btnCarreras)) {
            
            VistaCarrera vCarrera = new VistaCarrera();
            ModeloCarrera mCarrera = new ModeloCarrera();
            ControladorCarrera cCarrera = new ControladorCarrera(vCarrera, mCarrera);
            
            vCarrera = null;
            mCarrera = null;
            
        }else if (e.getSource().equals(menu.btnSalir)) {
            
            System.exit(0);
            
        }
        
    }
    
}
