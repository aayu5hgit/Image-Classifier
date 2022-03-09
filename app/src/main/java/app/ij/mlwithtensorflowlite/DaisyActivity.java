package app.ij.mlwithtensorflowlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class DaisyActivity extends AppCompatActivity {
    ImageButton backbtn;
    Window window;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daisy);

        //STATUS BAR COLOR:
        if (Build.VERSION.SDK_INT >= 15) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.slip));
        }

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}