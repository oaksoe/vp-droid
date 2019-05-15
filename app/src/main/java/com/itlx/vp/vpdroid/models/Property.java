package com.itlx.vp.vpdroid.models;

import java.io.Serializable;

/**
 * Created by OakSoe on 10/23/2016.
 */
@SuppressWarnings("serial")
public class Property extends Landmark implements Serializable {

    private String description;
    private PropertyType type;
    private String address;
    private String icon;

    public Property()
    {
        description = "";
    }

    public void setDescription(String _description)
    {
        description = _description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setType(PropertyType _type)
    {
        type = _type;
    }

    public PropertyType getType()
    {
        return type;
    }

    public void setAddress(String _address)
    {
        address = _address;
    }

    public String getAddress()
    {
        return address;
    }

    public void setIcon(String _icon)
    {
        icon = _icon;
    }

    public String getIcon()
    {
        return icon;
    }


}

