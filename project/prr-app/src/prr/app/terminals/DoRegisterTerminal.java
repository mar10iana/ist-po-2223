package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.ClientNotExistsException;
import prr.exceptions.InvalidTerminalIdException;
import prr.exceptions.TerminalExistsException;
import prr.exceptions.UnrecognizedEntryException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	/** @param receiver */
	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
	}

	@Override
	protected final void execute() throws CommandException {

		String type;
		String id = Form.requestString(Prompt.terminalKey());
		do{
			type = Form.requestString(Prompt.terminalType());
		} while (!type.equals("BASIC") && !type.equals("FANCY"));
		String client = Form.requestString(Prompt.clientKey());

		try{
			_receiver.registerTerminal(type, id, client, "IDLE");
		}
		catch (InvalidTerminalIdException e){
			throw new InvalidTerminalKeyException(id);
		}
		catch (TerminalExistsException e){
			throw new DuplicateTerminalKeyException(id);
		}
		catch (ClientNotExistsException e) {
			throw new UnknownClientKeyException(client);
		}
		catch (UnrecognizedEntryException e) {
			e.printStackTrace();
		}

	}
}
