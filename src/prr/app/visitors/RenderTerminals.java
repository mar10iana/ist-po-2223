package prr.app.visitors;

import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;
import prr.visits.TerminalVisitor;

import static java.lang.Math.round;

public class RenderTerminals implements TerminalVisitor {

    /** Rendered Terminal. */
    private String _rendering = "";

    private String renderFields(Terminal terminal) {
        return terminal.getTerminalID() + "|" + terminal.getOwner().getClientID() + "|" +
                terminal.getState().getState() + "|" + round(terminal.getPaid())
                + "|" + round(terminal.getToPay()) + "|"
                + terminal.getFriends();
    }

    @Override
    public void visitBasic(Basic terminal){
        _rendering = "BASIC|" + renderFields(terminal);
    }

    @Override
    public void visitFancy(Fancy terminal){
        _rendering = "FANCY|" + renderFields(terminal);
    }

    @Override
    public String toString(){
        return _rendering.substring(0, _rendering.length() - 1);
    }
}
