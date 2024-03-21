package prr.app.clients;


import prr.Network;
import prr.app.visitors.RenderClients;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

	DoShowAllClients(Network receiver) {
		super(Label.SHOW_ALL_CLIENTS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {

		_receiver.getClients().forEach(client -> {
			RenderClients renderer = new RenderClients();
			client.accept(renderer);
			_display.popup(renderer);
		});
	}
}
