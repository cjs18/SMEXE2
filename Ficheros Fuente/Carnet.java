
package Practica;


/**
 *
 * @author Carlos Desco Parada
 */
import java.time.LocalDate;
import java.io.*;

public class Carnet implements java.io.Serializable{
    private LocalDate fechaObtencion;
    private LocalDate fechaCaducidad;
    private boolean gafas;
    private int numeroId;
    private static int ultimo;
    
    public Carnet(boolean gafas){
    fechaObtencion = LocalDate.now();
    fechaCaducidad = fechaObtencion.plusYears(10);
    this.gafas = gafas;
    numeroId = ++ultimo;
    actualizarUltimo();
    }
    
    
    public static void setUltimo(int ultimo){
    Carnet.ultimo = ultimo;
    }
    
    

    public LocalDate getFechaObtencion() {
        return fechaObtencion;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public boolean isGafas() {
        return gafas;
    }

    public int getNumeroId() {
        return numeroId;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public void setGafas(boolean gafas) {
        this.gafas = gafas;
    }
    
    public static int getUltimo(){
    return ultimo;
    }
    
    public static void cargarCarnet(){
    int aux;
    try {
         FileInputStream fileIn = new FileInputStream("Carnet.bin");
         BufferedInputStream in = new BufferedInputStream(fileIn);
         DataInputStream dis = new DataInputStream(in);
         aux = dis.readInt();
         setUltimo(aux);
         in.close();
         fileIn.close();
      } catch (IOException i){}
    }
    
    public static void actualizarUltimo(){
    try {
         FileOutputStream fileOut =
         new FileOutputStream("Carnet.bin");
         BufferedOutputStream out = new BufferedOutputStream(fileOut);
         DataOutputStream dos = new DataOutputStream(out);
         dos.writeInt(getUltimo());
         out.close();
         fileOut.close();
      } catch (IOException i) {
      }
    }
    public boolean renovarCaducidad(){
        if (LocalDate.now().isAfter(getFechaCaducidad().minusMonths(3))){
            setFechaCaducidad(getFechaCaducidad().plusYears(10));
            return true;
        }
        return false;
    }
    
}
