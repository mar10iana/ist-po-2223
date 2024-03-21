package prr.terminals;

import prr.Client;
import prr.Network;
import prr.comms.Communication;
import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;
import prr.exceptions.InvalidCommExcepption;
import prr.exceptions.TerminalAlreadyMuteException;
import prr.exceptions.TerminalAlreadyOnException;
import prr.exceptions.TerminalNotExistsException;
import prr.exceptions.ToTerminalIsBusyException;
import prr.exceptions.ToTerminalIsMuteException;
import prr.exceptions.ToTerminalIsOffException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.UnsupportedAtOriginException;
import prr.notif.Notification;
import prr.terminals.states.State;
import prr.visits.TerminalVisitor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 141020221500L;

    private final String _terminalID;
    private final Client _owner;
    private Map<String, Terminal> _friends = new TreeMap<>();
    private State _state;
    private State _lastState;
    private Map<Integer, Communication> _commsReceived = new TreeMap<>();
    private Map<Integer, Communication> _commsMade = new TreeMap<>();
    private Communication _onGoing = null;
    private long _commPrice;
    private double _paid = 0;
    private double _toPay = 0;
    private static int _commNumber = 1;
    private LinkedList<Terminal> _sendNotif = new LinkedList<>();

    public Terminal(String terminalID, Client owner){
        this._terminalID = terminalID;
        _owner = owner;
    }

    public abstract String getType();

    public void addFriend(String friendID, Terminal friend){
        if(!_friends.containsKey(friendID))
            _friends.put(friendID, friend);
    }

    public abstract void accept(TerminalVisitor visitor);

    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive communication) and
     *          it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        if(_onGoing != null){
            if (_onGoing.getFrom() == this)
                return getState().getState().equals("BUSY");
            else
                return false;
        }
        else
            return false;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        return !getState().getState().equals("OFF") && !getState().getState().equals("BUSY");
    }

    public String getTerminalID() {
        return _terminalID;
    }

    public Client getOwner() {
        return _owner;
    }

    public State getState() { return _state; }

    public void setState(State state) { _state = state; }

    public double getPaid() {
        return _paid;
    }

    public double getToPay() {
        return _toPay;
    }

    public String getFriends () {
        String friendsString = "";
        for(Terminal f : _friends.values())
            friendsString += f.getTerminalID() + ",";

        return friendsString;
    }

    public Map<Integer, Communication> getCommsReceived() {
        return _commsReceived;
    }

    public Map<Integer, Communication> getCommsMade() {
        return _commsMade;
    }

    public void registerTextCommunication(String toId, String sms, Network _network)
            throws TerminalNotExistsException, ToTerminalIsOffException {

        int id = _commNumber;

        if(toId.equals(_terminalID))
            return;

        if(_network.fetchTerminal(toId).getState().getState().equals("OFF")) {
            _network.fetchTerminal(toId).addSendNotif(this);
            throw new ToTerminalIsOffException();
        }

        Text comm = new Text(id, this, _network.fetchTerminal(toId), sms);

        comm.statusToFinished();
        comm.setPrice();
        updateToPay(comm.getPrice());
        addCommMade(id, comm, _network.fetchTerminal(toId), _network);
    }

    public void registerInteractiveCommunication(String toId, String type, Network _network)
            throws TerminalNotExistsException, ToTerminalIsOffException, UnrecognizedEntryException,
            ToTerminalIsMuteException, UnsupportedAtDestinationException, UnsupportedAtOriginException,
            ToTerminalIsBusyException {

        int id = _commNumber;

        if(toId.equals(_terminalID))
            return;

        if(_network.fetchTerminal(toId).getState().getState().equals("OFF")) {
            _network.fetchTerminal(toId).addSendNotif(this);
            throw new ToTerminalIsOffException();
        }
        if(_network.fetchTerminal(toId).getState().getState().equals("SILENCE")){
            _network.fetchTerminal(toId).addSendNotif(this);
            throw new ToTerminalIsMuteException();
        }
        if(_network.fetchTerminal(toId).getState().getState().equals("BUSY")){
            _network.fetchTerminal(toId).addSendNotif(this);
            throw new ToTerminalIsBusyException();
        }
        if(_network.fetchTerminal(toId).getType().equals("BASIC") && type.equals("VIDEO"))
            throw new UnsupportedAtDestinationException();
        if(this.getType().equals("BASIC") && type.equals("VIDEO"))
            throw new UnsupportedAtOriginException();

        Communication comm = switch (type) {
            case "VOICE" -> new Voice(id, this, _network.fetchTerminal(toId));
            case "VIDEO" -> new Video(id, this, _network.fetchTerminal(toId));
            default -> throw new UnrecognizedEntryException(type);
        };

        _onGoing = comm;
        addCommMade(id, comm, _network.fetchTerminal(toId), _network);

        _lastState = _state;
        _onGoing.getTo().setLastState(_network.fetchTerminal(toId).getState());
        getState().toBusy();
        _onGoing.getTo().getState().toBusy();

    }

    public void addCommMade(int id, Communication comm, Terminal to, Network _network) {
        _network.addComms(id,comm);
        _commsMade.put(id, comm);
        _owner.addCommMade(id, comm);
        to.addCommReceived(id, comm);
        to.getOwner().addCommReceived(id, comm);
        _commNumber ++;
    }

    public void updateToPay(double price){
        _toPay = _toPay + price;
        _owner.setToPay();
        _owner.getLevel().toNormal();
        _owner.getLevel().toGold();
        _owner.getLevel().toPlatinum();
    }

    public void addCommReceived(int id, Communication communication) {
        _commsReceived.put(id, communication);
    }

    public Communication getOnGoingComm(){
        return _onGoing;
    }

    public void endComm(double time) throws TerminalAlreadyOnException, TerminalAlreadyMuteException {
        updateComm(time, _onGoing);

        if(_lastState.getState().equals("IDLE"))
            _state.toIdle();

        if(_lastState.getState().equals("SILENCE"))
            _state.toMute();

        _onGoing.getTo().getState().toIdle();
        updateToPay(_onGoing.getPrice());
        _commPrice = (long) _onGoing.getPrice();
        _onGoing = null;

    }

    public void updateComm(Double time, Communication comm){
        comm.setTime(time);
        comm.statusToFinished();
        comm.setPrice();
    }

    public void setLastState(State lastState) {
        _lastState = lastState;
    }

    public boolean used(){
        return !_commsMade.isEmpty() || !_commsReceived.isEmpty();
    }

    public boolean positiveBalance(){ return _paid > _toPay; }

    public Map<String, Terminal> getFriendsMap() {
        return _friends;
    }

    public void registerFriend(String friend, Network _network) throws TerminalNotExistsException {
        Terminal f = _network.fetchTerminal(friend);
        if(!friend.equals(_terminalID))
            this.addFriend(friend, f);
    }

    public void removeFriend(String friend){
        if(_friends.containsKey(friend))
            _friends.remove(friend);
    }

    public void pay(String key) throws InvalidCommExcepption {
        int id = Integer.parseInt(key);
        if(_commsMade.containsKey(id) && !_commsMade.get(id).isPayed()
                && _commsMade.get(id).getStatus().equals("FINISHED")){
            _commsMade.get(id).pay();
            double price = _commsMade.get(id).getPrice();
            _paid = _paid + price;
            _toPay = _toPay - price;
            _owner.setToPay();
            _owner.setPaid();
            _owner.getLevel().toGold();
            _owner.getLevel().toPlatinum();
        }
        else
            throw new InvalidCommExcepption();
    }

    public long getCommPrice(){ return _commPrice; }
    public void addSendNotif(Terminal terminal){
        _sendNotif.addLast(terminal);
    }

    public void send(Notification notification){

       _sendNotif.forEach(terminal -> {
           if(terminal.getOwner().notifiable())
                   terminal.getOwner().getDelivery().send(notification);
       });
    }
}
