package mipepe.music;
import java.util.ArrayList;

public class AudioThread extends Thread {
    @Override
    public void run() {
        ArrayList<MyShort> samples = App.waveToSamples(App.wave);
        //printWave(wave);
        //System.out.println("OK");
        
        //printSamples(samples);
        byte[] waveBytes = App.toBytes(samples);
        //printBytes(waveBytes);
        App.line.start();
        System.out.println(waveBytes.length);
        while(true) {
            App.line.write(waveBytes, 0, waveBytes.length);
            App.line.drain();
        }
        
    }
}