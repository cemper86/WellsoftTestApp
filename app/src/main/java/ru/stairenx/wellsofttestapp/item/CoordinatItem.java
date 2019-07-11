package ru.stairenx.wellsofttestapp.item;

public class CoordinatItem {

    double lng;
    double lat;

    public CoordinatItem(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }
}
