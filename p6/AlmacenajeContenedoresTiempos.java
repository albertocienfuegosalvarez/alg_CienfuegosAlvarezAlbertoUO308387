import java.io.FileReader;
import java.util.Scanner;

public class AlmacenajeContenedoresTiempos {
	public static void main(String[] args) {

		long t1, t2, tiempo;

		for (int i = 0; i <= 9; i++) {

			String fichero = "CasosPrueba/test0" + i + ".txt";
			t1 = System.currentTimeMillis();

			for (int n = 0; n < 1000; n++) {
				probarTest(fichero);
			}

			t2 = System.currentTimeMillis();
			tiempo = t2 - t1;
			System.out.println("test0" + i + " --- " + " Tiempo(ms): " + tiempo);

		}
	}
	
	private static void probarTest(String fichero) {
		try {
            Scanner sc = new Scanner(new FileReader(fichero));
            int capacidadC = sc.nextInt();
            sc.nextLine();  // para que salte a la siguiente porque sino se queda en la de la capacidad
            String[] conjuntosString = sc.nextLine().split(" ");
            Integer[] conjuntosInteger = new Integer[conjuntosString.length];
            int i = 0;
            for (String s: conjuntosString) {
                conjuntosInteger[i++] = Integer.parseInt(s);
            }
            
            new AlmacenajeContenedores(capacidadC, conjuntosInteger).resolver();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
