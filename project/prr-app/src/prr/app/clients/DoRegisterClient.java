package prr.app.clients;

import prr.Network;
import prr.app.exceptions.DuplicateClientKeyException;
import prr.exceptions.ClientExistsException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

	DoRegisterClient(Network receiver) {
		super(Label.REGISTER_CLIENT, receiver);
		addStringField("id", Prompt.key());
		addStringField("name", Prompt.name());
		addStringField("nif", Prompt.taxId());
	}

	@Override
	protected final void execute() throws CommandException {
		try{
			_receiver.registerClient("CLIENT", stringField("id"), stringField("name"),
					stringField("nif"));
		} catch (ClientExistsException e) {
			throw new DuplicateClientKeyException(e.getID());
		}
	}
}
