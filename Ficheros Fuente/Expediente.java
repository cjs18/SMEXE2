package Practica;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Franco_Viggiano
 */
public class Expediente implements java.io.Serializable {
    private Alegacion alegacionPrincipal;
    private Denuncia denuncia;
    private Notificacion notifPrinc;
    private Estado estado;
    private static ArrayList <Expediente> listaExpedientes;
    private int numExp;
    private boolean pagado;
    
    //Hacer un ArrayList de expedientes
    
    public Expediente(Denuncia denuncia){
    this.notifPrinc = new Notificacion(denuncia.getFecha(),denuncia.getImporte(),null,denuncia.getCausa());
    numExp = denuncia.getCodigo();
    this.estado=Estado.EJECUCION;
    this.denuncia = denuncia;
    anadirExpediente();
    }
    
    // GETTERS SETTERS
    public void setDenuncia(Denuncia denuncia){
    this.denuncia = new Denuncia(denuncia);
    }
    

    
    public void setAlegacion(String defensa){
    this.alegacionPrincipal = new Alegacion(defensa);
    }

    public Alegacion getAlegacionPrincipal() {
        return alegacionPrincipal;
    }

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public Notificacion getNotifPrinc() {
        return notifPrinc;
    }

    public Estado getEstado() {
        return estado;
    }

    public  int getNumExp() {
        return numExp;
    }
    
    public void eliminarAlegacion(){
    this.alegacionPrincipal = null;
    }

    
    private void anadirExpediente(){
    listaExpedientes.add(this);
    actualizarFichero();
    }
    
    
    public static ArrayList<Expediente> getListaExpedientes() {
        return listaExpedientes;
    }

    public void setPagado(){
     this.pagado=true;
    }
    public boolean getPagado(){
    return this.pagado;
    }

    //Metodos
    
    public static Expediente busquedaExpediente(int nexp){
    for (Expediente e: listaExpedientes){
        if (nexp == e.getNumExp())
            return e;
    }
    return null;
    }
    
    public void comprobarAlegacion() {
        LocalDate aux1 = LocalDate.now();  //Fecha a la hora de comprobar
        LocalDate aux2 = denuncia.getFecha();   //Fecha de la denuncia
        if (aux1.isAfter(aux2)) {
            this.estado = Estado.SANCIONADO;
        } else if (aux1.isBefore(aux2) || aux1.isEqual(aux2)) {
            if (alegacionPrincipal.getAceptada()) {
                this.estado = Estado.SOBRESEIDO;                
            } else {
                this.estado = Estado.EJECUCION;
            }
        }
    }
    
    
    public ArrayList envioMensual() {
        ArrayList<Expediente> expedientesInconclusos = null;
        LocalDate ahora = LocalDate.now();
        if (ahora.getDayOfMonth() == 1) {
            for (Expediente e : listaExpedientes) {
                if (e.estado.equals(Estado.SOBRESEIDO) || e.estado.equals(Estado.SANCIONADO)) {
                    expedientesInconclusos.add(e);
                }
            }
           
        return expedientesInconclusos;
    }
        return expedientesInconclusos;
    }
       
    public static void actualizarFichero(){
        try {
            FileOutputStream fos = new FileOutputStream("Expedientes.bin");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Expediente e : listaExpedientes){
                    try {
                        oos.writeObject(e);
                        System.out.println("saved");
                    } catch (NotSerializableException err) {
                        System.out.println("Objeto no serializable");
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
   public static ArrayList<Expediente> buscarExpedienteConductor(int numCarnet){
       ArrayList<Expediente> lista= new ArrayList();
       for(Expediente e : listaExpedientes){
           System.out.println(e.getDenuncia().getNumeroCarnet());
       if(e.getDenuncia().getNumeroCarnet() == numCarnet)
          lista.add(e);
       }
       System.out.println(lista);
       return lista;
       }

   public void pagarMulta(){
       setPagado();
       this.estado = Estado.SOBRESEIDO;
   }
    public static void cargarExpediente(){
    listaExpedientes = new ArrayList();
    ArrayList<Expediente> listaaux = new ArrayList<>();
    boolean haysig = true;
    try{
    FileInputStream fis = new FileInputStream("Expedientes.bin");
    ObjectInputStream input = new ObjectInputStream(fis);
    while(haysig){
      Expediente e = (Expediente) input.readObject();
      if(e != null)
         listaaux.add(e);
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
    listaExpedientes.addAll(listaaux);
    }

    @Override
    public String toString() {
        return "Expediente nº ["+ getNumExp() + "]";
    }

    public void aceptarAlegacion(){
    this.estado=Estado.SOBRESEIDO;
   ;
    
    }
    
    
}