import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Ferry {

    private int boatLength;                 // longitud de los carriles del barco
    private List<Integer> vehicles;     // lista de vehiculos
    private boolean[][] dp;             // matriz con las posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos
    private int travelVehicles;

    public Ferry(int boatLength, List<Integer> vehicles) {
        this.boatLength = boatLength;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size() + 1][boatLength + 1];
        this.sumatorio = new int[vehicles.size() + 1];
        this.travelVehicles = 0;
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
            boolean viaja = false;
            for (int p = boatLength; p >= 0; p--) {

                if (!dp[i - 1][p]) {
                    continue;
                }

                int Vi = vehicles.get(i - 1);
                int Si = sumatorio[i];

                // meter coche en babor
                if (p + Vi <= boatLength) {
                    dp[i][p + Vi] = true;
                    viaja = true;
                }

                // meter coche en estribor
                if (Si - p <= boatLength) {
                    dp[i][p] = true;
                    viaja = true;
                }
            }
            if (viaja)
                travelVehicles++;
        }

        printSolution();
    }

    private void printSolution() {
        System.out.printf("Han llegado un total de %d vehículos (%d viajarán).\n", vehicles.size(), travelVehicles);
        System.out.println("Tabla con los cálculos realizados:");
        printMatriz();
        System.out.println("Posible asignación:");
        printAsignaciones();

    }

    private void printMatriz() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-5s", "V/L"));
        for (int i = 0; i <= boatLength; i++) {
            sb.append(String.format("%-5s", i));
        }
        sb.append("\n");

        for (int i = 0; i < dp.length; i++) {
            sb.append(String.format("%-5s", i));
            for (int j = 0; j < dp[i].length; j++) {
                sb.append(String.format("%-5s", dp[i][j] ? "T" : "F"));
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }
    
    private void printAsignaciones() {
        int pBabor = 0;
        // eligiendo solucion que maximice babor
        for (int j = boatLength; j >= 0; j--) {
            if (dp[travelVehicles][j]) {
                pBabor = j;
                break;
            }
        }
        int pEstribor = sumatorio[travelVehicles] - pBabor;

        for (int i = 1; i <= travelVehicles; i++) {
           System.out.printf("Vehículo %d (longitud %d) a %s.\n",
                          i, vehicles.get(i - 1), "estribor");  // TODO
        }

        System.out.printf("Ocupación final: Babor %dm / Estribor %dm (válido <= %d).",
                          pBabor, pEstribor, boatLength);

    }
    
    private static Ferry loadData(String fichero) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fichero));

            int boatLength = Integer.parseInt(in.readLine());
            List<Integer> vehicles = parseVehicles(in.readLine());
            return new Ferry(boatLength, vehicles);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static List<Integer> parseVehicles(String data) {
        String[] vehicles = data.split(" ");
        List<Integer> list = new ArrayList<>();

        for (String s : vehicles) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }
    

    public static void main(String[] args) {
        String fichero = args[0];
        Ferry ferry = loadData(fichero);
        ferry.run();  
    }
}
