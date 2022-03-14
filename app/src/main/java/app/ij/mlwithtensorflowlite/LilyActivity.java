package app.ij.mlwithtensorflowlite;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class LilyActivity extends AppCompatActivity {
    Button readmorebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lily);

        readmorebtn = findViewById(R.id.readmorelily);
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://en.wikipedia.org/wiki/Lilium"));
        startActivity(browserIntent);

    }
}