package com.example.user.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
    }


    public void singlePlayer(View view) {
        Intent launchGame = new Intent(this, single_player_activity.class);
        startActivity(launchGame);
    }

    public void twoPlayer(View view) {

        Intent LaunchGame = new Intent(this, two_player_activity.class);
        startActivity(LaunchGame);
    }

    public void ext(View view) {
        finish();
        System.exit(0);
    }

    public void about(View view) {
        Intent LaunchAbout = new Intent(this, about_activity.class);
        startActivity(LaunchAbout);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
