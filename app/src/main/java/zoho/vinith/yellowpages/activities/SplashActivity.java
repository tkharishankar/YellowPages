package zoho.vinith.yellowpages.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import zoho.vinith.yellowpages.R;

public class SplashActivity extends Activity {
    SharedPreferences sharedpreferences;
    public static final String greetingPref = "greeting_prefs";
    public static final String isFirstTime = "isFirstTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedpreferences = getSharedPreferences(greetingPref, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!sharedpreferences.contains(isFirstTime)){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(isFirstTime, false);
                    editor.commit();
                    startActivity(new Intent(SplashActivity.this, GreetingActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        },3000);
    }
}
