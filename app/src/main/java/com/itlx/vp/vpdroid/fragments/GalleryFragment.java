package com.itlx.vp.vpdroid.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.itlx.vp.vpdroid.R;
import com.itlx.vp.vpdroid.helpers.BitmapHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OakSoe on 11/8/2016.
 */


public class GalleryFragment extends Fragment {

    public View thisView;

    private List<ImageView> photoViews;

    public GalleryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.fragment_gallery, container,
                false);

        photoViews = new ArrayList<ImageView>();
        photoViews.add((ImageView) thisView.findViewById(R.id.photo_view1));
        photoViews.add((ImageView) thisView.findViewById(R.id.photo_view2));
        photoViews.add((ImageView) thisView.findViewById(R.id.photo_view3));
        photoViews.add((ImageView) thisView.findViewById(R.id.photo_view4));
        photoViews.add((ImageView) thisView.findViewById(R.id.photo_view5));

        Button loadCompoundViewButton = (Button) thisView.findViewById(R.id.load_compound_view);
        loadCompoundViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int photoCounter = 1;
                String photoUri;
                Bitmap photoBitmap;
                for(ImageView photoView : photoViews)
                {
                    photoUri = "/storage/emulated/0/DCIM/Gallery/Compound/" + photoCounter + ".jpg";
                    photoBitmap = BitmapHelper.ShrinkBitmap(photoUri,
                            photoView.getWidth(),
                            photoView.getHeight());

                    photoView.setImageBitmap(photoBitmap);
                    photoCounter++;
                }
            }
        });

        Button loadFacilitiesViewButton = (Button) thisView.findViewById(R.id.load_facilities_view);
        loadFacilitiesViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int photoCounter = 1;
                String photoUri;
                Bitmap photoBitmap;
                for(ImageView photoView : photoViews)
                {
                    photoUri = "/storage/emulated/0/DCIM/Gallery/Facilities/" + photoCounter + ".jpg";
                    photoBitmap = BitmapHelper.ShrinkBitmap(photoUri,
                            photoView.getWidth(),
                            photoView.getHeight());

                    photoView.setImageBitmap(photoBitmap);
                    photoCounter++;
                }
            }
        });


        Button loadFloorPlansViewButton = (Button) thisView.findViewById(R.id.load_floorplans_view);
        loadFloorPlansViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int photoCounter = 1;
                String photoUri;
                Bitmap photoBitmap;
                for(ImageView photoView : photoViews)
                {
                    photoUri = "/storage/emulated/0/DCIM/Gallery/FloorPlans/" + photoCounter + ".jpg";
                    photoBitmap = BitmapHelper.ShrinkBitmap(photoUri,
                            photoView.getWidth(),
                            photoView.getHeight());

                    photoView.setImageBitmap(photoBitmap);
                    photoCounter++;
                }
            }
        });

        return thisView;
    }
}
