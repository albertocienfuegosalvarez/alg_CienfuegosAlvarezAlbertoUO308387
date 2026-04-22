import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class AlmacenajeContenedoresRyP {

    private int capacidadC;
    private Integer[] conjuntoS;
    private int mejorK; // número minimo de contenedores
    private List<List<Integer>> mejorDistribucion;
    private int llamadasRecursivas;

    public AlmacenajeContenedoresRyP(int capacidadC, Integer[] conjuntosS) {
        this.capacidadC = capacidadC;
        this.conjuntoS = conjuntosS;
        this.mejorK = conjuntosS.length;        // ponemos la peor distribucion
        this.llamadasRecursivas = 0;
    }

    public void resolver() {
        Arrays.sort(conjuntoS, Comparator.reverseOrder());     // Ordenar descendente para reducir el numero de llamadas recursivas
        List<List<Integer>> contenedores = new ArrayList<>();
        backtraking(0, contenedores, calcularSumaTotal());
        generarSolucion();
    }

    private int calcularSumaTotal() {
        int suma = 0;
        for (int i=0; i<conjuntoS.length; i++) {
            suma += conjuntoS[i];
        }
        return suma;
    }

    private void backtraking(int indexObject, List<List<Integer>> contenedores, int sumaRestante) {
        llamadasRecursivas++;

        // LowerBound
        // Calcular el numero minimo TEORICO de contenedores ADICIONALES necesarios
        int lowerBound = (sumaRestante + capacidadC - 1) / capacidadC;
        if (contenedores.size() + lowerBound >= mejorK) return;

        // Caso base: compruebo que he llegado al final y por tanto he llegado a una solucion
        if (indexObject == conjuntoS.length) {
            // Comprobamos si esta nueva solucion es mejor
            if (contenedores.size() < mejorK) {
                mejorK = contenedores.size();
                mejorDistribucion = copiar(contenedores);
            }
            return;     // acabar la ejecucion del backtraking
        }

        // Podamos: si el size de contenedores es >= mejor -> paramos
        // if (contenedores.size() >= mejorK) return;       // ya no es necesario

        // Probar a meter en contenedores existentes
        for (int i= 0; i <contenedores.size(); i++) {
            if (sum(contenedores.get(i)) + conjuntoS[indexObject] <= capacidadC) {
                // Avanzar
                contenedores.get(i).add(conjuntoS[indexObject]);
                backtraking(indexObject + 1, contenedores, sumaRestante - conjuntoS[indexObject]);
                // Retroceder
                contenedores.get(i).remove(contenedores.get(i).size() - 1);
            }
        }
        
        if (contenedores.size() < mejorK - 1) {
            // Intentar meterlo en un nuevo contenedor
            List<Integer> nuevoContenedor = new ArrayList<>();
            nuevoContenedor.add(conjuntoS[indexObject]);
            contenedores.add(nuevoContenedor);
            // Avanzo
            backtraking(indexObject + 1, contenedores, sumaRestante - conjuntoS[indexObject]);
            // Retroceso
            contenedores.remove(contenedores.size() - 1);
        }
    }

    private List<List<Integer>> copiar(List<List<Integer>> contenedores) {
        List<List<Integer>> copia = new ArrayList<>();
        for (List<Integer> i : contenedores) {
            copia.add(new ArrayList<>(i));  // copia sin referencia
        }
        return copia;
    }

    private int sum(List<Integer> contendores) {
        int total = 0;
        for (Integer i : contendores) {
            total += i;
        }
        return total;
    }

    private void generarSolucion() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("solucion.txt"))) {
            
            out.write("Lista de contenedores y objetos contenidos:\n");
            for (int i = 0; i < mejorK; i++) {
                out.write(String.format("Contenedor %d: %s\n", i + 1, getDistribucionDe(i)));
            }
            out.write(String.format("El número de contenedores necesario es %d.\n", mejorK));

        }catch (Exception e) {
            e.printStackTrace();

        }
    }
    
    private String getDistribucionDe(int contenedor) {
        String distribucion = "";
        for (int i = 0; i < mejorDistribucion.get(contenedor).size(); i++) {
            distribucion += mejorDistribucion.get(contenedor).get(i) + " ";
        }
        return distribucion;
    }

    public void imprimirLlamadas() {
        System.out.println("Numero de llamadas recursivas: " + llamadasRecursivas);
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new FileReader(args[0]));
            int capacidadC = sc.nextInt();
            sc.nextLine();  // para que salte a la siguiente porque sino se queda en la de la capacidad
            String[] conjuntosString = sc.nextLine().split(" ");
            Integer[] conjuntosInteger = new Integer[conjuntosString.length];
            int i = 0;
            for (String s: conjuntosString) {
                conjuntosInteger[i++] = Integer.parseInt(s);
            }
            
            AlmacenajeContenedoresRyP problema = new AlmacenajeContenedoresRyP(capacidadC, conjuntosInteger);
            problema.resolver();
            problema.imprimirLlamadas();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}