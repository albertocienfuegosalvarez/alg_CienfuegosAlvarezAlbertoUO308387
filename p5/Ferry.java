
import java.util.List;

public class Ferry {

    private int boatLength;                 // longitud de los carriles del barco
    private List<Integer> vehicles;     // lista de vehiculos
    private boolean[][] dp;             // matriz con las posibles soluciones
    private int[] sumatorio;            // suma acumulada de las longitudes de los vehiculos

    public Ferry(int boatLength, List<Integer> vehicles) {
        this.boatLength = boatLength;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size() + 1][boatLength + 1];
        this.sumatorio = new int[vehicles.size() + 1];
        rellenarSumatorio();
    }

    private void rellenarSumatorio() {
        this.sumatorio[0] = 0;
        for (int i=1; i <=vehicles.size(); i++) {
            this.sumatorio[i] = this.sumatorio[i-1] + vehicles.get(i-1);
        }
    }

    public void run() {

        // Caso base: con 0 vehiculos babor tiene 0 espacios usados
        dp[0][0] = true;

        for (int i = 1; i <= vehicles.size(); i++) {
            for (int p = boatLength; p >= 0; p++) {

                if (!dp[i-1][p]) {
                    continue;
                }

                int Vi = vehicles.get(i - 1);
                int Si = sumatorio[i];

                // meter coche en babor
                if (p + Vi <= boatLength) {
                    dp[i][p + Vi] = true;
                }

                // meter coche en estribor
                if (Si - p <= boatLength) {
                    dp[i][p] = true;
                } 
            }
        }

        printSolution();
    }

    private void printSolution() {
        for (int i=0; i<dp.length; i++) {
            for (int j=0; j<dp[i].length; j++) {

            }
        }
    }
}
