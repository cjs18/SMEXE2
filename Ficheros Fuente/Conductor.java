package practica;

/* Description of the class
It's used to manage case files and a static list that contains all the case files in the system,
also has methods for Loading and Updating a binary file that stores this information
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
    
    //Empty constructor
    private Conductor(){}

    //This method is a constructor, it creates a Conductor object and add it to the list of drivers. 
    public Conductor(String nombre,String apellidos,String direccion,String email,boolean gafas){
	    this.nombre = nombre;
	    this.apellidos = apellidos;
	    this.direccion = direccion;
	    this.email = email;
	    this.carnet = new Carnet(gafas);
	    anadirConductor();
    }

    // GETTERS SETTERS

    //Gets the nombre variable value from the object
    public String getNombre() {
        return nombre;
    }

    //Set the nombre variable value for the object
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    //Gets the apellidos variable value from the object
    public String getApellidos() {
        return apellidos;
    }

    //Set the apellidos variable value for the object
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }

    //Gets the direccion variable value from the object
    public String getDireccion() {
        return direccion;
    }

    //Set the direccion variable value for the object
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    //Gets the email variable value from the object
    public String getEmail() {
        return email;
    }

    //Set the email variable value for the object
    public void setEmail(String email){
        this.email = email;
    }

    //Gets the carnet variable value from the object
    public Carnet getCarnet() {
        return carnet;
    }

    //Set the carnet variable value for the object
    public void setCarnet(Carnet carnet){
        this.carnet= carnet;
    }

    //Gets the value of the static list listaConductores
    public static ArrayList <Conductor> getListaConductores(){
    	return listaConductores;
    }
    
    //END OF GETTERS AND SETTERS

    //METHODS

    //This method receives an integer as parameter and searches for a driver in the static list with that license number
    public static Conductor busquedaCarnet(int ncarnet){
	    for (Conductor con: listaConductores){
	        if (ncarnet == con.getCarnet().getNumeroId())
	            return con;
	    }
	    return null;
    }
    
    //This method is used to add the object itself to the static list and after update the file
    private void anadirConductor(){
	    listaConductores.add(this);
	    actualizarFichero();
    }

    //This method is used to remove the object itself from the static list and after update the file returning value true if removed successfully
    public boolean eliminarConductor(){
	    if(listaConductores.remove(this)){
	    	actualizarFichero();
	    	return true;
	    }
	    return false;
    }
    
    //This method searche for a driver with a given registration plate and if not found it returns an empty driver
    public static Conductor busquedaMatricula(String mat){
	    ArrayList <Coche> lista1 = Coche.getListaCoches();
	    ArrayList <Camion> lista2 = Camion.getListaCamiones();
	    if(lista1.contains(Coche.busquedaMatricula(mat))){
	        return Coche.busquedaMatricula(mat).getHabitual();
	    }
	    else if(lista2.contains(Camion.busquedaMatricula(mat))){
	        return Camion.busquedaMatricula(mat).getHabitual();
	    }
	    return new Conductor();
    }
    
    //This method loads the list of drivers from the binary file
    public static void cargarConductor(){
	    listaConductores = new ArrayList();
	    ArrayList<Conductor> listaaux = new ArrayList<>();
	    boolean haysig = true;
	    try{
		    FileInputStream fis = new FileInputStream("Conductores.bin");
		    ObjectInputStream input = new ObjectInputStream(fis);
		    while(haysig){
		      	Conductor con = (Conductor) input.readObject();
		      	if(con != null){
		        	listaaux.add(con);
		      	}
		      	else{
		        	haysig = false;
		      	}
		    }
	    }catch (FileNotFoundException e) {
	           e.printStackTrace();
	    }catch(IOException){
	    	System.out.println(e.printStackTrace());
	    }catch(ClassNotFoundException e){
	    	e.printStackTrace();
	    }
		listaConductores.addAll(listaaux);
	}
    
    //This method is used to update the binary file every time we change the static list
    public static void actualizarFichero(){
     	try{
	        FileOutputStream fos = new FileOutputStream("Conductores.bin");
	        try {
	        	ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Conductor con : listaConductores){
                    try{
                        oos.writeObject(con);
                        System.out.println("saved");
                    }catch (NotSerializableException e) {
                        System.out.println("Objeto no serializable");
                        e.printStackTrace();
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }catch (FileNotFoundException e) {
            e.printStackTrace();
        	}
    	}
    }    

    //We override the method equals in order to use comparisons
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!obj) {
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

    //We override the toString method to show the information of the driver
    @Override
    public String toString(){
        return "Numero de Carnet: " + Integer.toString(getCarnet().getNumeroId())+ " | Nombre:  " + getNombre() + " | Apellidos:  " 
        		+ getApellidos()+ " | Direccion:  " + getDireccion() + " |  Email:   "+ getEmail();
    }
    
}
