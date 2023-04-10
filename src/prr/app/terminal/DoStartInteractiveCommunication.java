package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.*;
import prr.terminals.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
	}

	@Override
	protected final void execute() throws CommandException {

		String type;
		String to = Form.requestString(Prompt.terminalKey());
		do{
			type = Form.requestString(Prompt.commType());
		} while (!type.equals("VOICE") && !type.equals("VIDEO"));

		try {
			_receiver.registerInteractiveCommunication(to,type, _network);
		} catch (TerminalNotExistsException e) {
			throw new UnknownTerminalKeyException(to);
		} catch (ToTerminalIsOffException e) {
			_display.popup(Message.destinationIsOff(to));
		} catch (ToTerminalIsMuteException e) {
			_display.popup(Message.destinationIsSilent(to));
		} catch (ToTerminalIsBusyException e) {
			_display.popup(Message.destinationIsBusy(to));
		} catch (UnsupportedAtDestinationException e) {
			_display.popup(Message.unsupportedAtDestination(to,type));
		} catch (UnsupportedAtOriginException e) {
			_display.popup(Message.unsupportedAtOrigin(to, type));
		} catch (UnrecognizedEntryException e) {
			e.printStackTrace();
		}
	}
}
