package prr.app.lookups;

import prr.Network;
import prr.app.visitors.RenderClients;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show clients with positive balance.
 */
class DoShowClientsWithoutDebts extends Command<Network> {

	DoShowClientsWithoutDebts(Network receiver) {
		super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_receiver.getNoDebtClients().forEach(client -> {
			RenderClients renderer = new RenderClients();
			client.accept(renderer);
			_display.popup(renderer);
		});
	}
}
