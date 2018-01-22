package com.droidmarvin.peripheraliointerfacingrgbledwithapa102led_driver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.contrib.driver.apa102.Apa102;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    // LED configuration.
    private static final int NUM_LEDS = 7;
    private static final int LED_BRIGHTNESS = 5; // 0 ... 31
    private static final Apa102.Mode LED_MODE = Apa102.Mode.BGR;
    private static final String SPI_BUS = "SPI3.1";

    private Apa102 mRGB;
    private int[] mRGBColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupRGB() {
        mRGBColors = new int[NUM_LEDS];
        try {
            Log.d(TAG, "Initializing LED strip");
            mRGB = new Apa102(SPI_BUS, LED_MODE);
            mRGB.setBrightness(LED_BRIGHTNESS);
        } catch (IOException e) {
            Log.e(TAG, "Error initializing LED strip", e);
        }
    }
}
