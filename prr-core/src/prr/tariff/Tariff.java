package prr.tariff;

import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;

import java.io.Serializable;

public abstract class Tariff implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 31120221718L;
    public abstract double setPrice(Text text);
    public abstract double setPrice(Voice voice);
    public abstract double setPrice(Video video);
}
