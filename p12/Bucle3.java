package p12;

public class Bucle3 {
	public static long bucle3(long n) {
		long cont = 0;
		long n1 = n;
		long i = 1;
		while (i <= 2 * n) {		// n
			for (long j = i; j >= 0; j -= 2) {  // n/2 -> n
				for (long k = 1; k <= n; k *= 2) {	// log2(n)
					cont++;
				}
			}
			i++;
		}

		return cont;
	}					// total -> n^2 * log2(n)	Complejidad teorica es la misma quue B2 pero tardan diferente

	// Sumatorio (para entender cual tarda mas):
	// E(i=1 (empieza); 2n (fin)) (i/2 * log2(n))  -> podemos sacar lo que sea constante entonces = log2(n)/2 * E (n*(n + 1)) donde (n*(n +1)) = i
	// Bucle tres tardara algo más POR NO SE QUE DEL NEPERIANO

	public static void main(String arg[]) {
		long t1, t2;
		int nVeces = Integer.parseInt(arg[0]);

		System.out.println("n\ttiempo\trepeticiones\tcontador");

		for (long n = 100; n <= 819200; n *= 2) {
			long c = 0;
			t1 = System.currentTimeMillis();

			for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++) {
				c = bucle3(n);
			}
			t2 = System.currentTimeMillis();
			System.out.println(n + "\t" + (t2 - t1) + "\t" + nVeces + "\t\t" + c);

		} // for
	} // main
} // class