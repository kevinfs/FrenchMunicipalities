package municipalities;

/**
 * {@link Municipality} is used to store informations regarding a Town's
 * Municipality.
 * 
 * @see MunicipalitiesRecord
 * 
 */
public class Municipality {

	private String name;
	private String inseeId;
	private String postalCode;
	private String phoneNumber;
	private String email;
	private String website;

	/**
	 * Constructs a Municipality with all parameters given
	 * 
	 * @param name
	 *            name of the municipality
	 * @param inseeId
	 *            INSEE identification number of the municipality
	 * @param postalCode
	 *            postal code of the municipality
	 * @param phoneNumber
	 *            telephone number of the municipality
	 * @param email
	 *            email address of the municipality
	 * @param website
	 *            website address of the municipality
	 */
	public Municipality(String name, String inseeId, String postalCode,
			String phoneNumber, String email, String website) {
		this.name = name;
		this.inseeId = inseeId;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.website = website;
	}

	/**
	 * Constructs a Municipality with only required infos
	 * 
	 * @param name
	 *            name of the municipality
	 * @param inseeId
	 *            INSEE identification number of the municipality
	 * @param postalCode
	 *            postal code of the municipality
	 * @param phoneNumber
	 *            telephone number of the municipality
	 */
	public Municipality(String name, String inseeId, String postalCode,
			String phoneNumber) {
		this.name = name;
		this.inseeId = inseeId;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public String getInseeId() {
		return inseeId;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getWebsite() {
		return website;
	}

	@Override
	public String toString() {
		String display = "====================\n" + name
				+ "\n--------------------\nCode Postal : " + postalCode
				+ "\nCode Insee : " + inseeId + "\nTŽl Mairie : " + phoneNumber;
		if (email != null && !email.isEmpty()) {
			display += "\nEmail Mairie : " + email;
		}
		if (website != null && !website.isEmpty()) {
			display += "\nSite Mairie : " + website;
		}
		display += "\n";

		return display;
	}

	/**
	 * Indicates whether some other object is "equal to" this one. The inseeId
	 * field is used to do the comparison.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Municipality) {
				Municipality other = (Municipality) obj;
				if (inseeId.equals(other.getInseeId())) {
					return true;
				}
			}
		}
		return false;
	}

}
