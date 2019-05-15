package com.itlx.vp.vpdroid.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itlx.vp.vpdroid.R;
import com.itlx.vp.vpdroid.helpers.BitmapHelper;

/**
 * Created by OakSoe on 9/4/2016.
 */
public class PanoramaFragment extends Fragment  {

    private static final int RESULT_LOAD_IMAGE = 1;
    public View thisView;

    private GestureDetectorCompat gestureDetector;

    private Bitmap panoramaBitmap;
    ImageView panoramaView;
    private int curBitmapX1, bitmapHeight, croppedBitmapWidth;
    private float prevX2, touchDownX;

    public PanoramaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.fragment_panorama, container,
                false);

        gestureDetector = new GestureDetectorCompat(getActivity(), new GestureListener());

//        String panoramaUri = "/storage/emulated/0/DCIM/CardboardCamera/IMG_20160715_135718.vr.jpg";
//
//        ImageView panoramaView = (ImageView) thisView.findViewById(R.id.panorama_view);
//        Bitmap panoramaBitmap = BitmapHelper.ShrinkBitmap(panoramaUri,
//                panoramaView.getWidth(),
//                panoramaView.getHeight());
//        panoramaView.setImageBitmap(panoramaBitmap);
//        panoramaView.setTag(panoramaUri);

        Button loadPanoramaViewButton = (Button) thisView.findViewById(R.id.load_panorama_view);
        loadPanoramaViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String panoramaUri = "/storage/emulated/0/DCIM/CardboardCamera/IMG_20160715_135718.vr.jpg";
                String panoramaUri = "/storage/emulated/0/DCIM/Panorama/Master1.jpg";

                panoramaView = (ImageView) thisView.findViewById(R.id.panorama_view);
                panoramaBitmap = BitmapHelper.ShrinkBitmap(panoramaUri,
                        panoramaView.getWidth(),
                        panoramaView.getHeight());

                int cropWidth = (int) (panoramaBitmap.getWidth() * ((double) panoramaBitmap.getHeight() / (double)panoramaView.getHeight()));
                prevX2 = 0;
                touchDownX = 0;
                curBitmapX1 = 0;
                croppedBitmapWidth = cropWidth;
                bitmapHeight = panoramaBitmap.getHeight();
                Bitmap croppedBitmap = Bitmap.createBitmap(panoramaBitmap, curBitmapX1, 0, cropWidth, panoramaBitmap.getHeight());

                panoramaView.setImageBitmap(croppedBitmap);
                panoramaView.setTag(panoramaUri);
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);

                panoramaView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        gestureDetector.onTouchEvent(motionEvent);
                        return true;
                    }
                });
            }
        });

        return thisView;
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//
//            Uri selectedImageUri = data.getData();
//            ImageView panoramaView = (ImageView) thisView.findViewById(R.id.panorama_view);
//
//            Bitmap panoramaBitmap = BitmapHelper.ShrinkBitmap(selectedImageUri.getEncodedPath(),
//                    panoramaView.getWidth(),
//                    panoramaView.getHeight());
//            panoramaView.setImageBitmap(panoramaBitmap);
//            panoramaView.setTag(selectedImageUri.getEncodedPath());
//        }
//    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Bitmap croppedBitmap;
            int move;

            if(touchDownX != e1.getX())
            {
                touchDownX = e1.getX();
                move = (int) (e2.getX() - e1.getX());
            }
            else
            {
                move = (int) (e2.getX() - prevX2);
            }
            prevX2 = e2.getX();

            curBitmapX1 += move * -1;

            if(curBitmapX1 > 0 && curBitmapX1 + croppedBitmapWidth < panoramaBitmap.getWidth())
            {
                croppedBitmap = Bitmap.createBitmap(panoramaBitmap, curBitmapX1, 0, croppedBitmapWidth, bitmapHeight);
                //String s = "P.width: " + panoramaBitmap.getWidth() + " , (x1,x2) : (" + curBitmapX1 + "," + curBitmapX2 + ")" + " = " + Math.abs(curBitmapX2 - curBitmapX1);
                //Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
                panoramaView.setImageBitmap(croppedBitmap);
            }
            else if(curBitmapX1 + croppedBitmapWidth >= panoramaBitmap.getWidth())
            {
                Bitmap bitmap1, bitmap2, mergedBitmap;
                int extraWidth = (curBitmapX1 + croppedBitmapWidth) - panoramaBitmap.getWidth();

                int bitmap1Width, bitmap2Width;

                bitmap1Width = croppedBitmapWidth - extraWidth;
                bitmap2Width = extraWidth;

                //if(bitmap1Width == 0)
                if(bitmap1Width <= 0)
                {
                    curBitmapX1 = 0;
                }
                else
                {
                    bitmap1 = Bitmap.createBitmap(panoramaBitmap, curBitmapX1, 0, bitmap1Width, bitmapHeight);
                    bitmap2 = Bitmap.createBitmap(panoramaBitmap, 0, 0, bitmap2Width, bitmapHeight);

                    int x = croppedBitmapWidth - extraWidth;//bitmap1.getWidth() + bitmap2.getWidth();

                    mergedBitmap = BitmapHelper.MergeBitmaps(bitmap1, bitmap2, getActivity());

                    panoramaView.setImageBitmap(mergedBitmap);
                }

            }

            return true;
        }
    }
}

