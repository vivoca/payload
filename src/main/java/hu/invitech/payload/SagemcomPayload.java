package hu.invitech.payload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SagemcomPayload extends Payload {

    public static final String HUMIDITY = "humidity";
    public static final String MAGNETOMETRY = "magnetometry";
    public static final String PRESSURE = "pressure";
    public static final String CHARGE = "charge";
    public static final String TEMP_INTERVAL = "temperatureInterval";
    public static final String HUM_INTERVAL = "humidityInterval";
    public static final String PRES_INTERVAL = "pressureInterval";
    public static final String MAGN_INTERVAL = "magnetometryInterval";
    public static final String TEMP_CHARGE_INTERVAL = "temperatureChargeInterval";
    public static final String HUM_CHARGE_INTERVAL = "humidityChargeInterval";
    public static final String PRES_CHARGE_INTERVAL = "pressureChargeInterval";
    public static final String MAGN_CHARGE_INTERVAL = "magnetometryChargeInterval";


    @Override
    public Map<String, Object> convertData(String hex, Integer port) {

        Map<String, Object> result = new HashMap<>();
        result.put(INVALID, false);
        List<Object> values;

        switch (port) {

            case 1:
                //get temperature periodically
                float temp = hexToFloat(hex);
                result.put(TEMPERATURE, temp);
                result.put(FORCED, false);
                if (temp > 100 || temp < -100) {
                    result.put(INVALID, true);
                }
                break;

            case 2:
                //get magnetometry periodically
                result.put(MAGNETOMETRY, magnetometryFromHex(hex));
                result.put(FORCED, false);
                break;

            case 3:
                //get humidity periodically
                float hum = hexToFloat(hex);
                result.put(HUMIDITY, hum);
                result.put(FORCED, false);
                if (hum < 0 || hum > 100) {
                    result.put(INVALID, true);
                }
                break;

            case 4:
                //get pressure periodically
                float pres = hexToFloat(hex);
                result.put(PRESSURE, pres);
                result.put(FORCED, false);
                if (pres < 99000 || pres > 110000) {
                    result.put(INVALID, true);
                }
                break;

            case 11:
                //get temperature with charge periodically
                values = splitAsciiWithCharge(hex);
                temp = Float.parseFloat((String) values.get(0));
                result.put(TEMPERATURE, temp);
                result.put(CHARGE, values.get(1));
                result.put(FORCED, false);
                if (temp > 100 || temp < -100) {
                    result.put(INVALID, true);
                }
                break;

            case 12:
                //get magnetometry with charge periodically
                values = splitAsciiWithCharge(hex);
                result.put(MAGNETOMETRY, magnetometryFromHex((String) values.get(0)));
                result.put(CHARGE, values.get(1));
                result.put(FORCED, false);
                break;

            case 13:
                //get humidity with charge periodically
                values = splitAsciiWithCharge(hex);
                hum = Float.parseFloat((String) values.get(0));
                result.put(HUMIDITY, hum);
                result.put(CHARGE, values.get(1));
                result.put(FORCED, false);
                if (hum < 0 || hum > 100) {
                    result.put(INVALID, true);
                }
                break;

            case 14:
                //get pressure with charge periodically
                values = splitAsciiWithCharge(hex);
                pres = Float.parseFloat((String) values.get(0));
                result.put(PRESSURE, pres);
                result.put(CHARGE, values.get(1));
                result.put(FORCED, false);
                if (pres < 99000 || pres > 110000) {
                    result.put(INVALID, true);
                }
                break;

            case 100:
                //temperature, auto message
            case 21:
                //get temperature ASAP once
                temp = hexToFloat(hex);
                result.put(TEMPERATURE, temp);
                result.put(FORCED, true);
                if (temp > 100 || temp < -100) {
                    result.put(INVALID, true);
                }
                break;

            case 22:
                //get magnetometry ASAP once
                result.put(MAGNETOMETRY, magnetometryFromHex(hex));
                result.put(FORCED, true);
                break;

            case 23:
                //get humidity ASAP once
                hum = hexToFloat(hex);
                result.put(HUMIDITY, hum);
                result.put(FORCED, true);
                if (hum < 0 || hum > 100) {
                    result.put(INVALID, true);
                }
                break;

            case 24:
                //get pressure ASAP once
                pres = hexToFloat(hex);
                result.put(PRESSURE, pres);
                result.put(FORCED, true);
                if (pres < 99000 || pres > 110000) {
                    result.put(INVALID, true);
                }
                break;

            case 31:
                //get temperature interval ASAP once
                result.put(TEMP_INTERVAL, (int) Long.parseLong(reverseHex(hex), 16));
                result.put(FORCED, true);
                break;

            case 32:
                //get magnetometry interval ASAP once
                result.put(MAGN_INTERVAL, (int) Long.parseLong(reverseHex(hex), 16));
                result.put(FORCED, true);
                break;

            case 33:
                //get humidity interval ASAP once
                result.put(HUM_INTERVAL, (int) Long.parseLong(reverseHex(hex), 16));
                result.put(FORCED, true);
                break;

            case 34:
                //get pressure interval ASAP once
                result.put(PRES_INTERVAL, (int) Long.parseLong(reverseHex(hex), 16));
                result.put(FORCED, true);
                break;

            case 41:
                //get temperature-with-charge interval ASAP once
                result.put(TEMP_CHARGE_INTERVAL, (int) Long.parseLong(reverseHex(hex), 16));
                result.put(FORCED, true);
                break;

            case 42:
                //get magnetometry-with-charge interval ASAP once
                result.put(MAGN_CHARGE_INTERVAL, (int) Long.parseLong(reverseHex(hex), 16));
                result.put(FORCED, true);
                break;

            case 43:
                //get humidity-with-charge interval ASAP once
                result.put(HUM_CHARGE_INTERVAL, (int) Long.parseLong(reverseHex(hex), 16));
                result.put(FORCED, true);
                break;

            case 44:
                // get pressure-with-charge interval ASAP once
                result.put(PRES_CHARGE_INTERVAL, (int) Long.parseLong(reverseHex(hex), 16));
                result.put(FORCED, true);
                break;
        }
        return result;
    }

    private List<Object> splitAsciiWithCharge (String hex) {
        List<Object> values = new ArrayList<>();
        String asciiFromHex = hexToAscii(hex);
        String[] parts = asciiFromHex.split(" ");
        values.add(parts[0]);
        values.add(Integer.parseInt(parts[1]));
        return values;
    }

    private Magnetometry magnetometryFromHex (String hex) {
        int x = (int) Long.parseLong(reverseHex(hex.substring(0,8)), 16);
        int y = (int) Long.parseLong(reverseHex(hex.substring(8,16)), 16);
        int z = (int) Long.parseLong(reverseHex(hex.substring(16,24)), 16);
        return new Magnetometry(x, y, z);
    }

}
