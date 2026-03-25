import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class AlmacenajeContenedores {

    private int capacidadC;
    private Integer[] conjuntoS;
    private int mejorK; // número minimo de contenedores
    private List<List<Integer>> mejorDistribucion;
    private int llamadasRecursivas;

    public AlmacenajeContenedores(int capacidadC, Integer[] conjuntosS) {
        this.capacidadC = capacidadC;
        this.conjuntoS = conjuntosS;
        this.mejorK = conjuntosS.length;
        this.llamadasRecursivas = 0;
        rellenarPeorSolucion();
    }

    private void rellenarPeorSolucion() {               // TODO ESTO CREO QUE NO HACE FALTA...
        this.mejorDistribucion = new ArrayList<>();
        for (int i=0; i < conjuntoS.length; i++) {
            mejorDistribucion.add(new ArrayList<Integer>().add(conjuntosS[i]));
        }
    }

    public void resolver() {
        Arrays.sort(conjuntoS, Comparator.reverseOrder());     // Ordenar descendente para reducir el numero de llamadas recursivas
        List<List<Integer>> contenedores = new ArrayList<>();
        backtraking(0, contenedores);
        mostrarSolucion();
    }

    private void backtraking(int indexObject, List<List<Integer>> contenedores) {
        llamadasRecursivas++;

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
        if (contenedores.size() >= mejorK) {
            return;
        }

        // Probar a meter en contenedores existentes
        for (int i= 0; i <contendores.size(); i++) {
            if (sum(contenedores.get(i)) + conjuntoS[indexObject] <= capacidadC) {
                // Avanzar
                contenedores.get(i).add(conjuntoS[indexObject]);
                backtraking(indexObject + 1, contendores);
                // Retroceder
                contenedores.get(i).removeLast();
               
            }
        }
        
        // Intentar meterlo en un nuevo contenedor
        List<Integer> nuevoContenedor = new ArrayList<>();
        nuevoContenedor.add(conjuntoS[indexObject]);
        contenedores.add(nuevoContenedor);
        // Avanzo
        backtraking(indexObject + 1, contenedores);
        // Retroceso
        contenedores.removeLast();
        
    }

    private List<List<Integer>> copiar(List<List<Integer>> contenedores) {
        List<List<Integer>> copia = new ArrayList<>();
        for (List<Integer> i : contenedores) {
            copia.add(i);
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

    private void mostrarSolucion() {
        System.out.println("Numero de llamadas recursivas: " + llamadasRecursivas);
        
        Scanner sc = new Scanner(new FileWrite("solucion.txt"));

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new FileReader(args[0]));
        int capacidadC = sc.nextInt();
        String[] conjuntosString = sc.nextLine().split(" ");
        Integer[] conjuntosInteger = new Integer(conjuntosString.length);
        int i = 0;
        for (String s: conjuntosString) {
            conjuntosInteger[i++] = Integer.parseInt(s);
        }
        
        new AlmacenajeContenedores(capacidadC, conjuntosInteger).resolver();    
    }   
}