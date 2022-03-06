package com.example.imageclassifier;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageButton image_add;
    TextView uploaded, res;
    Button classify;
    int SELECT_PHOTO = 1;
    Uri uri;
    Window window;
    Animation animation;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //STATUS BAR COLOR CODE:
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            //window.setStatusBarColor(this.getResources().getColor(R.color.white));
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        uploaded = findViewById(R.id.textView);
        image_add = findViewById(R.id.image_add);
        Glide.with(this).load(R.drawable.image).into(image_add);

        classify = findViewById(R.id.button);
        res = findViewById(R.id.textView4);

        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityIfNeeded(intent, SELECT_PHOTO);
            }

        });


    }

    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData()!=null){
            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                image_add.setImageBitmap(bitmap);
                uploaded.setText("IMAGE UPLOADED");
                classify.setVisibility(View.VISIBLE);
                classify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { res.setText("Result will be displayed here! ");
                        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim);
                        res.startAnimation(animation);

                    }
                });

            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}