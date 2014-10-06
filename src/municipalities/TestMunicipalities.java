package municipalities;

import java.util.ArrayList;

/**
 * {@link TestMunicipalities} is used to test in console mode the behavior of
 * the {@link MunicipalitiesRecord}.
 * 
 * @see gui.IndexGUI for more sophisticated implementations
 * 
 */
public class TestMunicipalities {
	public static void main(String args[]) {

		MunicipalitiesRecord rec = new MunicipalitiesRecord();

		System.out.println("before anything");
		System.out.println(rec.toString());

		try {
			rec.loadRestrictedSet("jeu_de_donnees_reduit.d");
		} catch (NoSuchDirectoryException e) {
			System.err.println(e.getMessage());
		}

		System.out.println(rec.toString());
		System.out.println("after loading data, we have "
				+ rec.getCurrentMunicipalityCount() + " entries stored.");

		System.out.println("Searching all municipalities beginning with 'd'");
		System.out.println();
		System.out.println();
		ArrayList<Municipality> results = rec.searchMunicipalitiesByName("d");
		System.out.println(results.toString());

	}
}
