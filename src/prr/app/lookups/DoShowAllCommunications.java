package prr.app.lookups;

import prr.Network;
import prr.app.visitors.RenderComms;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for showing all communications.
 */
class DoShowAllCommunications extends Command<Network> {

	DoShowAllCommunications(Network receiver) {
		super(Label.SHOW_ALL_COMMUNICATIONS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_receiver.getCommunications().forEach(comm -> {
			RenderComms renderer = new RenderComms();
			comm.accept(renderer);
			_display.popup(renderer);
		});
	}
}
