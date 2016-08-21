package com.github.williams.matt.cutegod.go;

import java.util.Random;

public class TerrainChunkGenerator {
  private static int WIDTH = 16;
  private static int HEIGHT = 16;
  private static String TILES = "Wegsw";

  static String generate() {
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {
        char tile = TILES.charAt(random.nextInt(TILES.length()));
        switch (random.nextInt(4)) {
          case 0:
            if (x > 0) {
              tile = sb.charAt(sb.length() - 1);
            }
            break;
          case 1:
            if (y > 0) {
              tile = sb.charAt(sb.length() - WIDTH - 1);
            }
            break;
        }
        sb.append(tile);
      }
      sb.append('\n');
    }
    return sb.toString();
  }
}
