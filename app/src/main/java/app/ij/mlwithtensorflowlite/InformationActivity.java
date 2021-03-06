package app.ij.mlwithtensorflowlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InformationActivity extends AppCompatActivity {
    Button readmorebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        readmorebtn = (Button)findViewById(R.id.readmorebtn);
//        readmorebtn = findViewById(R.id.readmorebtn);
        readmorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://en.wikipedia.org/wiki/Helianthus";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
//                Intent Getintent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://en.wikipedia.org/wiki/Helianthus"));
//                startActivity(Getintent);
            }
        });
    }
}