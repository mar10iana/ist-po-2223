package prr.notif;

import prr.terminals.Terminal;

import java.io.Serializable;

public abstract class Notification implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 181020221707L;

    private Terminal _owner;

    public Notification(Terminal owner){
        _owner = owner;
    }
    public abstract String getNotType();

    public Terminal getOwner(){
        return _owner;
    }
}
