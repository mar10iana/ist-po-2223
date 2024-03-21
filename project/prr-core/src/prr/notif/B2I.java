package prr.notif;

import prr.terminals.Terminal;

public class B2I extends Notification{

    public B2I(Terminal owner){
        super(owner);
    }
    public String getNotType(){
        return "B2I";
    }
}
