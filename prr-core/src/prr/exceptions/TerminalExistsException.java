package prr.exceptions;

/**
 * Terminal id has already been used.
 */

public class TerminalExistsException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 161020221605L;

    /** Terminal's ID */
    private final String _id;

    /** @param id */
    public TerminalExistsException(String id) { _id = id ;}

    /** @return Terminal's id */
    public String getID() { return _id; }

}

