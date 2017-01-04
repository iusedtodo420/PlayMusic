package com.example.patrick.playmusic;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity implements MediaPlayer.OnCompletionListener  {       //Add media player

    private ImageButton play, pause, stop, next;          //add
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {        //create project
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (ImageButton)findViewById(R.id.imageButton5);        //declare what it does
        pause = (ImageButton)findViewById(R.id.imageButton6);
        stop = (ImageButton)findViewById(R.id.imageButton7);
        next = (ImageButton)findViewById(R.id.imageButton);

        play.setOnClickListener(new View.OnClickListener() {        //on click do what you're told to
            public void onClick(View view) {
                play();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pause();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stop();
            }
        });

        setup();            //start
    }

    @Override
    public void onDestroy(){            //do and do not do something if something is doing something
        super.onDestroy();
        if(stop.isEnabled()){
            stop();
        }
    }
    public void onCompletion(MediaPlayer mp){
        stop();
    }
    private void play(){
        mp.start();
        play.setEnabled(false);
        pause.setEnabled(true);
        stop.setEnabled(true);
    }
    private void stop() {
        mp.stop();
        pause.setEnabled(false);
        stop.setEnabled(false);

        try{
            mp.prepare();
            mp.seekTo(0);
            play.setEnabled(true);
        }
        catch (Throwable t){
            goBlooey(t);
        }
    }
        private void pause() {
        mp.pause();
            play.setEnabled(true);
            pause.setEnabled(false);
            stop.setEnabled(true);
    }
    private void loadClip() {                                   //load the song
        try {
            mp = MediaPlayer.create(this, R.raw.cent_indaclub);
            mp.setOnCompletionListener(this);
        }
        catch (Throwable t) {
            goBlooey(t);
        }
    }
    private void setup(){               //play the song
        loadClip();
        play.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(false);
    }
    private void goBlooey(Throwable t){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setTitle("Peringatan")
                .setMessage(t.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}
