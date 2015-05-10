package com.example.chiharu_miyosi.music_player;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    TextView text;
    MediaPlayer mp;
    Timer timer;
    Handler handler = new Handler();
    SeekBar seekbar;
    TextView currenttime_t,wholetime_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = null;
        mp = MediaPlayer.create(this,R.raw.good_enough);

        try{
            mp.prepare();
        }catch(IllegalStateException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        text = (TextView)findViewById(R.id.title);
        text.setText("Good Enough");
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        currenttime_t = (TextView)findViewById(R.id.current_time);
        wholetime_t = (TextView)findViewById(R.id.whole_time);

        int duration = mp.getDuration();
        int duration_sec = duration / 1000;
        int current_m =  duration_sec / 60;
        int current_s = duration_sec % 60;
        seekbar.setMax(duration);

        String m = String.format(Locale.JAPAN, "%02d",current_m);
        String s = String.format(Locale.JAPAN, "%02d",current_s);

        wholetime_t.setText(m + ":" + s);
    }

    public void onStopTrackingTouch(SeekBar seekbar){
        int progress = seekbar.getProgress();
        mp.seekTo(progress);
        mp.start();
    }

    public void onStartTrackingTouch(SeekBar seekbar){
        int progress = seekbar.getProgress();
        mp.seekTo(progress);
        mp.pause();
    }

    public void start(View v){
        mp.start();

        if(timer == null){
            timer = new Timer();
            timer.schedule(new TimerTask(){
                @Override
                public void run(){
                    int duration = mp.getCurrentPosition() / 1000;

                    int current_m = duration / 60;
                    int current_s = duration % 60;

                    final String m = String.format(Locale.JAPAN,"%02d",current_m);
                    final String s = String.format(Locale.JAPAN,"%02d",current_s);

                    handler.post(new Runnable(){
                        @Override
                        public void run(){
                            currenttime_t.setText(m + ":" + s);
                            seekbar.setProgress(mp.getCurrentPosition());
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    public void stop(View v){
        mp.stop();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

    public void pause(View v){
        mp.pause();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
