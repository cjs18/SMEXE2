/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica;


/**
 *
 * @author Carlos Desco Parada
 */
import java.util.ArrayList;
import java.io.*;

public class Camion extends Vehiculo implements java.io.Serializable{
    private String cif;
    private ArrayList <Conductor> habituales;
    private static ArrayList <Camion> listaCamiones;
    
    
    public Camion(){super();}
    
    public Camion(String matricula, String color,String modelo,int carnetConductor,String cif){
    super(matricula,color,modelo,carnetConductor);
    this.cif = cif;
    habituales = new ArrayList();
    habituales.add(super.getHabitual());
    if(super.getMatricula() != null)
        anadirCamion();
    }
    
    public boolean anadirConductor(Conductor con){
    if (! habituales.contains(con))
        return habituales.add(con);
    return false;
    }
    
    public boolean eliminarConductor(Conductor con){
    if(getHabitual() != con)
    return habituales.remove(con);
    return false;
    }                 
    
    public void listadoConductores(){
    
    }
    public ArrayList <Conductor> getConductores(){
        return habituales; 
    }
    public String getCif() {
        return cif;
    }
    

    public void setCif(String cif) {
        this.cif = cif;
    }
    
    public static Camion busquedaMatricula(String matricula){
    for (Camion cam: listaCamiones){
        if (matricula.equals(cam.getMatricula()))
            return cam;
    }
    return null;
    }
    
    public static ArrayList<Camion> getListaCamiones(){
    return listaCamiones;
    }

    public void anadirCamion(){
    listaCamiones.add(this);
    actualizarFichero();
    }
    
    
     public static void cargarCamion(){
    listaCamiones = new ArrayList();
    ArrayList<Camion> listaaux = new ArrayList<>();
    boolean haysig = true;
    try{
    FileInputStream fis = new FileInputStream("Camiones.bin");
    ObjectInputStream input = new ObjectInputStream(fis);
    while(haysig){
      Camion cam = (Camion) input.readObject();
      if(cam != null)
         listaaux.add(cam);
      else
         haysig = false;
   }
    }
    catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    catch(IOException | ClassNotFoundException e){
   //System.out.println(e.printStackTrace());
}
    
listaCamiones.addAll(listaaux);

    }
    
    public static void actualizarFichero(){
     try {
            FileOutputStream fos = new FileOutputStream("Camiones.bin");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Camion cam : listaCamiones){
                    try {
                        oos.writeObject(cam);
                        System.out.println("saved");
                    } catch (NotSerializableException e) {
                        System.out.println("Objeto no serializable");
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    public boolean eliminarCamion(){
    if( listaCamiones.remove(this)){
    actualizarFichero();
    return true;
            }
    return false;
    } 

    @Override
    public String toString() {
        return "Matricula:   " + getMatricula()+ " | Color:  " + getColor() + " | Modelo:  " + getModelo()+ " | Carne asociado  " + Integer.toString(getHabitual().getCarnet().getNumeroId())+ "| CIF : " + getCif();
    }
    
    
    
}
