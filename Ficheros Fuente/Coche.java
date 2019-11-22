package Practica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.io.*;


/**
 *
 * @author Carlos Desco Parada
 */
public class Coche extends Vehiculo implements java.io.Serializable{
    private static ArrayList <Coche> listaCoches;
    
    public Coche(){}
    
    public Coche(String matricula, String color, String modelo, int carnetConductor){
    super(matricula,color,modelo,carnetConductor);
    if(super.getMatricula() != null)
        anadirCoche();
    }
    
    public static Coche busquedaMatricula(String matricula){
    for (Coche car: listaCoches){
        if (matricula.equals(car.getMatricula()))
            return car;
    }
    return null;
    }
    
    public static ArrayList<Coche> getListaCoches(){
    return listaCoches;
    }
   
    
    public static void cargarCoche(){
    listaCoches = new ArrayList();
    ArrayList<Coche> listaaux = new ArrayList();
    boolean haysig = true;
    try{
    FileInputStream fis = new FileInputStream("Coches.bin");
    ObjectInputStream input = new ObjectInputStream(fis);
    while(haysig){
      Coche car = (Coche) input.readObject();
      if(car != null && car.getMatricula() != null)
         listaaux.add(car);
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
listaCoches.addAll(listaaux);

    }
   
    public static void actualizarFichero(){
     try {
            FileOutputStream fos = new FileOutputStream("Coches.bin");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Coche car : listaCoches){
                    try {
                        oos.writeObject(car);
                        System.out.println("saved");
                        System.out.println(car);
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
    
    private void anadirCoche(){
    listaCoches.add(this);
    actualizarFichero();
    }
    
    public boolean eliminarCoche(){
    if(listaCoches.remove(this)){
    actualizarFichero();
    return true;
            }
    return false;
     
    }

    @Override
    public String toString() {
        return "Matricula:   " + getMatricula()+ " | Color:  " + getColor() + " | Modelo:  " + getModelo()+ " | Carne asociado  " + Integer.toString(getHabitual().getCarnet().getNumeroId());
    }
    
    
}
