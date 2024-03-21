package prr.levels;

import prr.Client;
import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;
import prr.tariff.Tariff;

import java.io.Serializable;

public abstract class Level implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 171020222327L;

    protected Client _client;
    protected Tariff _tariff;

    public Level(Client client){
        _client = client;
    }

    public abstract String getLevel();

    public abstract void toNormal();
    public abstract void toGold();
    public abstract void toPlatinum();

    public double setPrice(Text text) {
        return _tariff.setPrice(text);
    }

    public double setPrice(Voice voice) {
        return _tariff.setPrice(voice);
    }

    public double setPrice(Video video) {
        return _tariff.setPrice(video);
    }

}
