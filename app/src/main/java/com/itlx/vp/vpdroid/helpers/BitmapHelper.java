package com.itlx.vp.vpdroid.helpers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.widget.Toast;

/**
 * Created by darrylbayliss on 28/02/15.
 */
public class BitmapHelper {

    public static Bitmap ShrinkBitmap(String file, int width, int height) {

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        return bitmap;
    }

    public static Bitmap ShrinkAndCropBitmap(String file, int left, int top, int width, int height) {

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int bmpHeight = bmpFactoryOptions.outHeight;
        int bmpWidth = bmpFactoryOptions.outWidth;

        int heightRatio = (int) Math.ceil(bmpHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int cropWidth = (int) (bitmap.getWidth() * ((double) bitmap.getHeight() / (double)height));

        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, left, top, cropWidth, bitmap.getHeight());
        return croppedBitmap;
    }

    public static Bitmap MergeBitmaps(Bitmap bitmap1, Bitmap bitmap2, Activity a)
    {
        Bitmap newBitmap = null;

        int width, height = 0;

        //if(bitmap1.getWidth() > bitmap2.getWidth()) {
            width = bitmap1.getWidth() + bitmap2.getWidth();
            height = bitmap1.getHeight();
//        } else {
//            width = bitmap2.getWidth() + bitmap2.getWidth();
//            height = bitmap1.getHeight();
//        }

        newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(newBitmap);

        comboImage.drawBitmap(bitmap1, 0f, 0f, null);
        comboImage.drawBitmap(bitmap2, bitmap1.getWidth(), 0f, null);

        return newBitmap;
    }
}
