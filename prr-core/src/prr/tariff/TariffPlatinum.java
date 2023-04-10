package prr.tariff;

import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;

public class TariffPlatinum extends Tariff{

    @Override
    public double setPrice(Text text) {
        if(text.getNumChar()<50)
            return 0;
        else
            return 4;
    }

    @Override
    public double setPrice(Voice voice) {
        return 10 * voice.getTime();
    }

    @Override
    public double setPrice(Video video) {
        return 10 * video.getTime();
    }
}
