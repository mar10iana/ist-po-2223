package prr.terminals.states;

import prr.exceptions.TerminalAlreadyOffException;
import prr.notif.O2I;
import prr.notif.O2S;
import prr.terminals.Terminal;

public class Off extends State {

    public Off(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getState() { return "OFF"; }

    @Override
    public void toIdle() {
        _terminal.send(new O2I(_terminal));
        _terminal.setState(new Idle(_terminal));
    }

    @Override
    public void toMute() {
        _terminal.send(new O2S(_terminal));
        _terminal.setState(new Mute(_terminal));
    }

    @Override
    public void toOff() throws TerminalAlreadyOffException {
        throw new TerminalAlreadyOffException();
    }


}
