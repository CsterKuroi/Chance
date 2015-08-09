package com.kuroi.chance.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;

import com.kuroi.chance.R;

import java.io.File;
import java.io.IOException;

public class ShowActivity extends ActionBarActivity {
    private static final String ACTIVITY_TAG="LogDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Log.d(ACTIVITY_TAG,"show");
        Intent intent = getIntent();
        String picName= intent.getStringExtra("picName");
        Log.d(ACTIVITY_TAG,picName);
        if(new File(picName).isFile()){
            int digree=getExifOrientation(picName);
            Bitmap bm;
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = 4;
            bm = BitmapFactory.decodeFile(picName, option);
            if (digree != 0) {
                // 旋转图片
                Matrix m = new Matrix();
                m.postRotate(digree);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
            }

            ImageView pic = (ImageView) findViewById(R.id.show_pic);
            Log.d(ACTIVITY_TAG, "ok");
            pic.setImageBitmap(bm);
        }
    }

    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            // MmsLog.e(ISMS_TAG, "getExifOrientation():", ex);
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        break;
                }
            }
        }
        return degree;
    }
}
