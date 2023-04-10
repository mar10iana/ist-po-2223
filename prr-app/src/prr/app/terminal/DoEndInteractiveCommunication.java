package prr.app.terminal;

import prr.Network;
import prr.exceptions.TerminalAlreadyMuteException;
import prr.exceptions.TerminalAlreadyOnException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

	DoEndInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
		addStringField("time",Prompt.duration());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.endComm(Double.parseDouble(stringField("time")));
			_display.popup(Message.communicationCost(_receiver.getCommPrice()));
		} catch (TerminalAlreadyOnException | TerminalAlreadyMuteException e) {
			e.printStackTrace();
		}
	}
}
