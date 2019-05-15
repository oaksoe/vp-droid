package com.itlx.vp.vpdroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.view.Gravity;
import android .view.LayoutInflater;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.itlx.vp.vpdroid.helpers.BitmapHelper;
import com.itlx.vp.vpdroid.models.Landmark;
import com.itlx.vp.vpdroid.models.Property;
import com.itlx.vp.vpdroid.models.PropertyType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        //GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        //GoogleMap.OnInfoWindowLongClickListener,
        OnMapReadyCallback {

    public enum MapLevel {
        City,
        Township,
        Property
    };

    private LayoutInflater inflater = null;

    private GoogleMap map;
    private LatLng mainCityMarkerPosition;
    private Marker mainCityMarker;
    private HashMap<String, LatLng> townshipMarkerPositions;
    private ArrayList<Marker> townshipMarkers;
    private ArrayList<Marker> propertyMarkers;
    private HashMap<String, Landmark> landmarks;
    private MapLevel currentLevel;

    private static final int RESULT_LOAD_IMAGE = 1;

    private View addPropertyPopupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            currentLevel = MapLevel.City;
            mainCityMarkerPosition = new LatLng(16.8062456,96.1253983);

            townshipMarkers = new ArrayList<Marker>();
            propertyMarkers = new ArrayList<Marker>();
            landmarks = new HashMap<String, Landmark>();

            townshipMarkerPositions = new HashMap<String, LatLng>();
            townshipMarkerPositions.put(getString(R.string.bahan), new LatLng(16.811239,96.1485431));
            townshipMarkerPositions.put(getString(R.string.sanchaung), new LatLng(16.8062456,96.1253983));
            townshipMarkerPositions.put(getString(R.string.kamaryut), new LatLng(16.8232727,96.1220508));
            townshipMarkerPositions.put(getString(R.string.yankin), new LatLng(16.8374397,96.1576062));
            townshipMarkerPositions.put(getString(R.string.tamwe), new LatLng(16.809411,96.168158));
            townshipMarkerPositions.put(getString(R.string.kyauktada), new LatLng(16.7742041,96.1569464));
            townshipMarkerPositions.put(getString(R.string.mayangone), new LatLng(16.8658965,96.1020625));
            townshipMarkerPositions.put(getString(R.string.hlaing), new LatLng(16.846139,96.1118584));

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

            GoogleMapOptions options = new GoogleMapOptions();
            options.mapType(GoogleMap.MAP_TYPE_HYBRID)
                    .compassEnabled(false)
                    .rotateGesturesEnabled(false)
                    .tiltGesturesEnabled(false);

            mapFragment.newInstance(options);
            mapFragment.getMapAsync(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Google Map Override Functions
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
        //map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        //map.setOnInfoWindowLongClickListener(this);

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){
            @Override
            public View getInfoWindow(Marker marker) {
                return(null);
            }

            @Override
            public View getInfoContents(Marker marker) {
                Property property = (Property) landmarks.get(marker.getId());

                View popup;
                if(property == null)
                {
                    popup = getLayoutInflater().inflate(R.layout.township_info_popup, null);

                    TextView titleTextView = (TextView) popup.findViewById(R.id.township_title);
                    titleTextView.setText(marker.getTitle());
                }
                else
                {
                    popup = getLayoutInflater().inflate(R.layout.property_info_popup, null);

                    TextView titleTextView = (TextView) popup.findViewById(R.id.info_property_title);
                    titleTextView.setText(property.getName());

                    TextView typeTextView = (TextView) popup.findViewById(R.id.info_property_type);
                    typeTextView.setText(property.getType().toString());

//                    ImageView iconImageView = (ImageView) popup.findViewById(R.id.info_property_icon);
//                    Bitmap iconBitmap = BitmapHelper.ShrinkBitmap(property.getIcon(),
//                            100,
//                            100);
//                    iconImageView.setImageBitmap(iconBitmap);
                }

                return (popup);
            }
        });

        mainCityMarker = map.addMarker(new MarkerOptions().position(mainCityMarkerPosition).title(getString(R.string.main_city)));
        mainCityMarker.showInfoWindow();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mainCityMarkerPosition, 10));
        addTownshipMarkers();
    }

    @Override
    public void onMapLongClick(LatLng point)
    {
        if(currentLevel == MapLevel.Property)
        {
            final LatLng propertyPos = point;
            addPropertyPopupView = inflater.inflate(R.layout.add_property_popup, null, false);

            final PopupWindow propertyPopup = new PopupWindow(addPropertyPopupView,700,1000,true);
            propertyPopup.showAtLocation(findViewById(R.id.root_layout), Gravity.CENTER, 0,0);

            final Spinner propertyTypeSpinner = (Spinner) addPropertyPopupView.findViewById(R.id.property_type);
            propertyTypeSpinner.setAdapter(new ArrayAdapter<PropertyType>(MainActivity.this, android.R.layout.simple_spinner_item, PropertyType.values()));

            Button loadPropertyIconButton = (Button) addPropertyPopupView.findViewById(R.id.load_property_icon);
            loadPropertyIconButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
                }
            });

            Button addPropertyButton = (Button) addPropertyPopupView.findViewById(R.id.add_property);
            final EditText propertyNameEditText = (EditText) addPropertyPopupView.findViewById(R.id.property_name);
            final EditText propertyDescriptionEditText = (EditText) addPropertyPopupView.findViewById(R.id.property_description);
            final EditText propertyAddressText = (EditText) addPropertyPopupView.findViewById(R.id.property_address);
            final ImageView propertyIcon = (ImageView) addPropertyPopupView.findViewById(R.id.property_icon);

            addPropertyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String propertyName = propertyNameEditText.getText().toString();
                    String propertyDescription = propertyDescriptionEditText.getText().toString();
                    String propertyAddress = propertyAddressText.getText().toString();
                    String propertyType = propertyTypeSpinner.getSelectedItem().toString();
                    String propertyIconPath = propertyIcon.getTag().toString();
                    Marker propertyMarker = map.addMarker(new MarkerOptions().position(propertyPos).title(propertyName));

                    Property property = new Property();
                    property.setName(propertyName);
                    property.setDescription(propertyDescription);
                    property.setAddress(propertyAddress);
                    property.setType(PropertyType.valueOf(propertyType));
                    property.setIcon(propertyIconPath);
                    landmarks.put(propertyMarker.getId(), property);

                    propertyMarker.showInfoWindow();
                    propertyMarkers.add(propertyMarker);

                    propertyPopup.dismiss();

                    Bitmap iconBitmap = BitmapHelper.ShrinkBitmap(propertyIconPath, 100, 100);

                    GroundOverlay mGroundOverlayRotated = map.addGroundOverlay(new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromBitmap(iconBitmap)).anchor(0, 1)
                            .position(propertyPos, 430f, 302f)
                            .bearing(30)
                            .clickable(true));
                }
            });
        }
    }

    @Override
    public void onMapClick(LatLng point)
    {


    }

