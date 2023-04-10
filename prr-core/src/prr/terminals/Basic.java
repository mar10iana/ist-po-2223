package prr.terminals;

import prr.Client;
import prr.visits.TerminalVisitor;

public class Basic extends Terminal {

    public String getType(){ return "FANCY"; }

    public Basic(String terminalID, Client owner) {
        super(terminalID,owner);
    }

    @Override
    public String toString() {
        return "BASIC | " + super.toString();
    }

    @Override
    public void accept(TerminalVisitor visitor){
        visitor.visitBasic(this);
    }
}
