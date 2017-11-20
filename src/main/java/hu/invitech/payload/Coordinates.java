package hu.invitech.payload;

public class Coordinates {

    private double longitud;
    private double latitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitud = longitude;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String toString() {
        return "[" + latitude + ", " + longitud + "]";
    }
}
