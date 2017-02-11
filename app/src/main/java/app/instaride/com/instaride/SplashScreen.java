package app.instaride.com.instaride;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    private PrefManager prefManager;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                prefManager = new PrefManager(SplashScreen.this);
                if (!prefManager.isFirstTimeLaunch()) {
                    prefManager.setFirstTimeLaunch(false);
                    Intent mainIntent = new Intent(SplashScreen.this, WelcomeActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
//
//                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
//                    SplashScreen.this.finish();
                }
                else {
                    Intent mainIntent = new Intent(SplashScreen.this, WelcomeActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
