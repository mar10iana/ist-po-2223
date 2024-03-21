package prr.tariff;

import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;

public class TariffNormal extends Tariff{

    @Override
    public double setPrice(Text text) {
        if(text.getNumChar()<50)
            return 10;
        if(text.getNumChar()<100 && text.getNumChar()>=50)
            return 16;
        else
            return 2 * text.getNumChar();
    }

    @Override
    public double setPrice(Voice voice) {
        return 20 * voice.getTime();
    }

    @Override
    public double setPrice(Video video) {
        return 30 * video.getTime();
    }
}
