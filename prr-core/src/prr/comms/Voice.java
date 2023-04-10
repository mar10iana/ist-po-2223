package prr.comms;

import prr.terminals.Terminal;
import prr.visits.CommVisitor;

public class Voice extends Communication {

    private double _time;

    public Voice(int idComm, Terminal from, Terminal to) {
        super(idComm, from, to);
    }

    public String getCommType(){
        return "VOICE";
    }

    public double getTime(){
        return _time;
    }

    @Override
    public void accept(CommVisitor visitor){
        visitor.visitVoice(this);
    }
    @Override
    public void setTime(double time){
        _time = time;
    }

    @Override
    public void setPrice() {
        _price = _from.getOwner().getLevel().setPrice(this);
        if(_from.getFriendsMap().containsKey(_to.getTerminalID()))
            _price = _price / 2;
    }
}
