import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class LaberintoTodas {

    private List<Character[][]> soluciones;
    private Character[][] mejorSolucion;
    private int mejorNPasos;
    private int inicioX;
    private int inicioY;
    private int finX;
    private int finY;
    private final int[] dx = {-1, 1, 0, 0};
    private final int[] dy = {0, 0, -1, 1};

    public List<Character[][]> resolver(Integer[][] laberinto, int inicio, int fin) {
        this.soluciones = new ArrayList<>();
        this.mejorSolucion = null;
        this.mejorNPasos = Integer.MAX_VALUE;

        int columnas = laberinto[0].length;
        this.inicioX = inicio / columnas;
        this.inicioY = inicio % columnas;
        this.finX = fin / columnas;
        this.finY = fin % columnas;

        pintarLaberintoInicial(laberinto, inicio, fin);

        laberinto[inicioX][inicioY] = 2;
        backtracking(inicioX, inicioY, 0, laberinto);
        laberinto[inicioX][inicioY] = 0;

        return soluciones;
    }

    private void backtracking(int x, int y, int nPasos, Integer[][] laberinto) {

        // Caso base: llegar a la ultima casilla
        if (x == finX && y == finY) {
            Character[][] solucion = crearSolucion(laberinto);
            soluciones.add(solucion);
            pintarSolucion(solucion, nPasos);

            if (nPasos < mejorNPasos) {
                this.mejorNPasos = nPasos;
                this.mejorSolucion = solucion;
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (esValido(x + dx[i], y + dy[i], laberinto)) {
                
                laberinto[x + dx[i]][y + dy[i]] = 2;
                backtracking(x + dx[i], y + dy[i], nPasos + 1, laberinto);
                laberinto[x + dx[i]][y + dy[i]] = 0;
            }
        }
    }

    private boolean esValido(int x, int y, Integer[][] laberinto) {
        if ( x < 0 || y < 0 ||
             x >= laberinto.length || y >= laberinto[0].length || 
             laberinto[x][y] == 1 || laberinto[x][y] == 2) {

            return false;
        }
        return true;
    }

    private Character[][] crearSolucion(Integer[][] laberinto) {
        Character[][] solucion = new Character[laberinto.length][laberinto[0].length];
        for (int i=0; i < solucion.length; i++) {
            for (int j = 0; j < solucion[0].length; j++) {
                switch (laberinto[i][j]) {
                    case 1:
                        solucion[i][j] = 'H';
                        break;
                    case 2:
                        solucion[i][j] = '*';
                        break;
                    case 0:
                        solucion[i][j] = '.';
                        break;
                    default:
                        break;
                }
            }
        }
        return solucion;
    }

    private void pintarSolucion(Character[][] solucion, int nPasos) {
        System.out.println("SOLUCIÓN ENCONTRADA CON " + nPasos + " PASOS");
        for (int i = 0; i < solucion.length; i++) {
            for (int j = 0; j < solucion[0].length; j++) {
                System.out.print(solucion[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    private void mostrarMejorSolucion() {
        if (soluciones.size() == 0)
            return;
        System.out.println("LA MEJOR SOLUCIÓN TIENE " + mejorNPasos + " PASOS");
        pintarSolucion(mejorSolucion, mejorNPasos);
    }

    private void pintarLaberintoInicial(Integer[][] laberinto, int inicio, int fin) {
        System.out.println("EL LABERINTO ES INICIALMENTE DEL SIGUIENTE MODO:");
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[0].length; j++) {
                System.out.print(laberinto[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("El objetivo es ir desde la posición " + inicio+ " a la posición " + fin);
    }


    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(args[0]));
            List<Integer[]> laberintoData = new ArrayList<>();
            String linea;

            while ((linea = in.readLine()) != null) {
                if (linea.isEmpty() || linea.isBlank())
                    continue;
                String[] numeros = linea.trim().split("\\s+");
                Integer[] fila = new Integer[numeros.length];

                for (int i = 0; i < fila.length; i++) {
                    if (numeros[i].isEmpty())
                        continue;
                    fila[i] = Integer.valueOf(numeros[i]);
                }

                laberintoData.add(fila);
            }
            
            in.close();

            int filas = laberintoData.size();
            int columnas = laberintoData.get(0).length;
            Integer[][] laberinto = new Integer[filas][columnas];

            for (int i = 0; i < laberintoData.size(); i++) {
                laberinto[i] = laberintoData.get(i);
            }

            int inicio = Integer.valueOf(args[1]);
            int fin = Integer.valueOf(args[2]);
            
            LaberintoTodas problema = new LaberintoTodas();
            List<Character[][]> soluciones = problema.resolver(laberinto, inicio, fin);

            System.out.println("SE ENCONTRARON " + soluciones.size() + " SOLUCIONES");
            problema.mostrarMejorSolucion();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}