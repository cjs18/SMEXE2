package Practica;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.time.LocalDate;
import java.io.*;
/**
 *
 * @author Franco_Viggiano
 */
public class Denuncia implements java.io.Serializable{
    private int codigoDenuncia;
    private LocalDate fechaHora;
    private static int codigo;
    private String causa;
    private double importe;
    private int numeroCarnet;
   
   
    public Denuncia(int año, int mes, int dia, String causa, double importe,int numeroCarnet){
        this.fechaHora = LocalDate.of(año,mes,dia);
        this.causa = causa;
        this.importe = importe;
        this.numeroCarnet = numeroCarnet;
        asignarCodigo(); 
    }
    
    public Denuncia(int año,int mes,int dia,String causa, double importe,String matricula){
    this.fechaHora = LocalDate.of(año,mes,dia);
    this.causa = causa;
    this.importe = importe;
    this.numeroCarnet = Conductor.busquedaMatricula(matricula).getCarnet().getNumeroId();
    asignarCodigo();
    }
    
    public Denuncia(Denuncia denuncia){
        this.codigoDenuncia = denuncia.getCodigo();
        this.fechaHora = denuncia.getFecha();
        this.causa = denuncia.getCausa();
        this.importe= denuncia.getImporte(); 
        this.numeroCarnet = denuncia.getNumeroCarnet(); 
    }
    public Denuncia(){
    
    }

    private void asignarCodigo(){
    codigoDenuncia = codigo++;
    actualizarCodigo();
    }

    
    
    //GETTERS SETTERS
    public LocalDate getFecha(){
   return this.fechaHora;
    }
    
    public int getCodigoDenuncia(){
    return codigoDenuncia;
    }
    
    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public double getImporte() {
        return importe;
    }
    public void setImporte(double importe) { //Cuidado error importe negativo
        if (importe>0)
        this.importe = importe;
    }
    
    public int getNumeroCarnet(){
        return numeroCarnet;
    }
    
    public static void setCodigo(int i){
    codigo = i;
    }
    
    public static int getCodigo(){
    return codigo;
    }

    public static void cargarDenuncia(){
    int aux;
    try {
         FileInputStream fileIn = new FileInputStream("Denuncia.bin");
         BufferedInputStream in = new BufferedInputStream(fileIn);
         DataInputStream dis = new DataInputStream(in);
         aux = dis.readInt();
         setCodigo(aux);
         in.close();
         fileIn.close();
      } catch (IOException i){}
    }
    
    public static void actualizarCodigo(){
    try {
         FileOutputStream fileOut =
         new FileOutputStream("Denuncia.bin");
         BufferedOutputStream out = new BufferedOutputStream(fileOut);
         DataOutputStream dos = new DataOutputStream(out);
         dos.writeInt(getCodigo());
         out.close();
         fileOut.close();
      } catch (IOException i) {
      }
    }

}
