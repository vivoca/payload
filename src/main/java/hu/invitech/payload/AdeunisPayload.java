package hu.invitech.payload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdeunisPayload extends Payload {


    public static String TEMP_ON = "temperatureOn";
    public static String ACCELERO_ON = "acceleroOn";
    public static String BTN1_ON = "btn1On";
    public static String GPS_ON = "gpsOn";
    public static String MACDOWN_ON = "macDownOn";
    public static String MACUP_ON = "macUpOn";
    public static String BATTERY_ON = "batteryOn";
    public static String RSSI_SNR_ON = "rssiSnrOn";
    public static String COORDINATES = "coordinates";
    public static String DOP_RANGE = "dopRange";
    public static String SAT_NUMBERS = "satNumbers";
    public static String DOWNLINK_COUNTER = "downlinkCounter";
    public static String UPLINK_COUNTER = "uplinkCounter";
    public static String MSB_BATTERY = "msbBattery";
    public static String LSB_BATTERY = "lsbBattery";
    public static String RSSI = "rssi";
    public static String SNR = "snr";



    @Override
    public Map<String, Object> convertData(String hex, Integer port) {
        Map<String, Object> result = new HashMap<String, Object>();

        StringBuilder binary = new StringBuilder(hexToBin(hex));
        if (binary.length()<136) {
            while (binary.length()<136) {
                binary.append("0");
            }
        }

        // byte1 info
        String first = binary.substring(0,8);
        List<String> descr = new ArrayList<String>();
        descr.add(TEMP_ON);
        descr.add(ACCELERO_ON);
        descr.add(BTN1_ON);
        descr.add(GPS_ON);
        descr.add(MACDOWN_ON);
        descr.add(MACUP_ON);
        descr.add(BATTERY_ON);
        descr.add(RSSI_SNR_ON);
        boolean isOn;

        for (int i=0; i<8; i++) {
            isOn = Integer.parseInt(String.valueOf(first.charAt(i))) != 0;
            result.put(descr.get(i), isOn);
        }

        // byte2 Temperature
        if ((Boolean)result.get(TEMP_ON)) {
            result.put(TEMPERATURE, Integer.parseInt(binary.substring(8,16),2));
        }

        if ((Boolean)result.get(GPS_ON)) {
            // byte3-6 Latitude
            int latDeg = (Integer.parseInt(binary.substring(16, 20), 2) * 10) + Integer.parseInt(binary.substring(20, 24), 2);
            int latMin = (Integer.parseInt(binary.substring(24, 28), 2) * 10) + Integer.parseInt(binary.substring(28, 32), 2);
            int latSec = (Integer.parseInt(binary.substring(32, 36), 2) * 100) + Integer.parseInt(binary.substring(36, 40), 2) * 10 + Integer.parseInt(binary.substring(40, 44), 2);
            int hemNS = Integer.parseInt(String.valueOf(binary.charAt(47)));

            //byte7-10 Longitude
            int longDeg = (Integer.parseInt(binary.substring(48, 52), 2) * 100) + Integer.parseInt(binary.substring(52, 56), 2) * 10 + Integer.parseInt(binary.substring(56, 60), 2);
            int longMin = (Integer.parseInt(binary.substring(60, 64), 2) * 10) + Integer.parseInt(binary.substring(64, 68), 2);
            int longSec = (Integer.parseInt(binary.substring(68, 72), 2) * 10) + Integer.parseInt(binary.substring(72, 76), 2);
            int hemEW = Integer.parseInt(String.valueOf(binary.charAt(79)));
            double latitude = convertCoordinate(latDeg, latMin, latSec, hemNS);
            double longitude = convertCoordinate(longDeg, longMin, longSec, hemEW);
            result.put(COORDINATES, new Coordinates(latitude, longitude));

            //byte11 DOP range
            result.put(DOP_RANGE, Integer.parseInt(binary.substring(80,84),2));

            //byte11 Satellites
            result.put(SAT_NUMBERS, Integer.parseInt(binary.substring(84,88),2));
        } else {
            //byte11 DOP range
            result.put(DOP_RANGE, Integer.parseInt(binary.substring(16, 20), 2));

            //byte11 Satellites
            result.put(SAT_NUMBERS, Integer.parseInt(binary.substring(20, 24), 2));
        }

        //byte12 Uplink counter
        if ((Boolean)result.get(MACUP_ON)) {
            if ((Boolean)result.get(GPS_ON)) result.put(UPLINK_COUNTER, Integer.parseInt(binary.substring(88, 96), 2));
            else result.put(UPLINK_COUNTER, Integer.parseInt(binary.substring(24, 32), 2));
        }

        //byte13 Downlink counter
        if ((Boolean)result.get(MACDOWN_ON)) {
            if ((Boolean)result.get(GPS_ON)) result.put(DOWNLINK_COUNTER, Integer.parseInt(binary.substring(96, 104), 2));
            else result.put(DOWNLINK_COUNTER, Integer.parseInt(binary.substring(32, 40), 2));
        }

        //byte14 MSB Battery
        if ((Boolean)result.get(BATTERY_ON)) {
            if ((Boolean)result.get(GPS_ON)) result.put(MSB_BATTERY, Integer.parseInt(binary.substring(104, 112), 2));
            else result.put(MSB_BATTERY, Integer.parseInt(binary.substring(40, 48), 2));
        }

        //byte15 LSB Battery
        if ((Boolean)result.get(BATTERY_ON)) {
            if ((Boolean)result.get(GPS_ON)) result.put(LSB_BATTERY, Integer.parseInt(binary.substring(112, 120), 2));
            else result.put(LSB_BATTERY, Integer.parseInt(binary.substring(48, 56), 2));
        }

        //byte16 RSSI
        if ((Boolean)result.get(RSSI_SNR_ON)) {
            if ((Boolean)result.get(GPS_ON)) result.put(RSSI, Integer.parseInt(binary.substring(120, 128), 2));
            else result.put(RSSI, Integer.parseInt(binary.substring(56, 64), 2));
        }

        //byte17 SNR
        if ((Boolean)result.get(RSSI_SNR_ON)) {
            if ((Boolean)result.get(GPS_ON)) result.put(SNR, Integer.parseInt(binary.substring(128, 136), 2));
            else result.put(SNR, Integer.parseInt(binary.substring(64, 72), 2));
        }

        return result;
    }

    private double convertCoordinate(int deg, int min, int sec, int hem) {
        double decDeg = deg + (min/60.0) + (sec/3600.0);
        return (hem == 0) ? decDeg : decDeg*(-1);
    }

}
