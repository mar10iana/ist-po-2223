package prr.visits;


import prr.comms.Text;
import prr.comms.Video;
import prr.comms.Voice;

public interface CommVisitor {

    void visitText(Text text);

    void visitVideo(Video video);

    void visitVoice(Voice voice);

}

