/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author hk_th
 */
public class Sound {
    private Clip clip;

    
    // Change file name to match yours, of course

    public static Sound hover = new Sound("/sounds/hover.wav");
    
    public static Sound click = new Sound("/sounds/click.wav");
    

     

   public Sound (String fileName) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));

            clip = AudioSystem.getClip();

            clip.open(ais);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

 

    public void play() {

        try {

            if (clip != null) {

                new Thread() {

                    public void run() {

                        synchronized (clip) {

                            clip.stop();

                            clip.setFramePosition(0);

                            clip.start();

                        }

                    }

                }.start();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

     

    public void stop(){

        if(clip == null) return;

        clip.stop();

    }

     

    public void loop() {

        try {

            if (clip != null) {

                new Thread() {

                    public void run() {

                        synchronized (clip) {

                            clip.stop();

                            clip.setFramePosition(0);

                            clip.loop(Clip.LOOP_CONTINUOUSLY);

                        }

                    }

                }.start();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

     

    public boolean isActive(){

        return clip.isActive();

    
    
}
}
