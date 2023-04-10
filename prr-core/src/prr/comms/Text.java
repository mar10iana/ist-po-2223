package prr.comms;

import prr.terminals.Terminal;
import prr.visits.CommVisitor;

public class Text extends Communication {

    private int _numChar;
    private String _sms;

    public Text(int idComm, Terminal from, Terminal to, String sms) {
        super(idComm, from, to);
        _sms = sms;
        _numChar = _sms.length();
    }

    public String getCommType(){
        return "TEXT";
    }

    public int getNumChar(){
        return _numChar;
    }

    @Override
    public void accept(CommVisitor visitor){
        visitor.visitText(this);
    }

    @Override
    public void setPrice() {
        _price = _from.getOwner().getLevel().setPrice(this);
    }

}
