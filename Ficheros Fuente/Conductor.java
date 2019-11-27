package Practica;




/**
 *
 * @authors Carlos Desco Parada
 */

/* LANGUAGE NOTES
As we can see the variables and methods names are in Spanish so in order to make it more accesible
but not changing the names in order of the other classes to work we will provide some translations
so more people can understand:

Conductor = Driver
Nombre = Name
Apellidos = Surnames
Direccion = Adress
Gafas = Glasses
listaConductores = Drivers list
Carnet (de conducir) = (Driving) license
Anadir = Add
Busqueda = Search
Cargar = Load
(Conductor) habitual = Usual (Driver)
Actualizar = Update
Fichero = File
Numero (Número) = Number
Matricula = Registration plate
Coche = Car
Camion = Truck

*/

import java.util.ArrayList;
import java.util.Objects;
import java.io.*;

public class Conductor implements java.io.Serializable{
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private Carnet carnet;
    private static ArrayList <Conductor> listaConductores;
    
    public Conductor(){super();}
    
    public Conductor(String nombre,String apellidos,String direccion,String email,boolean gafas){
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.direccion = direccion;
    this.email = email;
    this.carnet = new Carnet(gafas);
    anadirConductor();
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Carnet getCarnet() {
        return carnet;
    }
    public void setCarnet(Carnet carnet){
        this.carnet= carnet;
    }
    public static ArrayList <Conductor> getListaConductores(){
    return listaConductores;
    }
    
    public static Conductor busquedaCarnet(int ncarnet){
    for (Conductor con: listaConductores){
        if (ncarnet == con.getCarnet().getNumeroId())
            return con;
    }
    return null;
    }
    
    public void anadirConductor(){
    listaConductores.add(this);
    actualizarFichero();
    }
    
    public static Conductor busquedaMatricula(String mat){
    ArrayList <Coche> lista1 = Coche.getListaCoches();
    ArrayList <Camion> lista2 = Camion.getListaCamiones();
    if(lista1.contains(Coche.busquedaMatricula(mat)))
        return Coche.busquedaMatricula(mat).getHabitual();
    else if(lista2.contains(Camion.busquedaMatricula(mat)))
            return Camion.busquedaMatricula(mat).getHabitual();
    return new Conductor();
    }
    
    public static void cargarConductor(){
    listaConductores = new ArrayList();
    ArrayList<Conductor> listaaux = new ArrayList<>();
    boolean haysig = true;
    try{
    FileInputStream fis = new FileInputStream("Conductores.bin");
    ObjectInputStream input = new ObjectInputStream(fis);
    while(haysig){
      Conductor con = (Conductor) input.readObject();
      if(con != null)
         listaaux.add(con);
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
    
listaConductores.addAll(listaaux);

    }
    
    public static void actualizarFichero(){
     try {
            FileOutputStream fos = new FileOutputStream("Conductores.bin");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Conductor con : listaConductores){
                    try {
                        oos.writeObject(con);
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
        final Conductor other = (Conductor) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        if (!Objects.equals(this.carnet.getNumeroId(), other.carnet.getNumeroId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Numero de Carnet:   " + Integer.toString(getCarnet().getNumeroId())+ " | Nombre:  " + getNombre() + " | Apellidos:  " + getApellidos()+ " | Direccion:  " + getDireccion() + " |  Email:   "+ getEmail();
    }
    
    
    
    public boolean eliminarConductor(){
    if(listaConductores.remove(this)){
    actualizarFichero();
    return true;
            }
    return false;
    }
}
