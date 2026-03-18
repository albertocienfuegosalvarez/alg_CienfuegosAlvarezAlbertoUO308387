import json
import time

from auxiliar import generar_mapa_grafo
from coloreado_grafo import realizar_voraz

if __name__ == "__main__":
    n = 4
    t1 = 0
    t2 = 0
    while n <= 65536:
        mapa = generar_mapa_grafo(n)
        t1 = time.time()
        for i in range(100):
            solucion = realizar_voraz(mapa["grafo"])
        t2 = time.time()
        tiempo = (t2 - t1) * 1000
        print(f"n: {n} Tiempo(ms): {tiempo}")
        n *= 2
        
