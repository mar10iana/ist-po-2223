package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

	DoRemoveFriend(Network context, Terminal terminal) {
		super(Label.REMOVE_FRIEND, context, terminal);
		addStringField("friend", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		_receiver.removeFriend(stringField("friend"));
	}
}
