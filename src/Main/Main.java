/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Ventanas.Gestor;
import java.io.IOException;
import org.apache.bcel.classfile.ClassFormatException;

/**
 *
 * @author Daniel Camacho
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassFormatException, ClassNotFoundException 
    {
        Gestor gestor= new Gestor();
        gestor.showMain();
    }
    
}
