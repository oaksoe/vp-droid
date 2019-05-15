package com.itlx.vp.vpdroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itlx.vp.vpdroid.R;
import com.itlx.vp.vpdroid.models.SidemenuItem;

import java.util.List;

/**
 * Created by OakSoe on 10/29/2016.
 */

public class PropertyMenuAdapter extends ArrayAdapter<SidemenuItem> {

    Context context;
    List<SidemenuItem> drawerItemList;
    int layoutResID;

    public PropertyMenuAdapter(Context context, int layoutResourceID,
                               List<SidemenuItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        SidemenuItemHolder sidemenuItemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            sidemenuItemHolder = new SidemenuItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            sidemenuItemHolder.ItemName = (TextView) view
                    .findViewById(R.id.drawer_itemName);
            sidemenuItemHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            view.setTag(sidemenuItemHolder);

        } else {
            sidemenuItemHolder = (SidemenuItemHolder) view.getTag();

        }

        SidemenuItem dItem = (SidemenuItem) this.drawerItemList.get(position);

        sidemenuItemHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getImgResID()));
        sidemenuItemHolder.ItemName.setText(dItem.getItemName());

        return view;
    }

    private static class SidemenuItemHolder {
        TextView ItemName;
        ImageView icon;
    }
}
