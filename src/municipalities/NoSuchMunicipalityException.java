package municipalities;

import java.util.NoSuchElementException;

/**
 * An {@link NoSuchMunicipalityException} is an exception indicating that an
 * operation failed because a {@link MunicipalitiesRecord} did not contained an
 * {@link Municipality} by the name specified.
 * 
 */
public class NoSuchMunicipalityException extends NoSuchElementException {

	private static final long serialVersionUID = 1L;

	public NoSuchMunicipalityException(String name) {
		super("The municipality with name " + name + " was not found !");
	}

}
