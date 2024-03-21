package prr.comms;

import prr.terminals.Terminal;
import prr.visits.CommVisitor;

public class Video extends Communication {

    private double _time;

    public Video(int idComm, Terminal from, Terminal to) {
        super(idComm, from, to);
    }

    public String getCommType(){
        return "VIDEO";
    }

    public double getTime(){
        return _time;
    }

    @Override
    public void accept(CommVisitor visitor){
        visitor.visitVideo(this);
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
