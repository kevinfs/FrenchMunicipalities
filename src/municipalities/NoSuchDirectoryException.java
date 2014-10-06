package municipalities;

/**
 * An {@link NoSuchDirectoryException} is an exception indicating that an
 * operation failed because the path given was not valid, or the directory did
 * not exist.
 * 
 */
public class NoSuchDirectoryException extends NullPointerException {

	private static final long serialVersionUID = 1L;

	public NoSuchDirectoryException(String name) {
		super("The directory with name " + name
				+ " does not exist, or is not a directory !");
	}

}
