package dali.oversight.data;

/**
 * Created by Mohamed ali on 11/05/2017.
 */

public class Gps {

    private String Latitude;
    private String Longitude;

    public Gps() {
    }

    public Gps(String latitude, String longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}