package prr.app.visitors;

import prr.comms.Communication;
import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;
import prr.visits.CommVisitor;

import static java.lang.Math.round;

public class RenderComms implements CommVisitor {

    /** Rendered Communication. */
    private String _rendering = "";

    private String renderFields1(Communication communication) {
        return communication.getCommType() + "|" +communication.getIdComm() + "|" + communication.getIdSender() + "|" +
                communication.getIdReceiver() + "|";
    }

    private String renderFields2(Communication communication) {
        return round(communication.getPrice()) + "|" + communication.getStatus();
    }

    @Override
    public void visitText(Text communication) {
        _rendering = renderFields1(communication) + communication.getNumChar() + "|" +
            renderFields2(communication);
    }

    @Override
    public void visitVideo(Video communication) {
        _rendering = renderFields1(communication) + round(communication.getTime()) + "|" + renderFields2(communication);
    }

    @Override
    public void visitVoice(Voice communication) {
        _rendering = renderFields1(communication) + round(communication.getTime()) + "|" + renderFields2(communication);
    }

    @Override
    public String toString(){
        return _rendering;
    }
}
