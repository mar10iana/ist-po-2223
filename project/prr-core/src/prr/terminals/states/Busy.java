package prr.terminals.states;

import prr.notif.B2I;
import prr.terminals.Terminal;

public class Busy extends State {

    public Busy(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String getState() { return "BUSY"; }

    @Override
    public void toIdle() {
        _terminal.send(new B2I(_terminal));
        _terminal.setState(new Idle(_terminal));
    }

    @Override
    public void toMute() {
        _terminal.setState(new Mute(_terminal));
    }

}
