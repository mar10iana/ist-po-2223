package prr.comms;

import prr.terminals.Terminal;
import prr.visits.CommVisitor;

import java.io.Serializable;

public abstract class Communication implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 141020221603L;

    private int _idComm;
    protected Terminal _from;
    protected Terminal _to;

    protected double _price = 0;

    private String _status = "ONGOING";

    private boolean _payed = false;

    public abstract String getCommType();

    public Communication(int idComm, Terminal from, Terminal to) {
        _idComm = idComm;
        _from = from;
        _to = to;
    }

    public String getIdSender(){
        return _from.getTerminalID();
    }

    public String getIdReceiver(){
        return _to.getTerminalID();
    }

    public int getIdComm() { return _idComm; }

    public double getPrice() { return _price; }

    public String getStatus() { return _status; }

    public void statusToFinished() { _status = "FINISHED"; }

    public abstract void accept(CommVisitor visitor);

    public void setTime(double time){}

    public Terminal getTo(){ return _to;}

    public Terminal getFrom(){ return _from;}
    public abstract void setPrice();

    public boolean isPayed() { return _payed; }

    public void pay() { _payed = true; }
}
