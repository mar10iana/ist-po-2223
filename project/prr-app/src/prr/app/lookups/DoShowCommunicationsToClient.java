package prr.app.lookups;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.app.visitors.RenderComms;
import prr.exceptions.ClientNotExistsException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

	DoShowCommunicationsToClient(Network receiver) {
		super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
		addStringField("id", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try{
			_receiver.fetchClient(stringField("id")).getReceivedComms().forEach(comm -> {
				RenderComms renderer = new RenderComms();
				comm.accept(renderer);
				_display.popup(renderer);
			});
		} catch (ClientNotExistsException e) {
			throw new UnknownClientKeyException(stringField("id"));
		}
	}
}
