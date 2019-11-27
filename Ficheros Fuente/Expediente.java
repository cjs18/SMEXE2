package practica;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/* Description of the class
It's used to manage case files and a static list that contains all the case files in the system,
also has methods for Loading and Updating a binary file that stores this information
 */


/* LANGUAGE NOTES
As we can see the variables and methods names are in Spanish so in order to make it more accesible
but not changing the names in order of the other classes to work we will provide some translations
so more people can understand:

Expediente = Case file
Alegacion = Allegation
Denuncia = Complaint
Notificacion = Notification // notifPrinc (meaning notificacionPrincipal) = Main notification
Estado = Status
listaExpedientes = case files list
numExp (numeroExpediente) = case file number
Pagado = Paid
Fecha = Date
Importe = Amount
Causa = Cause
Codigo = Code
Eliminar = Remove
Anadir (Añadir) = Add
Busqueda = Search
Comprobar =  Check
Conductor = Driver
Fichero = File
Cargar = Load
Actualizar = Update
Pagar = Pay
Aceptar = Accept
EnvioMensual = Monthly Shipping
SOBRESEIDO = DISMISSED
EJECUCION = EXECUTION
SANCIONADO = SANCTIONED
haysig (Hay siguiente) = There is another

*/

public class Expediente {
    private Alegacion alegacionPrincipal;
    private Denuncia denuncia;
    private Notificacion notifPrinc;
    private Estado estado;
    private static ArrayList <Expediente> listaExpedientes;
    private int numExp;
    private boolean pagado;
    
    //This Method is a constructor, it creates a Expediente object. and add it to a list of dossiers. 
    
    public Expediente(Denuncia denuncia){
        this.notifPrinc = new Notificacion(denuncia.getFecha(),denuncia.getImporte(),null,denuncia.getCausa());
        numExp = denuncia.getCodigo();
        this.estado=Estado.EJECUCION;
        this.denuncia = denuncia;
        anadirExpediente();
    }
    
    // GETTERS SETTERS

    //Set the denuncia variable value for the object
    public void setDenuncia(Denuncia denuncia){
        this.denuncia = new Denuncia(denuncia);
    }

    //Set the alegacionPrincipal variable value for the object
    public void setAlegacion(String defensa){
        this.alegacionPrincipal = new Alegacion(defensa);
    }

    //Gets the alegacionPrincipal variable value from the object
    public Alegacion getAlegacionPrincipal() {
        return alegacionPrincipal;
    }

    //Gets the denuncia variable value from the object
    public Denuncia getDenuncia() {
        return denuncia;
    }

    //Gets the notiPrinc variable value from the object
    public Notificacion getNotifPrinc() {
        return notifPrinc;
    }

    //Gets the estado variable value from the object
    public Estado getEstado() {
        return estado;
    }

    //Gets the numExp variable value from the object
    public  int getNumExp() {
        return numExp;
    }
    
    //Gets the value of the static list listaExpedientes
    public static ArrayList<Expediente> getListaExpedientes() {
        return listaExpedientes;
    }

    //Sets the pagado variable value for the object
    public void setPagado(){
        this.pagado=true;
    }

    //Gets the pagado variable value from the object
    public boolean getPagado(){
        return this.pagado;
    }

    //END OF GETTERS AND SETTERS

    //METHODS

    // This method deletes the allegation from the object
    public void eliminarAlegacion(){
        this.alegacionPrincipal = null;
    }
    
    //This method adds the object itself to the static list and updates the file 
    private void anadirExpediente(){
        listaExpedientes.add(this);
        actualizarFichero();
    }
    
    //This method searchs in the static list for a case file with the number "nexp" and returns the object if found or null if not
    public static Expediente busquedaExpediente(int nexp){
	    for (Expediente e: listaExpedientes){
	        if (nexp == e.getNumExp())
	            return e;
	    }
	    return null;
	}
    
    //This method should check the case file and change the status to "SANCIONADO" if the deadline for allegations finished
    //Or would change it to "SOBRESEIDO" if the allegations presented are accepted
    //If nothing of the above happened it will stay as "EJECUCION"
    public void comprobarAlegacion() {
        LocalDate  date1 = LocalDate.now();  //Local date in order to check
        LocalDate  date2 = denuncia.getFecha();   //Date of the complaint
        if (date1.isAfter(date2)) {
            this.estado = Estado.SANCIONADO;
        } 
        else{
            if (alegacionPrincipal.getAceptada()){
                this.estado = Estado.SOBRESEIDO;                
            }
            else {
                this.estado = Estado.EJECUCION;
            }
        }
    }
        
    //This method would be used to send the list of inconcluded case files the first day of the month to the main traffic office.
    //It only returns the list of case files with status "SOBRESEIDO" or "SANCIONADO"
    public ArrayList envioMensual() {
        ArrayList<Expediente> expedientesInconclusos = null;
        LocalDate now = LocalDate.now();
        if (now.getDayOfMonth() == 1) {
            for (Expediente e : listaExpedientes) {
                if (e.estado.equals(Estado.SOBRESEIDO) || e.estado.equals(Estado.SANCIONADO)) {
                    expedientesInconclusos.add(e);
                }
            }
        return expedientesInconclusos;
    }
    
    //This methos is used to update the file that stores the list of case files    
    public static void actualizarFichero(){
        try {
            FileOutputStream fos = new FileOutputStream("Expedientes.bin");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Expediente e : listaExpedientes){
                    try {
                        oos.writeObject(e);
                        System.out.println("Saved");
                    } catch (NotSerializableException err) {
                        System.out.println("Non serializable object");
                        err .printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //This method gets a driving license number and returns a list of all the case files related to that driver
    public static ArrayList<Expediente> buscarExpedienteConductor(int numCarnet){
        ArrayList<Expediente> files= new ArrayList();
        for(Expediente e : listaExpedientes){
        	System.out.println(e.getDenuncia().getNumeroCarnet());
        	if(e.getDenuncia().getNumeroCarnet() == numCarnet){
          		files.add(e);
          	}
        }
        System.out.println(files);
        return files;
    }

    //This method sets tha paid state to true and changes the status to "SOBRESEIDO"
    public void pagarMulta(){
	    setPagado();
	    this.estado = Estado.SOBRESEIDO;
   	}

   	//This method is used to load the case file list from the file
    public static void cargarExpediente(){
	    listaExpedientes = new ArrayList(); 
	    ArrayList<Expediente> listfiles = new ArrayList<>();
	    boolean haysig = true;
	    try{
		    FileInputStream file = new FileInputStream("Expedientes.bin");
		    ObjectInputStream input = new ObjectInputStream(file);
		    while(haysig){
			    Expediente e = (Expediente) input.readObject();
			    if(e != null){
			    	listfiles.add(e);
			    }
			    else{
			    	haysig = false;	   
			    }
			}
			file.close();
		}catch (FileNotFoundException e) {
            e.printStackTrace();
    	}catch(IOException e){
   			System.out.println(e.printStackTrace());
		}catch(ClassNotFoundException e){
   			System.out.println("Error");
		}
    	listaExpedientes.addAll(listaaux);
    }

    //Method to return the number of the case file object as a string
    @Override
    public String toString() {
        return "Case file nº ["+ getNumExp() + "]";
    }

    //This method is used to accept the allegation and set the status to "SOBRESEIDO"
    public void aceptarAlegacion(){
    	this.estado=Estado.SOBRESEIDO;
    }
  
}