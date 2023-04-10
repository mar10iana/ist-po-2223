package prr;

import prr.comms.Communication;
import prr.exceptions.OffNotifAlreadyException;
import prr.exceptions.OnNotifAlreadyException;
import prr.exceptions.TerminalExistsException;
import prr.levels.Level;
import prr.levels.Normal;
import prr.notif.DeliveryMethod;
import prr.notif.Notification;
import prr.terminals.Terminal;
import prr.visits.ClientVisitor;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;


public class Client implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 121020221840L;
    private final String _clientID;
    private final String _name;
    private final int _nif;
    private double _paid = 0;
    private double _toPay = 0;
    private Level _level = new Normal(this);
    private boolean _notification = true;
    private Map<String, Terminal> _terminals = new TreeMap<>();

    private Map<Integer, Communication> _commsReceived = new TreeMap<>();
    private Map<Integer, Communication> _commsMade = new TreeMap<>();
    private int _textCount = 0;
    private int _videoCount = 0;
    private DeliveryMethod _delivery = new Default();
    private LinkedList<Terminal> _sendNotifTerminal = new LinkedList<>();

    private LinkedList<Notification> _notifications = new LinkedList<>();


    public Client(String clientID, String name, int nif){
        _clientID = clientID;
        _name = name;
        _nif = nif;
    }

    public String getClientID() {
        return _clientID;
    }

    public String getName() {
        return _name;
    }

    public int getNif(){ return _nif; }

    public double getPaid() { return _paid; }

    public double getToPay() { return _toPay; }

    public double getBalance() { return _paid-_toPay; }

    public void addTerminal(String terminalID, Terminal terminal) throws TerminalExistsException {
        if(_terminals.containsKey(terminalID))
            throw new TerminalExistsException(terminalID);

        _terminals.put(terminalID, terminal);
    }

    public int numTerminal(){
        return _terminals.size();
    }

    public String activeNotif(){
        if(_notification)
            return "YES";
        else
            return "NO";
    }

    public Level getLevel() {
        return _level;
    }

    public void accept(ClientVisitor visitor){
        visitor.visitClient(this);
    }

    public void turnOnNotif() throws OnNotifAlreadyException {
        if(_notification)
            throw new OnNotifAlreadyException();
        else
            _notification = true;
    }

    public void turnOffNotif() throws OffNotifAlreadyException {
        if(!_notification)
            throw new OffNotifAlreadyException();
        else
            _notification = false;
    }

    public void addCommMade(int id, Communication communication) {
        if(communication.getCommType().equals("TEXT")){
            _textCount++;
            _videoCount = 0;
        }
        if(communication.getCommType().equals("VIDEO")){
            _textCount = 0;
            _videoCount ++;
        }
        else{
            _textCount = 0;
            _videoCount = 0;
        }
        _commsMade.put(id, communication);

    }

    public void addCommReceived(int id, Communication communication) {
        _commsReceived.put(id, communication);
    }

    public Collection<Communication> getMadeComms(){
        return _commsMade.values();
    }

    public Collection<Communication> getReceivedComms(){
        return _commsReceived.values();
    }

    public void setLevel(Level level){
        _level = level;
    }

    public void setToPay() {

        Collection<Terminal> total = _terminals.values();

        double toPay = 0;
        for (Terminal terminal : total) {
            toPay += terminal.getToPay();
        }
        _toPay = toPay;
    }

    public void setPaid() {

        Collection<Terminal> total = _terminals.values();

        double paid = 0;
        for (Terminal terminal : total) {
            paid += terminal.getPaid();
        }
        _paid = paid;
    }

    public int getTextCount() { return _textCount; }

    public int getVideoCount() { return _videoCount; }

    public boolean notifiable() {
        return _notification;
    }

    public String getNotif() {
        String notifs = "";
        for (Notification not : _notifications) {
            notifs += "\n" + not.getNotType() + "|" + not.getOwner().getTerminalID();
        }
        _notifications.clear();
        return notifs;
    }

    public DeliveryMethod getDelivery() { return _delivery; }

    public class Default extends DeliveryMethod{
        @Override
        public void send(Notification notification) {
            if(!_notifications.contains(notification))
                _notifications.addLast(notification);
        }
    }

}
