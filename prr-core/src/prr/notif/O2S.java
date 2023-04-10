package prr.notif;

import prr.terminals.Terminal;

public class O2S extends Notification{

    public O2S(Terminal owner) {
        super(owner);
    }

    public String getNotType(){
        return "O2S";
    }
}
