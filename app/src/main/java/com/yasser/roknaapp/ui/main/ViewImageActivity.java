package com.yasser.roknaapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.yasser.roknaapp.R;

public class ViewImageActivity extends AppCompatActivity {
    ImageView iv_preview,iv_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
    }
}
