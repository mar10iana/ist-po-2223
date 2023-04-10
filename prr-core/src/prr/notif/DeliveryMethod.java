package prr.notif;

import java.io.Serializable;

public abstract class DeliveryMethod implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 21120221816L;

    public abstract void send(Notification notification);
}
