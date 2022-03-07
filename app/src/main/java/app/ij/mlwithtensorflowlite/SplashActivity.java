package app.ij.mlwithtensorflowlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.scwang.wave.MultiWaveHeader;

public class SplashActivity extends AppCompatActivity {

    TextView name;
    LottieAnimationView intro;
    Animation fadein;
//    Window window;
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        //        REMOVE TITLE SLIP
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();

        MultiWaveHeader waveHeader, waveFooter;
        waveHeader = findViewById(R.id.wave_header);
//        waveFooter = findViewById(R.id.wave_footer);

        waveHeader.setVelocity(2);
        waveHeader.setProgress(1);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setStartColor(Color.parseColor("#E0F1F8"));
        waveHeader.setCloseColor(Color.parseColor("#A569BD"));

//        //STATUS BAR COLOR:
//        if (Build.VERSION.SDK_INT >= 21) {
//            window = this.getWindow();
//            window.setStatusBarColor(this.getResources().getColor(R.color.white));
//        }
        name = findViewById(R.id.name);
        intro = findViewById(R.id.lottie);

        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        name.startAnimation(fadein);

        name.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        intro.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        //SWITCHING ACTIVITY:
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}