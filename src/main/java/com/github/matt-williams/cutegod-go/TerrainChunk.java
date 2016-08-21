package com.github.williams.matt.cutegod.go;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;
import com.googlecode.objectify.condition.IfNotNull;

@Entity
public class TerrainChunk {
  @Id Long mortonLocation;
  Float latitude;
  Float longitude;
  String data;

  @OnSave void updateMortonLocation() {
    if ((latitude != null) &&
        (longitude != null)) {
      mortonLocation = MortonCoder.encode(latitude, longitude);
    } else {
      mortonLocation = null;
    }
  }
}
