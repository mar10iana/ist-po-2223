package prr.terminals.states;

import prr.exceptions.TerminalAlreadyMuteException;
import prr.notif.S2I;
import prr.terminals.Terminal;

public class Mute extends State {

    public Mute(Terminal terminal) { super(terminal); }

    @Override
    public String getState() { return "SILENCE"; }

    @Override
    public void toIdle() {
        _terminal.send(new S2I(_terminal));
        _terminal.setState(new Idle(_terminal));
    }

    @Override
    public void toMute() throws TerminalAlreadyMuteException {
        throw new TerminalAlreadyMuteException();
    }

    @Override
    public void toBusy() {
        _terminal.setState(new Busy(_terminal));
    }

    @Override
    public void toOff(){
        _terminal.setState(new Off(_terminal));
    }
}
