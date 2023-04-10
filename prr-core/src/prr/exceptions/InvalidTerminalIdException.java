package prr.exceptions;

/**
 * Terminal ID is not valid.
 */
public class InvalidTerminalIdException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 161020221602L;

    /** Terminal's id. */
    private final String _id;

    /** @param id */
    public InvalidTerminalIdException(String id) { _id = id; }

    /** @return Terminal's id. */
    public String getID() { return _id; }

}
