package com.yasser.roknaapp.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yasser.roknaapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.felix.imagezoom.ImageZoom;
import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;
import ozaydin.serkan.com.image_zoom_view.ImageViewZoomConfig;
import ozaydin.serkan.com.image_zoom_view.SaveFileListener;

public class ViewImageActivity extends AppCompatActivity {
    ImageView iv_back_arrow,iv_share;
    ImageViewZoom imageZoom;

    Intent getURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        getURL = getIntent();
        imageZoom = findViewById(R.id.iv_image_preview);
        iv_back_arrow = findViewById(R.id.iv_backarrow);
        iv_share = findViewById(R.id.iv_share);


        String ImageURL = getURL.getStringExtra("image_url");
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.roknalogo);

        Glide.with(ViewImageActivity.this)
                .load(ImageURL)
                .apply(requestOptions)
                .into(imageZoom);


        // Return Image's base 64 code
        imageZoom.getBase64();

        // ImageViewZoomConfig
        // OnlyDialog Enum work only user when click to save choose
        // Always Enum work when use saveImage() method and user when click to save choose
        ImageViewZoomConfig imageViewZoomConfig =new ImageViewZoomConfig.Builder().saveProperty(true).saveMethod(ImageViewZoomConfig.ImageViewZoomConfigSaveMethod.onlyOnDialog).build();


        imageZoom.setConfig(imageViewZoomConfig);


        // Save Image
        imageZoom.saveImage(ViewImageActivity.this, "ImageViewZoom", "Rokna images", Bitmap.CompressFormat.JPEG, 1, imageViewZoomConfig, new SaveFileListener() {
            @Override
            public void onSuccess(File file) {
                Toast.makeText(ViewImageActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Exception excepti) {
                Toast.makeText(ViewImageActivity.this, "Error Save", Toast.LENGTH_SHORT).show();
            }
        });

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
              screenShot(imageZoom);
                shareBitmap(screenShot(imageZoom),"myimage");
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();

    }
    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    //////// this method share your image
    private void shareBitmap (Bitmap bitmap,String fileName) {
        try {
            File file = new File(getApplicationContext().getCacheDir(), fileName + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(     android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    }

