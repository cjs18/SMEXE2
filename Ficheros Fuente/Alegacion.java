package Practica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Franco_Viggiano
 */
public class Alegacion implements java.io.Serializable{
    private String textoAlegacion;
    private boolean aceptada;

    public Alegacion(String defensa){
    this.textoAlegacion=defensa;
    }
    public Alegacion(){
    
    }
    
    public String getTextoAlegacion() {
        return textoAlegacion;
    }

    public boolean getAceptada() {
        return aceptada;
    }
    
    public void setTextoAlegacion(String textoAlegacion){
    this.textoAlegacion = textoAlegacion; 
    }
    
    public void setAceptada(boolean aceptada){
        this.aceptada = aceptada;
    }    
    
}
