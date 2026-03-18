package p4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ColoreoGrafo {
    
    /**
     * Algoritmo voraz de colorear un grafo (anotaciones clase):
     * 
     * Definir la estructura de datos para guardar la representacion del mapa
     * y la paleta de colores
     *
     * RealizarVoraz devuelve un map de string string
     * 
     * Reccorrer los nodos, para cada uno de los nodos visitamos sus vecinos
     * si algun vecino ya tiene algun color del diccionario no esta disponible
     * para ser usado (los numero de dentro de la lista del json son los vecinos del nodo)
     * 
     * Una vez identificamos los vecimos y sus colores, seleccionamos el color que no tiene 
     * asignado ningun vecino, y asi todo el rato.
     *
     */
	public static Map<String, String> realizarVoraz(Map<String, List<String>> grafo) {
		String[] colores = {"red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta", "lime"};
		HashMap<String, String> solucion = new HashMap<>();
		
		for (String nodo: grafo.keySet()) {
			HashSet<String> coloresVecinos = new HashSet<>();
			
			for (Object vecino: grafo.get(nodo)) {
				vecino = vecino.toString();
				if (solucion.containsKey(vecino)) {
					coloresVecinos.add(solucion.get(vecino));
				}
			}
			
			for (String color: colores) {
				if (!coloresVecinos.contains(color)) {
					solucion.put(nodo, color);
					break;
				}
			}	
		}
		return solucion;
	}

}
