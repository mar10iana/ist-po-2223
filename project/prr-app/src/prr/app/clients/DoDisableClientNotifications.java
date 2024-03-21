package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.ClientNotExistsException;
import prr.exceptions.OffNotifAlreadyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

	DoDisableClientNotifications(Network receiver) {
		super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("id", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.fetchClient(stringField("id")).turnOffNotif();
		}
		catch (ClientNotExistsException e) {
			throw new UnknownClientKeyException(stringField("id"));
		}
		catch (OffNotifAlreadyException e) {
			_display.popup(Message.clientNotificationsAlreadyDisabled());
		}
	}
}
