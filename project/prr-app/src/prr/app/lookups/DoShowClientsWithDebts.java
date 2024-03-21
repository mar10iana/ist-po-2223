package prr.app.lookups;

import prr.Network;
import prr.app.visitors.RenderClients;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with negative balance.
 */
class DoShowClientsWithDebts extends Command<Network> {

	DoShowClientsWithDebts(Network receiver) {
		super(Label.SHOW_CLIENTS_WITH_DEBTS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_receiver.getDebtClients().forEach(client -> {
			RenderClients renderer = new RenderClients();
			client.accept(renderer);
			_display.popup(renderer);
		});
	}
}
