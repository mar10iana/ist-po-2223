package prr.terminals;

import prr.Client;
import prr.visits.TerminalVisitor;

public class Fancy extends Terminal {

    public String getType(){ return "FANCY"; }
    public Fancy(String terminalID, Client owner) {
        super(terminalID, owner);
    }

    @Override
    public String toString() {
        return "FANCY | " + super.toString();
    }

    @Override
    public void accept(TerminalVisitor visitor){
        visitor.visitFancy(this);
    }
}
