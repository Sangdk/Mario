package sounds;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.io.File;

public class MusicManage {

    public static void play(String name){
        try {
            File f = new File("src/sounds/"+name);
            Sequence sequence = MidiSystem.getSequence(f);
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(9999);
            sequencer.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}