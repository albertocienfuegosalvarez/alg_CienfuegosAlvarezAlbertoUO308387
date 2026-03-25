package uo.p5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Ferry {

    private int boatLength; // longitud de los carriles del barco
    private List<Integer> vehicles; // lista de vehiculos
    private boolean[][] dp; // matriz con las posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos
    private List<Step> path; //variable para guardar el camino seleccionado

    public Ferry(int boatLength, List<Integer> vehicles) {
        this.boatLength = boatLength;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size() + 1][boatLength + 1];
        this.sumatorio = new int[vehicles.size() + 1];
        rellenarSumatorio();
        this.path = new ArrayList<>();
    }

    private void rellenarSumatorio() {
        this.sumatorio[0] = 0;
        for (int i = 1; i <= vehicles.size(); i++) {
            this.sumatorio[i] = this.sumatorio[i - 1] + vehicles.get(i - 1);
        }
    }

    /**
    * Devuelve el numero máximo de vehiculos posibles
    * l (siendo l < boatlength) con dp[i][l] = true. es el maximo número de coches que pueden entrar.
    */
    public int getMaximumNumberOfVehicles() {
        for (int i = vehicles.size(); i >= 0; i--) {
            for (int l = 0; l <= boatLength; l++) {
                if (dp[i][l]) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * Ejecuta el algoritmo
     */
    public void run() {

        // Caso base: con 0 vehiculos babor tiene 0 espacios usados
        dp[0][0] = true;

        for (int i = 1; i <= vehicles.size(); i++) {
            for (int p = boatLength; p >= 0; p--) {

                if (!dp[i - 1][p]) {
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
    }

    public void printData() {
        System.out.printf("Length of parallel lanes for starboard and port on the ferry: %d\n", boatLength);
        System.out.printf("The vehicles have the following lengths:\n");
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.printf("\tVehicle %d: %d\n", i + 1, vehicles.get(i));
        }
    }
    
    public void printPossibleAssignation() {
        boolean found = false;
        System.out.printf("\nPossible assignation:\n");

        for (int i = getMaximumNumberOfVehicles(); i > 0; i--) {
            //si found es true -> rompo la ejecución
            //para cada p de la longitud del barco
            //		si found es true -> rompo la ejecución
            //		si dp[i][p-v(i)] es true -> found = true; llamo a processAssignation()

            if (found)
                break;
            for (int p = boatLength; p >= 0; p--) {
                if (found)
                    break;
                
                int Vi = vehicles.get(i - 1);

                if (p - Vi >= 0 && dp[i][p - Vi]) {
                    found = true;
                    processAssignation(i, p);
                }
            }
        }
    }
    
    private void processAssignation(int i, int l) {
        if ((i == 0) && (l == 0)) { 
            // llamo a printPath y acabo la ejecución (return)
            printPath();
            path.clear();
            return;
        }
            
        if (dp[i-1][l]) {
            //añado al path (path.addFirst) un nuevo Step llamado estribor; llamo a processAssignation(i-1, l);
            path.addFirst(new Step(i-1, l, i, l, i, "estribor"));
            processAssignation(i - 1, l);
            return;
        }


            
        if (dp[i - 1][l - vehicles.get(i - 1)]) {
            //añado al path (path.addFirst) un nuevo Step llamado babor; llamo a processAssignation(i-1, l-vehicles.get(i-1));
            path.addFirst(new Step(i - 1, l - vehicles.get(i-1), i, l, i, "babor"));
            processAssignation(i - 1, l - vehicles.get(i - 1));
            return;
        }


	}

    public void printSolutionTable() {
        System.out.printf("\nTable with calculations:\n");

        System.out.printf("%4s", "V/L");
        for (int i = 0; i <= boatLength; i++) {
            System.out.printf("%4d", i);
        }
        System.out.printf("\n");

        for (int i = 0; i <= vehicles.size(); i++) {
            System.out.printf("%4d", i);
            for (int l = 0; l <= boatLength; l++) {
                if (dp[i][l]) {
                    System.out.printf("%4s", "T");
                } else {
                    System.out.printf("%4s", "F");
                }
            }
            System.out.printf("\n");
        }
    }
    
    private void printPath() {
        int portLength = 0;
        int starboardLength = 0;
        for (var step : path) {		
            if (step.movement().equals("babor")){
                portLength += vehicles.get(step.vehicle()-1);
            }
            else{
                starboardLength += vehicles.get(step.vehicle()-1);
            }
            System.out.printf("Vehicle %d (length %d) -- From (%d, %d) -- To (%d, %d) -- Position: %s -- Port lengh: %d -- Starboard length: %d\n", 
                    step.vehicle(), vehicles.get(step.vehicle()-1),
                    step.previousI(), step.previousL(),
                    step.currentI(), step.currentL(), 
                    step.movement(), portLength, starboardLength);
        }
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
        ferry.printData();
        ferry.printSolutionTable();
        ferry.printPossibleAssignation();
    }
}

record Step(int previousI, int previousL, 
		int currentI, int currentL, 
		int vehicle, String movement) {}

