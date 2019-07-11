package com.xyy.yhsh.glideyj;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView iv = findViewById(R.id.iv);
        /*Glide.with(MainActivity.this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515762447529&di=2efd7e54b4fe8d31f34a611ceb4770a2&imgtype=0&src=http%3A%2F%2Fpic33.nipic.com%2F20130907%2F13534366_092511672176_2.jpg")
                .transform(new YJtools(this, 20))
                .into(iv);*/
        //圆形
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515762447529&di=2efd7e54b4fe8d31f34a611ceb4770a2&imgtype=0&src=http%3A%2F%2Fpic33.nipic.com%2F20130907%2F13534366_092511672176_2.jpg").asBitmap().centerCrop().into(new BitmapImageViewTarget(iv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

}
