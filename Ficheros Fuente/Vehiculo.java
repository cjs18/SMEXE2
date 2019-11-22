/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica;


import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Carlos Desco Parada
 */
public abstract class Vehiculo implements java.io.Serializable{
    private String matricula;
    private String color;
    private Conductor habitual;
    private String modelo;
    
    
    public Vehiculo(){}
    
    public Vehiculo(String matricula,String color,String modelo,int CarnetConductor){
        ArrayList <Coche> lista1 = Coche.getListaCoches();
        ArrayList <Camion> lista2 = Camion.getListaCamiones();
        if (! (lista1.contains(Coche.busquedaMatricula(matricula)) || lista2.contains(Camion.busquedaMatricula(matricula))))
            this.matricula = matricula;
        else
            this.matricula = null;
        this.color = color;
        Conductor con = Conductor.busquedaCarnet(CarnetConductor);
        this.habitual = con;
        this.modelo = modelo;
    }

    public void setHabitual(int ncarnet) {
    this.habitual = Conductor.busquedaCarnet(ncarnet);
    }
    
    public boolean cambioValidoHabitual(int ncarnet){
    if (ncarnet == this.habitual.getCarnet().getNumeroId())
        return true;
    return (Conductor.getListaConductores().contains(Conductor.busquedaCarnet(ncarnet)));
    }

    public String getMatricula() {
        return matricula;
    }

    public String getColor() {
        return color;
    }
    
    public void setColor(String color){
    this.color = color;
    }
    public String getModelo(){
        return modelo;
    }
    
    public void setModelo(String modelo){
    this.modelo = modelo;
    }
    
    public Conductor getHabitual() {
        return habitual;
    }

    public void setMatricula(String matricula){
     this.matricula = matricula;
    }
    
    public boolean cambioValidoMatricula(String matricula){
    ArrayList <Coche> lista1 = Coche.getListaCoches();
    ArrayList <Camion> lista2 = Camion.getListaCamiones();
    if(matricula.equals(this.matricula))
        return true;
    return (! (lista1.contains(Coche.busquedaMatricula(matricula)) && lista2.contains(Camion.busquedaMatricula(matricula))));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vehiculo other = (Vehiculo) obj;
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        return true;
    }   
}
