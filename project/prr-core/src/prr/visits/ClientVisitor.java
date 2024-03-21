package prr.visits;

import prr.Client;

public interface ClientVisitor {

    /** @param client */
    void visitClient(Client client);
}
