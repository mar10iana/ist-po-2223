package prr.notif;

import prr.terminals.Terminal;

public class S2I extends Notification{

    public S2I(Terminal owner) {
        super(owner);
    }

    public String getNotType(){
        return "S2I";
    }
}