//    @Override
//    public boolean onMarkerClick(Marker marker)
//    {
//        if(marker.getTitle().equals(getString(R.string.main_city)))
//        {
//            showTownshipMarkers();
//            map.moveCamera(CameraUpdateFactory.newLatLng(mainCityMarkerPosition));
//            map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
//        }
//        else
//        {
//            marker.showInfoWindow();
//        }
//
//        return true;
//    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
//        Intent propertyScreenIntent = new Intent(this, PropertyActivity.class);
//        //propertyScreenIntent.putExtra("Property", property);
//        startActivity(propertyScreenIntent);

        if(marker.getTitle().equals(getString(R.string.main_city)))
        {
            currentLevel = MapLevel.Township;
            showTownshipMarkers();
            mainCityMarker.setVisible(false);
            map.moveCamera(CameraUpdateFactory.newLatLng(mainCityMarkerPosition));
            map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
        }
        else
        {
            Property property = (Property) landmarks.get(marker.getId());
            if(property == null)
            {
                currentLevel = MapLevel.Property;
                hideTownshipMarkers();
                map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
            else
            {
                //Go to PropertyActivity
                Intent propertyScreenIntent = new Intent(this, PropertyActivity.class);
                propertyScreenIntent.putExtra("Property", property);
                startActivity(propertyScreenIntent);
            }
        }
    }

//    @Override
//    public void onInfoWindowLongClick(Marker marker)
//    {
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImageUri = data.getData();
            ImageView propertyIconImageView = (ImageView) addPropertyPopupView.findViewById(R.id.property_icon);

            Bitmap iconBitmap = BitmapHelper.ShrinkBitmap(selectedImageUri.getEncodedPath(),
                    propertyIconImageView.getWidth(),
                    propertyIconImageView.getHeight());
            propertyIconImageView.setImageBitmap(iconBitmap);
            propertyIconImageView.setTag(selectedImageUri.getEncodedPath());
        }
    }


    //Utitlity Functions
    public void addTownshipMarkers()
    {
        for(Map.Entry<String, LatLng> markerPos: townshipMarkerPositions.entrySet()){
            LatLng pos = (LatLng) markerPos.getValue();
            String title = (String) markerPos.getKey();

            Marker marker = map.addMarker(new MarkerOptions().position(pos).title(title));
            marker.setVisible(false);

            townshipMarkers.add(marker);
        }
    }

    public void showTownshipMarkers()
    {
        Iterator<Marker> iterator = townshipMarkers.iterator();
        while (iterator.hasNext()) {
            Marker marker = iterator.next();
            marker.setVisible(true);

            if(marker.getTitle().equals(getString(R.string.bahan)))
                marker.showInfoWindow();
        }
    }

    public void hideTownshipMarkers()
    {
        Iterator<Marker> iterator = townshipMarkers.iterator();
        while (iterator.hasNext()) {
            Marker marker = iterator.next();
            marker.setVisible(false);
        }
    }
}
