package com.itlx.vp.vpdroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.itlx.vp.vpdroid.adapters.PropertyMenuAdapter;
import com.itlx.vp.vpdroid.fragments.BuildingFragment;
import com.itlx.vp.vpdroid.fragments.GalleryFragment;
import com.itlx.vp.vpdroid.fragments.PanoramaFragment;
import com.itlx.vp.vpdroid.models.Property;
import com.itlx.vp.vpdroid.models.SidemenuItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by OakSoe on 10/25/2016.
 */

public class PropertyActivity extends AppCompatActivity implements View.OnClickListener {

    private Property thisProperty;
    private DrawerLayout propertyLayout;
    private ListView propertySideMenu;

    private ActionBarDrawerToggle sidemenuToggle;
    private CharSequence sidemenuTitle;
    private CharSequence title;

    PropertyMenuAdapter menuAdapter;
    List<SidemenuItem> menuDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        //Intent intent = getIntent();
        //thisProperty = (Property)intent.getSerializableExtra("Property");

//        Button backButton = (Button) findViewById(R.id.back_to_main);
//        backButton.setOnClickListener(this);

        propertyLayout = (DrawerLayout) findViewById(R.id.property_layout);
        propertySideMenu = (ListView) findViewById(R.id.property_sidemenu);

        menuDataList= new ArrayList<SidemenuItem>();
        menuDataList.add(new SidemenuItem("3D Building", R.drawable.bubble));
        menuDataList.add(new SidemenuItem("Panorama", R.drawable.bubble));
        menuDataList.add(new SidemenuItem("Gallery", R.drawable.bubble));

        menuAdapter = new PropertyMenuAdapter(this, R.layout.property_menu_item, menuDataList);

        propertySideMenu.setAdapter(menuAdapter);

        propertySideMenu.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sidemenuToggle = new ActionBarDrawerToggle(this, propertyLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(sidemenuTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        propertyLayout.setDrawerListener(sidemenuToggle);

        if (savedInstanceState == null) {
            SelectItem(0);
        }
    }

    @Override
    public void onClick(View v) {

//        switch (v.getId()) {
//            case R.id.back_to_main:
//                finish();
//                break;
//        }
    }

    public void SelectItem(int pos) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (pos) {
            case 0:
                fragment = new BuildingFragment();
//                args.putString(BuildingFragment.ITEM_NAME, dataList.get(possition)
//                        .getItemName());
//                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
//                        .getImgResID());
                break;
            case 1:
                fragment = new PanoramaFragment();
                break;
            case 2:
                fragment = new GalleryFragment();
                break;
            default:
                fragment = new GalleryFragment();
                break;
        }

        //fragment.setArguments(args);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.property_content, fragment)
                .commit();

        propertySideMenu.setItemChecked(pos, true);
        setTitle(menuDataList.get(pos).getItemName());
        propertyLayout.closeDrawer(propertySideMenu);

    }

    @Override
    public void setTitle(CharSequence _title) {
        title = _title;
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        sidemenuToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (sidemenuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        sidemenuToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);

        }
    }
}

