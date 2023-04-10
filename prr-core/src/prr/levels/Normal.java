package prr.levels;

import prr.Client;
import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;
import prr.tariff.TariffNormal;

public class Normal extends Level {

    public Normal(Client client) {
        super(client);
        _tariff = new TariffNormal();
    }

    @Override
    public String getLevel() { return "NORMAL"; }

    @Override
    public void toNormal() { }

    @Override
    public void toGold(){
        if(_client.getBalance()>500)
            _client.setLevel(new Gold(_client));
    }

    @Override
    public void toPlatinum() { }

}
