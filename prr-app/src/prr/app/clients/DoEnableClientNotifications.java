package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.ClientNotExistsException;
import prr.exceptions.OnNotifAlreadyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

	DoEnableClientNotifications(Network receiver) {
		super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
		addStringField("id", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.fetchClient(stringField("id")).turnOnNotif();
		}
		catch (ClientNotExistsException e) {
			throw new UnknownClientKeyException(stringField("id"));
		}
		catch (OnNotifAlreadyException e) {
			_display.popup(Message.clientNotificationsAlreadyEnabled());
		}
	}
}
