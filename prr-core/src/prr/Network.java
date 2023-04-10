package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


import prr.comms.Communication;
import prr.exceptions.ClientExistsException;
import prr.exceptions.ClientNotExistsException;
import prr.exceptions.ImportFileException;
import prr.exceptions.InvalidTerminalIdException;
import prr.exceptions.TerminalExistsException;
import prr.exceptions.TerminalNotExistsException;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.Basic;
import prr.terminals.Fancy;
import prr.terminals.Terminal;
import prr.terminals.states.Busy;
import prr.terminals.states.Idle;
import prr.terminals.states.Mute;
import prr.terminals.states.Off;
import prr.terminals.states.State;


public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 121020221835L;

	/** Network's Clients. */
	private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER) ;

	/** Network's Terminals. */
	private Map<String, Terminal> _terminals = new TreeMap<>();

	/** Network's Communications*/

	private Map<Integer, Communication> _comms = new TreeMap<>();

	/** Network object has been changed. */
	private boolean _changed = false;

	/** Set changed. */
	public void changed() { setChanged(true);}

	/** @return changed */
	public boolean hasChanged() { return _changed; }

	/** @param changed */
	public void setChanged(boolean changed) { _changed = changed; }


	/**
	 * Read text input file and create corresponding domain entities (Clients, Terminals and Terminals' Friends).
	 *
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO error while processing the text file
	 * @throws ImportFileException if there is an IO error while processing the text file
	 */

	void importFile(String filename) throws UnrecognizedEntryException, IOException, ImportFileException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("[|,]");
				try {
					registerEntry(fields);
				} catch (UnrecognizedEntryException | ClientExistsException | ClientNotExistsException |
						 TerminalExistsException | InvalidTerminalIdException | TerminalNotExistsException  e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			throw new ImportFileException(filename);
		}
	}

	/**
	 * Analise each entry and create corresponding domain entities.
	 * @param fields 0 - type of entity; others - specific to each entity
	 * @throws UnrecognizedEntryException
	 * @throws ClientNotExistsException
	 * @throws TerminalExistsException
	 * @throws InvalidTerminalIdException
	 * @throws ClientExistsException
	 * @throws TerminalNotExistsException
	 */
	public void registerEntry(String... fields) throws UnrecognizedEntryException, ClientNotExistsException,
			TerminalExistsException, InvalidTerminalIdException, ClientExistsException, TerminalNotExistsException {
		switch (fields[0]) {
			case "CLIENT" -> registerClient(fields);
			case "BASIC", "FANCY" -> registerTerminal(fields);
			case "FRIENDS" -> registerFriend(fields);
			default -> throw new UnrecognizedEntryException(fields[0]);
		}
	}

	/**
	 * Create new Client.
	 * @param fields 1 - Client's id; 2- Client's name; 3- Client's tax number
	 * @throws ClientExistsException
	 */
	public void registerClient(String... fields) throws ClientExistsException {

		int nif = Integer.parseInt(fields[3]);
		String id = fields[1];
		Client client = new Client(id, fields[2], nif);

		addClient(id, client);
		changed();
	}

	/**
	 * Create new Terminal and associate with their owner (Client).
	 * @param fields 0 - Terminal's type; 1- Terminal's id; 2- Terminal's owner; 3- Terminal's state
	 * @throws InvalidTerminalIdException
	 * @throws UnrecognizedEntryException
	 * @throws ClientNotExistsException
	 * @throws TerminalExistsException
	 */
	public void registerTerminal(String... fields) throws InvalidTerminalIdException, UnrecognizedEntryException,
			ClientNotExistsException, TerminalExistsException {

		String terminalID = fields[1];
		if (terminalID.length() != 6)
			throw new InvalidTerminalIdException(terminalID);

		for ( int i = 0; i < 6; i++ ) {
			if (!Character.isDigit(terminalID.charAt(i)))
				throw new InvalidTerminalIdException(terminalID);
		}

		Client owner = fetchClient(fields[2]);

		Terminal terminal = switch (fields[0]) {
			case "BASIC" -> new Basic(terminalID, owner);
			case "FANCY" -> new Fancy(terminalID, owner);
			default -> throw new UnrecognizedEntryException(fields[0]);
		};

		State state = insertState(fields[3], terminal);
		terminal.setState(state);

		owner.addTerminal(terminalID, terminal);
		addTerminalN(terminalID, terminal);
		changed();
	}

	/**
	 * Update Terminal's friend list.
	 * @param fields 1 - Terminal's id; others - Friend's id
	 * @throws TerminalNotExistsException
	 */
	public void registerFriend(String[] fields) throws TerminalNotExistsException {

		Terminal t1 = fetchTerminal(fields[1]);

		for(int i=2; i < fields.length; i++){
			Terminal friend = fetchTerminal(fields[i]);
			t1.addFriend(fields[i], friend);
		}
		changed();
	}

	/**
	 * Add Client to network.
	 * @param id
	 * @param client
	 * @throws ClientExistsException
	 */
	public void addClient(String id, Client client) throws ClientExistsException {
		if (_clients.containsKey(id))
			throw new ClientExistsException(id);
		_clients.put(id, client);
	}

	/**
	 * Get specific Client.
	 * @param clientID
	 * @return Client
	 * @throws ClientNotExistsException
	 */
	public Client fetchClient(String clientID) throws ClientNotExistsException {
		if (!_clients.containsKey(clientID))
			throw new ClientNotExistsException(clientID);

		return _clients.get(clientID);
	}

	/**
	 * Create state from imported data.
	 * @param stateName
	 * @return state
	 * @throws UnrecognizedEntryException
	 */
	public State insertState(String stateName, Terminal terminal) throws UnrecognizedEntryException {
		State state = switch (stateName) {
			case "OFF" -> new Off(terminal);
			case "SILENCE" -> new Mute(terminal);
			case "IDLE", "ON" -> new Idle(terminal);
			case "BUSY" -> new Busy(terminal);
			default -> throw new UnrecognizedEntryException(stateName);
		};
		return state;
	}

	/**
	 * Get specific Terminal.
	 * @param terminalID
	 * @return Terminal
	 * @throws TerminalNotExistsException
	 */
	public Terminal fetchTerminal(String terminalID) throws TerminalNotExistsException {
		if(!_terminals.containsKey(terminalID))
			throw new TerminalNotExistsException(terminalID);

		return _terminals.get(terminalID);
	}

	/**
	 * Add Terminal to Network.
	 * @param id
	 * @param terminal
	 * @throws TerminalExistsException
	 */
	public void addTerminalN(String id, Terminal terminal) throws TerminalExistsException {
		if(_terminals.containsKey(id))
			throw new TerminalExistsException(id);

		_terminals.put(id, terminal);
	}

	/** Get all clients. */
	public Collection<Client> getClients(){
		return _clients.values();
	}

	/** Get all terminals. */
	public Collection<Terminal> getTerminals(){
		return _terminals.values();
	}

	/** Get all communications. */
	public Collection<Communication> getCommunications(){
		return _comms.values();
	}

	/** Get unused Terminals. */
	public Collection<Terminal> getUnusedTerminals() {

		return getTerminals().stream().
				filter(terminal -> !terminal.used()).
				collect(Collectors.toList());
	}

	/** Get value paid by all clients. */
	public double getTotalPaid(){

		Collection<Client> total = _clients.values();

		double paid = 0;
		for (Client client : total) {
			paid += client.getPaid();
		}
		return paid;
	}

	/** Get value to paid by all clients. */
	public double getTotalToPay(){

		Collection<Client> total = _clients.values();

		double toPay = 0;
		for (Client client : total) {
			toPay += client.getToPay();
		}
		return toPay;
	}

	/** Add communication to the Network. */
	public void addComms(int id, Communication comm) {
		_comms.put(id,comm);
	}

	/** Get Terminals with positive balance. */
	public Collection<Terminal> getPositiveTerminals() {

		return getTerminals().stream().
				filter(terminal -> terminal.positiveBalance()).
				collect(Collectors.toList());
	}

	/** Get Clients with debts. */
	public Collection<Client> getDebtClients() {

		return getClients().stream().
				filter(client -> client.getToPay()!=0).
				collect(Collectors.toList());
	}

	/** Get Clients without debts. */
	public Collection<Client> getNoDebtClients() {

		return getClients().stream().
				filter(client -> client.getToPay()==0).
				collect(Collectors.toList());
	}

}