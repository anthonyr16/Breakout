import javax.sound.sampled.*;
import java.io.*;
import javax.swing.*;

public class Audio
{
    InputStream music;
    Clip clip;
    public Audio(String filepath){
        try{
            File musicPath = new File(filepath);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            }else{
                System.out.println("Can't find audio file");
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void loop(){
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void playOnce(){
        clip.start();
        clip.setFramePosition(0);
    }
    
    public void stop(){
        clip.stop();
    }
    
    
}
