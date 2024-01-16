package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound {
    public final static boolean SOUND_ON = true;
    public final static boolean SOUND_OFF = false;
    private static boolean soundSetting = SOUND_ON;
    
    public static void setSoundSetting(boolean setting){
        soundSetting = setting;
    }
    
    public static void sound(String fileName){
        sound(fileName,0);
    }
    public static void soundLoop(String fileName){
        sound(fileName,Clip.LOOP_CONTINUOUSLY);
    }
    
    public static void sound(String fileName,int loop){
        if( !soundSetting ){
            return;
        }
        AudioInputStream ais = null;
        try {
            Path p1 = Paths.get("sound\\"+fileName);
            Path p2 = p1.toAbsolutePath();

            ais = AudioSystem.getAudioInputStream(new File(p2.toString()));
            AudioFormat af = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, af);
            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(ais);
            clip.loop(loop);
            clip.flush();
            //while(clip.isActive()) {
            //   Thread.sleep(10);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                ais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
