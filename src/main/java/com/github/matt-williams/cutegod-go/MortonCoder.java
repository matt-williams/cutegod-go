package com.github.williams.matt.cutegod.go;

public class MortonCoder {
  public static long encode(float latitude, float longitude) {
    long longLatitude = (long)((latitude + 90) * 1000000);
    long longLongitude = (long)((longitude + 180) * 1000000);
    long combined = 0;
    for (int i = 0; i < 32; i++) {
      combined |= (longLatitude & (1 << i)) << i | (longLongitude & (1 << i)) << (i + 1);
    }
    return combined;
  }
}
