package prr.notif;

import prr.terminals.Terminal;

public class O2I extends Notification{

    public O2I(Terminal owner) {
        super(owner);
    }

    public String getNotType(){
        return "O2I";
    }

}
