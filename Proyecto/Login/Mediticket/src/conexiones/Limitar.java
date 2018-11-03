/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author DAVE
 */
public class Limitar extends PlainDocument{
    private JTextField editor;
    private int num;

    public Limitar(JTextField editor, int num) {
        this.editor = editor;
        this.num = num;
    }
    
    public void insertString(int arg0,String arg1, AttributeSet arg2)throws BadLocationException{
        if((editor.getText().length()+arg1.length()) > this.num ){
            return;
        
        } super.insertString(arg0, arg1, arg2);
    
    }
}
