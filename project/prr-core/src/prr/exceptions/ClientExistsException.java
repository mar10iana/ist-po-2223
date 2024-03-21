package prr.exceptions;

/**
 * Client id has already been used.
 */

public class ClientExistsException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 161020221545L;

    /** Client's ID */
    private final String _id;

    /** @param id */
    public ClientExistsException(String id) { _id = id ;}

    /** @return Client's id */
    public String getID() { return _id; }

}
