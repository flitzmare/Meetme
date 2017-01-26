package riksasuviana.apps.meetme;

/**
 * Created by riksasuviana on 23/01/17.
 */

public class PositionInput {
    private double lat;
    private double lng;

    public PositionInput(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
