package Practica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedWriter;
import java.time.LocalDate;
import java.io.IOException;

import java.io.*;
/**
 *
 * @author Franco_Viggiano
 */
public class Notificacion implements java.io.Serializable{
    
 private LocalDate plazo;
 private double importe;
 private Alegacion alegacion;
 private String causa;

 public Notificacion(LocalDate fechaHora, double importe,Alegacion alegacion, String causa ){
     /* this.plazo = fechaHora.plusMonths(1); */       
        this.importe = importe;
        this.alegacion = alegacion;
        this.causa = causa;
 }
  public Notificacion(){
     
  }

    //Texto de la notificacion
    public String textoNotificado(){
        boolean aux = this.alegacion.getAceptada();
        if (aux){
        return("Se han tenido en cuenta sus alegaciones, el expediente se da por concluido por sobreseimiento");} else
        return("Se rechazan las alegaciones, la causa de la denuncia es: " + this.causa +
                ", El importe es de: "+this.importe+", tiene Ud de plazo hasta: "+this.plazo);
    }
    
    //Crear fichero (Notificación de la dencuncia)
    public void crearFichero(){
    try {
    FileWriter fw = new FileWriter("Notificacion de la denuncia.txt");
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter escribir = new PrintWriter(bw);
    escribir.println(textoNotificado());
    escribir.close();
    }
    catch(java.io.IOException ioex) { }
    }
 
    //GETTERS SETTERS
    public LocalDate getPlazo() {
        return plazo;
    }

    public double getImporte() {
        return importe;
    }

    public Alegacion getAlegacion() {
        return alegacion;
    }

    public String getCausa() {
        return causa;
    }


    public void setPlazo(LocalDate plazo) {
        this.plazo = plazo.plusMonths(1);
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public void setAlegacion(Alegacion alegacion) {
        this.alegacion = alegacion;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }
 
    
    
    
}
