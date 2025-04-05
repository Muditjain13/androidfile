package com.example.files;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.Arrays;

public class ReaderActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        IsoDep isoDep = IsoDep.get(tag);
        if (isoDep != null) {
            try {
                isoDep.connect();
                byte[] responseAPDU = isoDep.transceive(Utils.SELECT_APDU);
                if (responseAPDU[0] == (byte) 0x90 && responseAPDU[1] == (byte) 0x00) {
                    String s = Utils.ByteArrayToHexString(Arrays.copyOfRange(responseAPDU, 2, responseAPDU.length));
                    // Log or use the received response
                }
                responseAPDU = isoDep.transceive(Utils.concatArrays(Utils.BLUETOOTH_REQUEST));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
