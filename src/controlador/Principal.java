package controlador;

import vista.VistaMenu;

public class Principal {

    public static void main(String[] args) {
        
        VistaMenu menu = new VistaMenu();
        ControladorMenu principal = new ControladorMenu(menu);
        
    }
    
}
