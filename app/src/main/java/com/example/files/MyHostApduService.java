package com.example.files;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MyHostApduService extends HostApduService {

    private static final String TAG = "HCEApp";

    // AID SELECT command expected from reader
    private static final byte[] SELECT_APDU = {
            (byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00,
            (byte) 0x07, (byte) 0xA1, (byte) 0xA2, (byte) 0xA3,
            (byte) 0xA4, (byte) 0xA5, (byte) 0xB6, (byte) 0xB7
    };

    // Status codes
    private static final byte[] STATUS_SUCCESS = {(byte) 0x90, (byte) 0x00};
    private static final byte[] STATUS_FAILED = {(byte) 0x6F, (byte) 0x00};

    // Message sent to the reader
    private static String dynamicMessage = "Default message";

    public static void setMessage(String message) {
        dynamicMessage = message;
    }

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        Log.d(TAG, "APDU received: " + bytesToHex(apdu));

        // Handle SELECT AID
        if (Arrays.equals(apdu, SELECT_APDU)) {
            Log.d(TAG, "SELECT AID matched. Returning STATUS_SUCCESS.");
            return STATUS_SUCCESS;
        }

        // Respond with the trimmed message + status
        byte[] messageBytes = dynamicMessage.trim().getBytes(StandardCharsets.UTF_8); // ✅ FIXED
        byte[] response = concatenate(messageBytes, STATUS_SUCCESS);
        Log.d(TAG, "Sending message: " + dynamicMessage);
        return response;
    }


    @Override
    public void onDeactivated(int reason) {
        Log.d(TAG, "NFC connection deactivated. Reason: " + reason);
    }

    // Combine two byte arrays
    private byte[] concatenate(byte[] first, byte[] second) {
        byte[] result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    // Convert bytes to hex string for logging
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
