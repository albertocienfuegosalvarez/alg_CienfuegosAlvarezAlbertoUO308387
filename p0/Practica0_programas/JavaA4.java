package p0 ;
import java.util.ArrayList;
public class JavaA4
{

public static void primosA4(int n)
{
   ArrayList<Boolean> listaNumeros = new ArrayList<Boolean>();
   for (int i=0; i<= n; i++) {
      listaNumeros.add(true);
   }

   int x=2;
   while (x*x <= n) {
      if (listaNumeros.get(x)) {
         int paso = 2*x;
         while (paso <= n) {
            listaNumeros.set(paso, false);
            paso = paso + x;
         }
      }
      x = x + 1;
   }

   ArrayList<Integer> lSal = new ArrayList<Integer>();
   int contPrimos=0;
   for (int i=2; i<= n; i++) {
      if (listaNumeros.get(i)) {
         lSal.add(i);
         contPrimos++;
      }
   }

   System.out.println("Hay "+contPrimos+" primos hasta "+n);

}


public static void main (String arg [] )
{

System.out.println("TIEMPO EN JAVA DEL ALGORITMO A4");

long t1,t2;                  //obligatoriamente de tipo long para no desbordar
// la toma de tiempos en Java se ver con ms profundidad en la sesin siguiente


for (int n=5000; n<=1000000; n*=2)
   {
    t1=System.currentTimeMillis();	// milisegundos (sin decimales) 

    primosA4(n);

    t2=System.currentTimeMillis();	

    System.out.println(t1+"///"+t2);

    System.out.println ("n="+n+"**** tiempo = "+(t2-t1)+" milisegundos \n");
    
   }
} // de main

}  // de clase

