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


public class MainActivity extends ActionBarActivity {

    TextView text;
    MediaPlayer mp;
    Timer timer;
    Handler handler = new Handler();
    SeekBar seekbar;
    TextView currenttime,wholetime;

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
        currenttime = (TextView)findViewById(R.id.current_time);
        wholetime = (TextView)findViewById(R.id.whole_time);

        int duration = mp.getDuration();
        seekbar.setMax(duration);
        int duration_sec = duration / 1000;
        int current_m =  duration_sec / 60;
        int current_s = duration_sec % 60;

        String m = String.format(Locale.JAPAN, "%02d",current_m);
        String s = String.format(Locale.JAPAN, "%02d",current_s);

        wholetime.setText(m + ":" + s);
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
    }

    public void stop(View v){
        mp.stop();
    }

    public void pause(View v){
        mp.pause();
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
