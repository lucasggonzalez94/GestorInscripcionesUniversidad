package controlador;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MetodosGenerales {
    
    //----------VALIDA QUE LOS CAMPOS NUMERICOS SOLO CONTENGAN NUMEROS---------------------------------
    
    public static boolean validarNumero(String s) {
        
        try {
            if (s!=null) {

                Long.parseLong(s);
                
                return true;

            }
        } catch (NumberFormatException e) {
            
            return false;
            
        }
        
        return false;
        
    }
    
    //----------LIMPIA LA TABLA-----------------------------------------------------------------------
    
    public static void limpiarTabla(JTable tabla) {
        DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
        int a = tabla.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
    }
    
}
