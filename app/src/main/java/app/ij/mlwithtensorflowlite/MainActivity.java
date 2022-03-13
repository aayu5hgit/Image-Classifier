
package app.ij.mlwithtensorflowlite;

import static org.checkerframework.checker.units.UnitsTools.s;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import app.ij.mlwithtensorflowlite.ml.Model;

public class MainActivity extends AppCompatActivity {

    TextView result, confidence, classified;
    ImageView imageView, backbtn;
    Uri uri;
//    Button prob;
    ImageButton picture, importbtn, info;
    int imageSize = 224;
    int SELECT_PHOTO = 1;
    Window window;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        //        REMOVE TITLE SLIP
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();

        result = findViewById(R.id.result);
        confidence = findViewById(R.id.confidence);
        imageView = findViewById(R.id.imageView);
        picture = findViewById(R.id.button);
//        prob = findViewById(R.id.probbutton);
        backbtn = findViewById(R.id.backbtn);
        info = findViewById(R.id.infobtn);
        classified = findViewById(R.id.classified);
        importbtn = findViewById(R.id.importbtn);


        //STATUS BAR COLOR:
        if (Build.VERSION.SDK_INT >= 15) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.main2));
        }


//        prob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Coming Soon...", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(getApplicationContext(), ProbabilityActivity.class);
//                startActivity(i);
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
//                prob.startAnimation(animation);
//            }
//        });

//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent j = new Intent(getApplicationContext(), SplashActivity.class);
//                startActivity(j);
//            }
//        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
                picture.startAnimation(animation);
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        importbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                Toast.makeText(MainActivity.this, "Coming Soon...", Toast.LENGTH_SHORT).show();
                startActivityIfNeeded(intent, SELECT_PHOTO);
            }

        });

    }

    public void classifyImage(Bitmap image) {
        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // get 1D array of 224 * 224 pixels in image
            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            int pixel = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Sunflower", "Tulip", "Daisy", "Lily"};
            result.setText(classes[maxPos]);

            String s = "";

            if (classes[maxPos] == "Sunflower") {
                info.setVisibility(View.VISIBLE);
//                prob.setVisibility(View.VISIBLE);
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent k = new Intent(getApplicationContext(), SunflowerActivity.class);
//                        startActivity(k);
//                        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
//                        info.startAnimation(animation);
                        final BottomSheetDialog bottomSheetDialogS = new BottomSheetDialog(
                                MainActivity.this, R.style.BottomSheetDialogThemeSun
                        );
                        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                                .inflate(
                                        R.layout.activity_sunflower,
                                        (LinearLayout)findViewById(R.id.bottomSheetSunflower)
                                );
                        bottomSheetDialogS.setContentView(bottomSheetView);
                        bottomSheetDialogS.show();
                    }
                });
            } else if (classes[maxPos] == "Lily") {
                info.setVisibility(View.VISIBLE);
//                prob.setVisibility(View.VISIBLE);
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent l = new Intent(getApplicationContext(), LilyActivity.class);
//                        startActivity(l);
//                        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
//                        info.startAnimation(animation);
                        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                                MainActivity.this, R.style.BottomSheetDialogTheme
                        );
                        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                                .inflate(
                                        R.layout.activity_lily,
                                        (LinearLayout)findViewById(R.id.bottomSheetContainer)
                                );
                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetDialog.show();
                    }
                });
            } else if (classes[maxPos] == "Daisy") {
                info.setVisibility(View.VISIBLE);
//                prob.setVisibility(View.VISIBLE);
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent m = new Intent(getApplicationContext(), DaisyActivity.class);
//                        startActivity(m);
//                        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
//                        info.startAnimation(animation);
                        final BottomSheetDialog bottomSheetDialogD = new BottomSheetDialog(
                                MainActivity.this, R.style.BottomSheetDialogThemeDaisy
                        );
                        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                                .inflate(
                                        R.layout.activity_daisy,
                                        (LinearLayout)findViewById(R.id.bottomSheetDaisy)
                                );
                        bottomSheetDialogD.setContentView(bottomSheetView);
                        bottomSheetDialogD.show();
                    }
                });
            } else if (classes[maxPos] == "Tulip") {
                info.setVisibility(View.VISIBLE);
//                prob.setVisibility(View.VISIBLE);
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent m = new Intent(getApplicationContext(), TulipActivity.class);
//                        startActivity(m);
//                        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
//                        info.startAnimation(animation);
                        final BottomSheetDialog bottomSheetDialogT = new BottomSheetDialog(
                                MainActivity.this, R.style.BottomSheetDialogThemeTulip
                        );
                        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                                .inflate(
                                        R.layout.activity_tulip,
                                        (LinearLayout)findViewById(R.id.bottomSheetTulip)
                                );
                        bottomSheetDialogT.setContentView(bottomSheetView);
                        bottomSheetDialogT.show();
                    }
                });

            }

//            for(int i = 0; i < classes.length; i++){
//                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
//            }
//            confidence.setText(s);


            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }


        @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);
            classified.setVisibility(View.VISIBLE);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

