package hu.invitech.payload;

import java.util.Map;

public abstract class Payload {

    public static String TEMPERATURE = "temperature";
    public static String FORCED = "forced";
    public static String INVALID = "invalid";

    public abstract Map<String, Object> convertData(String hex, Integer port);

    protected String reverseHex(String originalHex) {
        // TODO: Validation that the length is even
        int lengthInBytes = originalHex.length() / 2;
        char[] chars = new char[lengthInBytes * 2];
        for (int index = 0; index < lengthInBytes; index++) {
            int reversedIndex = lengthInBytes - 1 - index;
            chars[reversedIndex * 2] = originalHex.charAt(index * 2);
            chars[reversedIndex * 2 + 1] = originalHex.charAt(index * 2 + 1);
        }
        return new String(chars);
    }

    protected Float hexToFloat(String hex) {
        String bigEndianHex = reverseHex(hex);
        Long i = Long.parseLong(bigEndianHex, 16);
        return Float.intBitsToFloat(i.intValue());
    }

    protected String hexToAscii(String hex){
        String output = "";
        for (int i = 0; i < hex.length(); i+=2) {
            String str = hex.substring(i, i+2);
            output += (char)Integer.parseInt(str, 16);
        }
        return output;
    }

    protected String hexToBin(String hex){
        StringBuilder bin = new StringBuilder();
        StringBuilder binFragment;
        int iHex;
        hex = hex.trim();
        hex = hex.replaceFirst("0x", "");

        for(int i = 0; i < hex.length(); i++){
            iHex = Integer.parseInt(""+hex.charAt(i),16);
            binFragment = new StringBuilder(Integer.toBinaryString(iHex));

            while(binFragment.length() < 4){
                binFragment.insert(0, "0");
            }
            bin.append(binFragment);
        }
        return bin.toString();
    }

}
