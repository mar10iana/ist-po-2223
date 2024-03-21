package prr.exceptions;

/**
 * Client does not exist.
 */
public class ClientNotExistsException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 161020221553L;

    /** Client's id. */
    private final String _id;

    /** @param id */
    public ClientNotExistsException(String id) { _id = id; }

    /** @return Client's id. */
    public String getID() { return _id; }

}
