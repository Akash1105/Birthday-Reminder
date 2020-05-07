package com.rishi.family.utilities;

import android.graphics.Color;

import java.util.Random;


public class RandomColorGenerator {

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
