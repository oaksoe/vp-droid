package com.itlx.vp.vpdroid.fragments;

import android.content.Context;
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
import android.widget.TextView;

import com.itlx.vp.vpdroid.R;
import com.itlx.vp.vpdroid.helpers.BitmapHelper;

/**
 * Created by OakSoe on 9/4/2016.
 */
public class BuildingFragment extends Fragment {

    public View thisView;

    private GestureDetectorCompat gestureDetector;

    private Bitmap buildingBitmap;
    private ImageView buildingView;
    private int curBitmapX1, bitmapHeight, croppedBitmapWidth;
    private float prevX2, touchDownX;
    private int curBuildingImageIndex, minImageIndex, maxImageIndex;

    public BuildingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.fragment_building, container,
                false);

        gestureDetector = new GestureDetectorCompat(getActivity(), new BuildingFragment.GestureListener());

        Button loadBuildingViewButton = (Button) thisView.findViewById(R.id.load_building_view);
        loadBuildingViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                minImageIndex = 10000;
                maxImageIndex = 10120;
                curBuildingImageIndex = minImageIndex;
                String buildingUri = "/storage/emulated/0/DCIM/Building3dImages/" + curBuildingImageIndex + ".jpg";

                buildingView = (ImageView) thisView.findViewById(R.id.building_view);
                buildingBitmap = BitmapHelper.ShrinkBitmap(buildingUri,
                        buildingView.getWidth(),
                        buildingView.getHeight());

                buildingView.setImageBitmap(buildingBitmap);
                //buildingView.setTag(buildingUri);

                buildingView.setOnTouchListener(new View.OnTouchListener() {
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

    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
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

            curBuildingImageIndex += (move % 10) * -1;

            if(curBuildingImageIndex > maxImageIndex)
                curBuildingImageIndex = minImageIndex;
            else if(curBuildingImageIndex < minImageIndex)
                curBuildingImageIndex = maxImageIndex;

            String buildingUri = "/storage/emulated/0/DCIM/Building3dImages/" + curBuildingImageIndex + ".jpg";

            buildingView = (ImageView) thisView.findViewById(R.id.building_view);
            buildingBitmap = BitmapHelper.ShrinkBitmap(buildingUri,
                    buildingView.getWidth(),
                    buildingView.getHeight());

            buildingView.setImageBitmap(buildingBitmap);

            return true;
        }
    }
}
