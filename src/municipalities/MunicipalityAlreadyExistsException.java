package municipalities;

/**
 * An {@link MunicipalityAlreadyExistsException} is an exception indicating that
 * an operation failed because a {@link MunicipalitiesRecord} already contained
 * an identical {@link Municipality}.
 * 
 */
public class MunicipalityAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public MunicipalityAlreadyExistsException(String inseeId) {
		super("The municipality with INSEE ID " + inseeId + " already exists !");
	}
}
