package prr.visits;

import prr.terminals.Basic;
import prr.terminals.Fancy;

public interface TerminalVisitor {

    /** @param basic */
    void visitBasic(Basic basic);

    /** @param fancy */
    void visitFancy(Fancy fancy);

}
