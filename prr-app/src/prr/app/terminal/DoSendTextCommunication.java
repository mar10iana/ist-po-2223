package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.TerminalNotExistsException;
import prr.exceptions.ToTerminalIsOffException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("to", Prompt.terminalKey());
                addStringField("sms", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                try {
                        _receiver.registerTextCommunication(stringField("to"),stringField("sms"),_network);
                } catch (TerminalNotExistsException e) {
                        throw new UnknownTerminalKeyException(stringField("to"));
                } catch (ToTerminalIsOffException e) {
                        _display.popup(Message.destinationIsOff(stringField("to")));
                }
        }
} 
