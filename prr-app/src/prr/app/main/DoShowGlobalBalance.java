package prr.app.main;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import static java.lang.Math.round;

/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<Network> {

	DoShowGlobalBalance(Network receiver) {
		super(Label.SHOW_GLOBAL_BALANCE, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_display.popup(Message.globalPaymentsAndDebts(
				round(_receiver.getTotalPaid()),
				round(_receiver.getTotalToPay())));
	}
}
