package app.ij.mlwithtensorflowlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class DandelionActivity extends AppCompatActivity {
    ImageButton backbtn;
    Button readmorebtn;
    Window window;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dandelion);

        //STATUS BAR COLOR:
        if (Build.VERSION.SDK_INT >= 15) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.main2));
        }

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        readmorebtn = findViewById(R.id.readmorebtn_dandelion);
        readmorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DandelionActivity.this, "Feature Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}