import java.util.ArrayList;
import java.util.List;

public class NReinasTiempos {
	public static void main(String[] args) {

		long t1, t2, tiempo;

		for (int n = 4; n <= 20; n++) {

			t1 = System.currentTimeMillis();

			probarTest(n);

			t2 = System.currentTimeMillis();
			tiempo = t2 - t1;
			System.out.println("Tablero de tamaño " + n + " --- " + " Tiempo(ms): " + tiempo + "\n");

		}
	}
	
	private static void probarTest(int n) {
		NReinas algoritmo = new NReinas();
		List<List<String>> resultado = algoritmo.resolverNReinas(n);
			
		System.out.println("Se encontraron " + resultado.size() + " soluciones para N = " + n);
	}
}
