package prr.levels;


import prr.Client;
import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;
import prr.tariff.TariffGold;

public class Gold extends Level {

    public Gold(Client client) {
        super(client);
        _tariff = new TariffGold();
    }

    @Override
    public String getLevel() { return "GOLD"; }

    @Override
    public void toNormal(){
        if(_client.getBalance()<0)
            _client.setLevel(new Normal(_client));
    }

    @Override
    public void toGold() { }

    @Override
    public void toPlatinum(){
        if(_client.getVideoCount()>=5 && _client.getBalance()>=0)
            _client.setLevel(new Platinum(_client));
    }
}
