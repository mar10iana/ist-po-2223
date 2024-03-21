package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.ClientNotExistsException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import static java.lang.Math.round;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
		addStringField("id", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_display.popup(Message.clientPaymentsAndDebts(stringField("id"),
					round(_receiver.fetchClient(stringField("id")).getPaid()),
					round(_receiver.fetchClient(stringField("id")).getToPay())));
		} catch (ClientNotExistsException e) {
			throw new UnknownClientKeyException(stringField("id"));
		}
	}
}
