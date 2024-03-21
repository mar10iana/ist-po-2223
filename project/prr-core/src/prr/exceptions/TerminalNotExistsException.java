package prr.exceptions;

/**
 * Terminal does not exist.
 */
public class TerminalNotExistsException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 161020221605L;

    /** Terminal's ID */
    private final String _id;

    /** @param id */
    public TerminalNotExistsException(String id) { _id = id ;}

    /** @return Terminal's id */
    public String getID() { return _id; }

}
