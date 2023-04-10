package prr.tariff;

import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;

public class TariffGold extends Tariff{
    @Override
    public double setPrice(Text text) {
        if(text.getNumChar()<100)
            return 10;
        else
            return 2 * text.getNumChar();
    }

    @Override
    public double setPrice(Voice voice) {
        return 10 * voice.getTime();
    }

    @Override
    public double setPrice(Video video) {
        return 20 * video.getTime();
    }
}
