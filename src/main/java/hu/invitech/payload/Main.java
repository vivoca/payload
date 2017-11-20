package hu.invitech.payload;

public class Main {

    public static void main(String[] args) {
        String hex2 = "695044b7";
        Payload payload2 = new SagemcomPayload();
        System.out.println(payload2.convertData(hex2, 1));
//        System.out.println("HEX: " + hex2);
//        System.out.println("Forced: " + payload2.getResult().get(SagemcomPayload.FORCED));
//        System.out.println("Temperature: " + payload2.getResult().get(SagemcomPayload.TEMPERATURE));
//        System.out.println("Magnetometry: " + payload2.getResult().get(SagemcomPayload.MAGNETOMETRY));
//        System.out.println("Humidity: " + payload2.getResult().get(SagemcomPayload.HUMIDITY));
//        System.out.println("Pressure: " + payload2.getResult().get(SagemcomPayload.PRESSURE));
//        System.out.println("Charge: " + payload2.getResult().get(SagemcomPayload.CHARGE));
//        System.out.println("Interval: " + payload2.getResult().get(SagemcomPayload.INTERVAL));


//        String hex = "be1d472916800191080075001040";
//        Payload payload = new AdeunisPayload();
//        Map result = payload.convertData(hex, null);
//        System.out.println("HEX: " + hex);
//        System.out.println("Temperature: " + result.get(AdeunisPayload.TEMPERATURE));
//        System.out.println("Coordinates: " + result.get(AdeunisPayload.COORDINATES));
//        System.out.println("DOP: " + result.get(AdeunisPayload.DOP_RANGE));
//        System.out.println("Satellite numbers: " + result.get(AdeunisPayload.SAT_NUMBERS));
//        System.out.println("Uplink Counter: " + result.get(AdeunisPayload.UPLINK_COUNTER));
//        System.out.println("Downlink Counter: " + result.get(AdeunisPayload.DOWNLINK_COUNTER));
//        System.out.println("Battery msbBattery: " + result.get(AdeunisPayload.MSB_BATTERY));
//        System.out.println("Battery lsbBattery: " + result.get(AdeunisPayload.LSB_BATTERY));
//        System.out.println("RSSI: " + result.get(AdeunisPayload.RSSI));
//        System.out.println("SNR: " + result.get(AdeunisPayload.SNR));
//        System.out.println("Button: " + result.get(AdeunisPayload.BTN1_ON));

    }
}
