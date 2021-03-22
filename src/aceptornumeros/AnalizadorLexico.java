package aceptornumeros;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
public class AnalizadorLexico {
    static String Q[];
    static String Σ [];
    static String q0;
    static String F[];
    static String [][] δ; //[filas][columnas] Q.length,Σ.length
    static List<String> Tokens=new ArrayList();
    static List<String> newtokens=new ArrayList();
    
    

    static String tipo=""; 
    
    
    /*public static void main (String [ ] args) throws IOException {
        main_lexico();
        main_sintactico();
    }*/
         void main_lexico() throws IOException{
           String Automata_txt = leerAutomata("src/aceptornumeros/AlexicoAuto.txt");
            init_Arreglos(Automata_txt);
            Algoritomo_automata();
        }

        static void Algoritomo_automata() throws IOException {
        String qini=q0;
        String Corridas = leerCorridas("src/aceptornumeros/ALexicoPro.txt");
        String Corrida[]=Corridas.split("\n");//Separa por lineas de codigo
        String Lineacaracteres[];
        List<String> token = new ArrayList();
        int contadorN=0;
        for (int i=0;i<Corrida.length;i++) {
            Lineacaracteres=separarLineaCodigo(Corrida[i]);//Manda linea de codigo String y regresa arreglo con caracteres en string
            try {
             while(/*!CompararFinal(F, qini) &&*/ contadorN<Lineacaracteres.length) {
                 String ultimoestadovalido=qini;//=0
                 System.out.println(""+qini);
                 String dato=Lineacaracteres[contadorN];//ñ
                 token.add(dato);//sp

                 if(!Arrays.asList(Σ).contains(dato)){ //Para ver si el dato de la corrida pertenece al alfabeto
                     String out="Al menos un elemento de la corrida " + (i + 1) + " no pertenece al alfabeto";
                     JOptionPane.showMessageDialog(null,"ERROR, Al menos un elemento de la entrada no pertenece al alfabeto");
                     System.out.println(out);
                 }
                 
                 //System.out.println(""+δ[indexQ(qini)][index(dato)]);
                 qini=δ[indexQ(qini)][index(dato)];//Se le resta 1 al qini se inicio en q1(el automata) 0,ñ 9
                 
                 if(!Arrays.asList(Q).contains(qini))//Si el valor de qini es f u otro
                    qini=ultimoestadovalido;
                 
                    if(CompararFinal(F, qini)&&(!qini.equals("1")&&!qini.equals("2")&&!qini.equals("6")&&!qini.equals("8")&&!qini.equals("10"))){
                        Tokens.add(qini);                        
                        token.clear();
                        if((qini.equals("100")))
                                if(qini.equals("100")){
                                JOptionPane.showMessageDialog(null,"Error, Analisis Falló");
                                Tokens.clear();
                                newtokens.clear();
                                System.exit(0);
                                }
                            qini=q0;
                        }else{
                        if((qini.equals("1")||qini.equals("2")||qini.equals("6")||qini.equals("8")||qini.equals("10"))&&contadorN==Lineacaracteres.length-1){
                             newtokens.add(qini);
                             Tokens.add(""+qini);
                             token.clear();
                             switch(qini){
                                 case "1": tipo="Es un numero entero";
                                     break;
                                 case "2": tipo="Es un numero real";
                                     break;
                                 case "6": tipo="Es un numero en notacion cientifica";
                                     break;
                                 case "8": tipo="Es un numero en notacion cientifica";
                                     break;
                                 case "10": tipo="Es un numero en hexadecimal";
                                     break;
                                 
                             }
                             JOptionPane.showMessageDialog(null,"Analisis Lexico Exitoso, "+tipo);
                        } 
                    }
                    
                  if((qini.equals("167")||qini.endsWith("159"))){
                    contadorN--;
                    token.remove(token.size()-1);
                 }
                 else{
                      if(ultimoestadovalido.equals("0")&&qini.equals("0")){
                      token.remove(token.size()-1);//remueve caracter de mas
                      
                  }
                 }  
                  contadorN++;
             }
            }catch (Exception e){
            } 
            token.clear();
            contadorN=0;
            qini=q0;
        }   
        
    }
    
        static String tokenlista(List lista, String valBusqueda){
            for(int i=0;i<lista.size();i++){
               if(lista.get(i).equals(valBusqueda))
                   return ""+(i+600);
            }
            return "";
        }
        static String tokenstring(List list){
            String tokenD="";
             for(int l=0;l<list.size();l++){
                        tokenD+=list.get(l);
                    }
            return tokenD;
        }

    static void init_Arreglos(String cadena) {//Carga los valores del txt del automata a los arreglos
        String renglones_cadena [];
        renglones_cadena=cadena.split("\n");//Hace un arreglo con loa string separados por \n
        Q=renglones_cadena[0].split(" ");
        Σ=renglones_cadena[1].split(" ");
        q0=renglones_cadena[2];
        F=renglones_cadena[3].split(" ");
         String δ_aux[][]=new String[Q.length][Σ.length];
         for(int i=0;i<Q.length;i++){
                for (int j=0;j<Σ.length;j++){
                    String caracteres_cadena[]=renglones_cadena[4+i].split(""+(char)9);
                    δ_aux[i][j] = caracteres_cadena[j];
                }
                
        }
         δ=δ_aux;
     }
    
    public static String leerAutomata(String archivo) throws FileNotFoundException, IOException {
        String cadena,cadena_ret="";
        System.out.println(archivo);
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            cadena_ret=cadena_ret+cadena+"\n";
        }
        b.close();
        System.out.println("cadenaret: "+cadena_ret);
        return cadena_ret;
    }
    
    public static String leerCorridas(String archivo) throws FileNotFoundException, IOException {
        String cadena,cadena_ret="";
        System.out.println(archivo);
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            //System.out.println(cadena);
            cadena_ret=cadena_ret+cadena+"\n";
        }
        b.close();
        return cadena_ret;
    }
    
    static Integer index(String buscarindice){
        int i=0;
         for(; i<Σ.length; i++){
             if(Σ[i].equals(buscarindice))
                  return (i);
         }
         return (i);
     }
    static Integer indexQ(String buscarindice){
        int i=0;
         for(; i<Q.length; i++){
             if(Q[i].equals(buscarindice))
                  return (i);
         }
         return (i);
     }
    private static boolean CompararFinal(String [] F, String qini) {
        boolean retorno=false;
        for(int i=0;i<F.length;i++){
            String qf=F[i];
            if (qini.equals(qf))
                retorno=true;
        }
        return retorno;
    }

    private static String [] separarLineaCodigo(String linea) {
        List<Character> caractereslist = new ArrayList();
        for(int i=0;i<linea.length();i++){
            caractereslist.add(linea.charAt(i));
        }
        caractereslist.add(' ');
        String lineax[]=new String[caractereslist.size()-1];
        for(int j=0;j<caractereslist.size()-1;j++){
            lineax[j]=""+caractereslist.get(j);
        }
        for(int i=0;i<lineax.length;i++)
            System.out.println("lineax "+lineax[i]);
        return lineax;
    }
}
