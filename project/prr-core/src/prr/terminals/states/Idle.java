package prr.terminals.states;

import prr.exceptions.TerminalAlreadyOnException;
import prr.terminals.Terminal;

public class Idle extends State {

    public Idle(Terminal terminal) { super(terminal); }

    @Override
    public String getState() { return "IDLE"; }

    @Override
    public void toIdle() throws TerminalAlreadyOnException {
        throw new TerminalAlreadyOnException();
    }

    @Override
    public void toMute() {
        _terminal.setState(new Mute(_terminal));
    }

    @Override
    public void toOff(){
        _terminal.setState(new Off(_terminal));
    }

    @Override
    public void toBusy(){
        _terminal.setState(new Busy(_terminal));
    }
}
