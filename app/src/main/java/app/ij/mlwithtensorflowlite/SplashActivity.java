package app.ij.mlwithtensorflowlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.scwang.wave.MultiWaveHeader;

public class SplashActivity extends AppCompatActivity {

    TextView name;
    LottieAnimationView intro;
    Animation fadein;
    ImageButton start;
    Window window;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        //        REMOVE TITLE SLIP
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();
//
//        MultiWaveHeader waveHeader, waveFooter;
//        waveHeader = findViewById(R.id.wave_header);
////        waveFooter = findViewById(R.id.wave_footer);
//
//        waveHeader.setVelocity(2);
//        waveHeader.setProgress(1);
//        waveHeader.isRunning();
//        waveHeader.setGradientAngle(45);
//        waveHeader.setStartColor(Color.parseColor("#E0F1F8"));
//        waveHeader.setCloseColor(Color.parseColor("#A569BD"));

        //STATUS BAR COLOR:
        if (Build.VERSION.SDK_INT >= 15) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.main));
        }
        name = findViewById(R.id.name);
        intro = findViewById(R.id.lottie);

        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        name.startAnimation(fadein);

//        name.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
//        intro.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), OnboardingActivity.class));
//                finish();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.anim);
                start.startAnimation(animation);
            }
        });
        //SWITCHING ACTIVITY:
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 5000);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
