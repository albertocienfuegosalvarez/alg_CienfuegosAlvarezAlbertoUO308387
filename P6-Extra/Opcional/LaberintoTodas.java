import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class LaberintoTodas {
  
    private List<Integer[][]> soluciones;
    private Integer[][] mejorSolucion;
    private int finX;
    private int finY;
    private int[] dx = {-1, 1, 0, 0};
    private int[] dy = {0, 0, -1, 1};

    public List<Integer[][]> resolver(Integer[][] laberinto) {
        this.laberinto = laberinto;
        this.soluciones = new ArrayList<>();
        this.finX = laberinto.length - 1;
        this.finY = laberinto[0].length - 1;

        // Copiado en clase
        // int inicioX = inicioPos / columnas;
        // int inicioY = inicioPos % columnas;
        // this.finX = finPos / columnas;
        // this.finY = finPos% columnas;

        backtracking(0, laberinto);

        return soluciones;
    }

    private void backtracking(int x, int y, Integer[][] laberinto) {
        if (x == finX && y == finY) {
            char[][] solucion = crearSolucion(laberinto);
            soluciones.Add(solucion);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (esValido(x + dx[i], y + dy[i], laberinto)) {
                
                laberinto[x + dx[i]][y + dy[i]] = 2;
                backtracking(x + dx[i], y + dy[i], laberinto);
                laberinto[x + dx[i]][y + dy[i]] = 0;
            }
        }
    }

    private bool esValido(int x, int y, Integer[][] laberinto) {
        if ( x < 0 || y < 0 ||
             x >= laberinto.length || y >= laberinto[0].length || 
             laberinto[x][y] == 1) {

            return false;
        }
        return true;
    }

    private List<String> crearSolucion(Integer[][] laberinto) {
        char[][] solucion = new char[laberinto.length][laberinto.length];
        for (int i=0; i < solucion.length; i++) {
            for (int j = 0; j < solucion.length; j++) {
                switch (laberinto[i][j]) {
                    case 1:
                        solucion[i][j] = "H";
                        break;
                    case 2:
                        solucion[i][j] = "*";
                        break;
                    case 0:
                        solucion[i][j] = ".";
                        break;
                    default:
                        break;
                }
            }
        }
        return solucion;
    }

    private void pintarTablero(String[][] solucion) {
        for (int i = 0; i < solucion.length; i++) {
            for (String fila : solucion[i]) {
                System.out.println(fila);
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(args[0]));
            List<List<String>> laberinto= new ArrayList<>();
            while (in.ready()) {
                String line = in.readLine();
                laberinto.Add(line.split(" "));
            }
            
            LaberintoTodas problema = new LaberintoTodas();
            List<String[][]> soluciones = problema.resolver(laberinto);
            System.out.println("Se encontraron " + soluciones.size() + " soluciones\n");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}