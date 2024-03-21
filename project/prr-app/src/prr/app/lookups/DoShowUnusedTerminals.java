package prr.app.lookups;

import prr.Network;
import prr.app.visitors.RenderTerminals;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {

	DoShowUnusedTerminals(Network receiver) {
		super(Label.SHOW_UNUSED_TERMINALS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_receiver.getUnusedTerminals().forEach(terminal -> {
			RenderTerminals renderer = new RenderTerminals();
			terminal.accept(renderer);
			_display.popup(renderer);
		});
	}
}
