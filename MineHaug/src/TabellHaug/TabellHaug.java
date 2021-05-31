package TabellHaug;

import HaugADT.HaugADT;

public class TabellHaug<T extends Comparable<T>> implements HaugADT<T> {
	// Lager en minimumshaug

	private T[] data;
	private int antall;
	private int slettetSlut = antall;

	private static final int STDK = 100;

	public TabellHaug() {
		data = (T[]) new Comparable[STDK];
		antall = 0;
	}

	@Override
	public void leggTilElement(T el) {
		if (antall == data.length)
			utvidTabell();
		data[antall] = el; // Plasser den nye helt sist
		antall++;
		slettetSlut++;
		if (antall > 1)
			reparerOpp(); // Bytt om oppover hvis nødvendig
	}

	private void utvidTabell() {
		// Dobler tabellen ved behov for utviding
		int lengde = data.length;
		T[] ny = (T[]) new Comparable[2 * lengde];
		for (int i = 0; i < antall; i++)
			ny[i] = data[i];
		data = ny;
	}

	private void reparerOpp() {
		int rettPlass = antall - 1;
		T tmp = data[rettPlass];
		int forelder = (rettPlass - 1) / 2;
		while (rettPlass > 0 && tmp.compareTo(data[forelder]) < 0) {
			data[rettPlass] = data[forelder];
			rettPlass = forelder;
			forelder = (rettPlass - 1) / 2;
		}
		data[rettPlass] = tmp;
	}

	private void reparerOpp2() {
		int nyelem = antall - 1;
		T nyelement = data[nyelem];
		int foreldreIndeks = (nyelem - 1) / 2;
		int minst = nyelement.compareTo(data[foreldreIndeks]);

		while (minst < 0) {
			swap(data, nyelem, foreldreIndeks);
			nyelem = foreldreIndeks;
			foreldreIndeks = (nyelem - 1) / 2;
			minst = nyelement.compareTo(data[foreldreIndeks]);
		}

		// ... fyll ut
	}

	private static <T> void swap(T[] tab, int i, int j) {
		T tmp = tab[i];
		tab[i] = tab[j];
		tab[j] = tmp;
	}

	@Override
	public T fjernMinste() {
		T svar = null;
		if (antall > 0) {
			svar = data[0];
			swap(data, 0, antall - 1);
			// data[0] = data[antall - 1];
			antall--;
			reparerNed(); // Bytter om nedover hvis nødvendig
			// antall--;
		}
		return svar;
	}

	@Override
	public T finnMinste() {
		T svar = null;
		if (antall > 0) {
			svar = data[0];
		}
		return svar;
	}

	private void reparerNed() {
		T hjelp;
		boolean ferdig = false;
		int forelder = 0; // Start i roten og sml med neste nivå
		int minbarn;
		int vbarn = forelder * 2 + 1;
		int hbarn = vbarn + 1;
		while ((vbarn < antall) && !ferdig) { // Har flere noder lenger nede
			minbarn = vbarn;

			if ((hbarn < antall) && ((data[hbarn]).compareTo(data[vbarn]) < 0)) {
				minbarn = hbarn;
			}
			// Har funnet det "minste" av barna. Sml med forelder

			if ((data[forelder]).compareTo(data[minbarn]) <= 0) {
				ferdig = true;
			} else { // Bytt om og gå videre nedover hvis forelder er for stor
				hjelp = data[minbarn];
				data[minbarn] = data[forelder];
				data[forelder] = hjelp;
				forelder = minbarn;
				vbarn = forelder * 2 + 1;
				hbarn = vbarn + 1;
			}
		}
	}

	@Override
	public boolean erTom() {
		return antall == 0;
	}

	public void skrivTab() {
		// Hjelpemetode til test
		for (int i = 0; i < antall; i++)
			System.out.print(data[i] + " ");
		System.out.println();
	}

	public void skrivSlettTab() {
		// Hjelpemetode til test
		for (int i = antall; i < slettetSlut; i++)
			System.out.print(data[i] + " ");
		System.out.println();
	}
}