/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Structures_Logic.Graph;
import Ventanas.Gestor;

/**
 *
 * @author dcama
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Gestor gestor= new Gestor();
//        gestor.showMain();


          Graph g = new Graph();
          g.init("NodoInicial");
          g.insert("nodo1","");
          g.insert("nodo2","nodo1");
          g.insert("nodo3","NodoInicial");
          g.insert("nodo4","nodo1");
          g.insert("nodo5","nodo2");
          g.showGraph();
          
          
    }
    
}
