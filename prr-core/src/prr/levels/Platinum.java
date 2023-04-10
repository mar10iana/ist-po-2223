package prr.levels;

import prr.Client;
import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;
import prr.tariff.TariffPlatinum;

public class Platinum extends Level{

    public Platinum(Client client) {
        super(client);
        _tariff = new TariffPlatinum();
    }

    @Override
    public String getLevel() { return "PLATINUM"; }

    @Override
    public void toNormal(){
        if(_client.getBalance()<0)
            _client.setLevel(new Normal(_client));
    }

    @Override
    public void toGold(){
        if(_client.getTextCount()>=2 && _client.getBalance()>=0)
            System.out.println(_client.getTextCount());
            _client.setLevel(new Gold(_client));
    }

    @Override
    public void toPlatinum() { }
}
