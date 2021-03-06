package com.droidmarvin.peripheraliointerfacingrgbledwithapa102led_driver;

import android.app.Activity;
import android.graphics.Color;
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
        setupRGB();

        float[] hsv = {1f, 1f, 1f};
        for (int i = 0; i < mRGBColors.length; i++) { // Assigns gradient colors.
            hsv[0] = i * 360.f / mRGBColors.length;
            mRGBColors[i] = Color.HSVToColor(0, hsv);
        }

        // Light the RGB
        try {
            mRGB.write(mRGBColors);
        } catch (IOException e) {
            Log.e(TAG, "Error setting LED colors", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLedStrip();
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

    private void destroyLedStrip() {
        if (mRGB != null) {
            try {
                mRGB.close();
            } catch (IOException e) {
                Log.e(TAG, "Exception closing LED strip", e);
            } finally {
                mRGB = null;
            }
        }
    }
}
