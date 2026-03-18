package p4;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DevoradorTiempos {
	public static void main(String[] args) {
		
		long t1,t2,tiempo;
		JSONParser parser = new JSONParser();
		
		for (int i= 4; i<65537; i*=2) {
			
			String file = "sols/g" + i + ".json";
			try (FileReader reader = new FileReader(file)) {
				JSONObject jsonObject = (JSONObject) parser.parse(reader);
				@SuppressWarnings("unchecked")
				Map<String, List<String>> grafo = (Map<String, List<String>>) jsonObject.get("grafo");
				
				// Medicion de tiempos del algoritmo
				t1 = System.currentTimeMillis ();
				Map<String, String> solucion;
				for (int nVeces=0; nVeces<100; nVeces++) {		// con 100 repes para mejores tiempos
					solucion = ColoreoGrafo.realizarVoraz(grafo);
				}
				t2 = System.currentTimeMillis ();
				tiempo = t2 - t1;
				System.out.println("n: " + i + " Tiempo(ms): " + tiempo);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
