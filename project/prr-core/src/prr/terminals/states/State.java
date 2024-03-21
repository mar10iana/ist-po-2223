package prr.terminals.states;

import prr.exceptions.TerminalAlreadyMuteException;
import prr.exceptions.TerminalAlreadyOffException;
import prr.exceptions.TerminalAlreadyOnException;
import prr.terminals.Terminal;

import java.io.Serializable;

public abstract class State implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 171020222326L;

    protected Terminal _terminal;
    public State (Terminal terminal){ _terminal = terminal; }
    public abstract String getState();

    public abstract void toIdle() throws TerminalAlreadyOnException;
    public abstract void toMute() throws TerminalAlreadyMuteException;

    public void toBusy() { }

    public void toOff() throws TerminalAlreadyOffException { }


}
