package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.app.visitors.RenderClient;
import prr.exceptions.ClientNotExistsException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

	DoShowClient(Network receiver) {
		super(Label.SHOW_CLIENT, receiver);
		addStringField("id", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		RenderClient renderer = new RenderClient();
		try {
			_receiver.fetchClient(stringField("id")).accept(renderer);
		}
		catch (ClientNotExistsException e) {
			throw new UnknownClientKeyException(stringField("id"));
		};

		if(renderer.toString().length() != 0)
			_display.popup(renderer);
	}
}
