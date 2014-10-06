package municipalities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * {@link MunicipalitiesRecord} contains a set of {@link Municipality} and is
 * used to manage, access and search them.
 * 
 */
public class MunicipalitiesRecord {

	private ArrayList<Municipality> record = new ArrayList<Municipality>();

	/**
	 * Adds a {@link Municipality} to the {@link MunicipalitiesRecord}, if the
	 * record does not contain it already.
	 * 
	 * @param municipality
	 *            {@link Municipality} to add
	 * @throws MunicipalityAlreadyExistsException
	 */
	public void add(Municipality municipality)
			throws MunicipalityAlreadyExistsException {
		if (!record.contains(municipality)) {
			record.add(municipality);
		} else {
			throw new MunicipalityAlreadyExistsException(
					municipality.getInseeId());
		}
	}

	/**
	 * Loads data from a specified directory. Goes through all the xml files
	 * matching <code>mairie-</code>, and fetches the necessary info. Used to
	 * store Municipalities from a single province.
	 * 
	 * @param dir
	 *            {@link String} path to directory
	 * @throws NoSuchDirectoryException
	 */
	public void loadRestrictedSet(String dir) throws NoSuchDirectoryException {

		try {

			File directory = new File(dir);
			if (!directory.isDirectory()) {
				throw new NoSuchDirectoryException(dir);
			}

			File[] fileList = directory.listFiles();
			for (File file : fileList) {
				if (file.isFile() && file.getName().matches("^mairie-(.*).xml")) {

					File xmlFile = new File(file.getPath());

					DocumentBuilderFactory dbFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document document = dBuilder.parse(xmlFile);

					document.getDocumentElement().normalize();

					NodeList nodeList = document
							.getElementsByTagName("Organisme");

					String name = "";
					String inseeId = "";
					String postalCode = "";
					String phoneNumber = "";
					String email = "";
					String website = "";

					Node node = nodeList.item(0);

					if (node.getNodeType() == Node.ELEMENT_NODE) {

						Element organisme = (Element) node;

						inseeId = organisme.getAttribute("codeInsee");
						name = organisme.getElementsByTagName("NomCommune")
								.item(0).getTextContent();
						if (organisme.getElementsByTagName("CodePostal")
								.item(0) != null) {
							postalCode = organisme
									.getElementsByTagName("CodePostal").item(0)
									.getTextContent();
						}
						if (organisme.getElementsByTagName("Téléphone").item(0) != null) {
							phoneNumber = organisme
									.getElementsByTagName("Téléphone").item(0)
									.getTextContent();
						}
						if (organisme.getElementsByTagName("Email").item(0) != null) {
							email = organisme.getElementsByTagName("Email")
									.item(0).getTextContent();
						}
						if (organisme.getElementsByTagName("Url").item(0) != null) {
							website = organisme.getElementsByTagName("Url")
									.item(0).getTextContent();
						}

						record.add(new Municipality(name, inseeId, postalCode,
								phoneNumber, email, website));

					}
				}
			}
		} catch (ParserConfigurationException e) {
			System.err.println(e.getMessage());
		} catch (SAXException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	/**
	 * Loads data from a specified directory. Goes through all sub-directories,
	 * and access all xml files matching <code>mairie-</code>, and fetches the
	 * necessary info. Used if all {@link Municipality}, regardless of
	 * provinces, need to be stored in the {@link MunicipalitiesRecord}.
	 * 
	 * @param dir
	 *            {@link String} the path of the directory containing all
	 *            provinces' directories
	 * @throws NoSuchDirectoryException
	 */
	public void loadCompleteSet(String dir) throws NoSuchDirectoryException {

		File directory = new File(dir);

		if (!directory.isDirectory()) {
			throw new NoSuchDirectoryException(dir);
		}

		File[] dirList = directory.listFiles();

		for (File file : dirList) {
			if (file.isDirectory()) {
				System.out.println("Loading files from " + file.getPath());
				loadRestrictedSet(file.getPath());
			}
		}

	}

	/**
	 * Removes the given {@link Municipality}.
	 * 
	 * @param municipality
	 *            Municipality to remove
	 */
	public void remove(Municipality municipality) {
		record.remove(municipality);
	}

	/**
	 * Removes all {@link Municipality} stored
	 */
	public void removeAll() {
		record.clear();
	}

	/**
	 * Returns the number of {@link Municipality} currently stored in the
	 * record.
	 * 
	 * @return int size
	 */
	public int getCurrentMunicipalityCount() {
		return record.size();
	}

	/**
	 * Returns a list of all the Municipalities' names stored in the record.
	 * Used to display the {@link JList} of Municipalities
	 * 
	 * @return a table of {@link String} containing only the names
	 */
	public String[] getNamesList() {
		return extractNamesFromList(searchMunicipalitiesByName(""));
	}

	/**
	 * Returns the {@link Municipality} of index <code>index</code>.
	 * 
	 * @param index
	 *            the index of the wanted Municipality
	 * @return {@link Municipality} the wanted Municipality
	 */
	public Municipality getMunicipality(int index) {
		return record.get(index);
	}

	public Municipality getMunicipalityByName(String name)
			throws NoSuchMunicipalityException {

		for (Municipality muni : record) {
			if (muni.getName().equals(name)) {
				return muni;
			}
		}
		throw new NoSuchMunicipalityException(name);
	}

	/**
	 * Searches the {@link MunicipalitiesRecord} for every {@link Municipality}
	 * which name matches a given name, and returns a list of all found
	 * Municipalities.
	 * 
	 * @param name
	 *            the name to search for
	 * @return {@link ArrayList} of the found {@link Municipality}
	 */
	public ArrayList<Municipality> searchMunicipalitiesByName(String name) {
		ArrayList<Municipality> output = new ArrayList<Municipality>();
		for (Municipality muni : record) {
			if (muni.getName().matches("(?i)(^" + name + "(.*))")) {
				output.add(muni);
			}
		}
		return output;
	}

	/**
	 * Searches the {@link MunicipalitiesRecord} for every {@link Municipality}
	 * which inseeId matches a given insee code, and returns a list of all found
	 * Municipalities.
	 * 
	 * @param insee
	 *            the insee code to search for
	 * @return {@link ArrayList} of the found {@link Municipality}
	 */
	public ArrayList<Municipality> searchMunicipalitiesByInsee(String insee) {
		ArrayList<Municipality> output = new ArrayList<Municipality>();
		for (Municipality muni : record) {
			if (muni.getInseeId().matches("^" + insee + "(.*)")) {
				output.add(muni);
			}
		}
		return output;
	}

	/**
	 * Searches the {@link MunicipalitiesRecord} for every {@link Municipality}
	 * which postal code matches a given postal code, and returns a list of all
	 * found Municipalities.
	 * 
	 * @param postal
	 *            the postal code to search for
	 * @return {@link ArrayList} of the found {@link Municipality}
	 */
	public ArrayList<Municipality> searchMunicipalitiesByPostal(String postal) {
		ArrayList<Municipality> output = new ArrayList<Municipality>();
		for (Municipality muni : record) {
			if (muni.getPostalCode().matches("^" + postal + "(.*)")) {
				output.add(muni);
			}
		}
		return output;
	}

	/**
	 * Extracts only the names contained in the given list of
	 * {@link Municipality}, and returns them in an array. Used to display the
	 * {@link JList} of Municipalities
	 * 
	 * @param list
	 *            {@link ArrayList}<{@link Municipality}>
	 * @return {@link String}[] of the corresponding names
	 */
	public static String[] extractNamesFromList(ArrayList<Municipality> list) {
		int size = list.size();
		String[] output = new String[size];
		for (int i = 0; i < size; i++) {
			output[i] = list.get(i).getName();
		}
		return output;
	}

	/**
	 * Displays all {@link Municipality} contained in the
	 * {@link MunicipalitiesRecord}
	 */
	@Override
	public String toString() {
		if (getCurrentMunicipalityCount() == 0) {
			return "The record is empty, no municipalities to list yet...\n";
		} else {
			String display = "";
			for (Municipality municipality : record) {
				display += municipality.toString() + "\n";
			}
			return display;
		}
	}

}
