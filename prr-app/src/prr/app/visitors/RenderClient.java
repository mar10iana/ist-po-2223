package prr.app.visitors;

import prr.Client;
import prr.visits.ClientVisitor;

import static java.lang.Math.round;

public class RenderClient implements ClientVisitor {

    /** Rendered client. */
    private String _rendering = "";

    private String renderFields(Client client) {

        return "CLIENT|"+  client.getClientID() + "|" + client.getName() +
                "|" + client.getNif() + "|" + client.getLevel().getLevel()
                + "|" + client.activeNotif() + "|" + client.numTerminal() +
                "|" + round(client.getPaid()) + "|" + round(client.getToPay())
                + client.getNotif();
    }

    @Override
    public void visitClient(Client client) {

        _rendering = renderFields(client);
    }

    @Override
    public String toString() { return _rendering; }
}