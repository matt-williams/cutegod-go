package com.github.williams.matt.cutegod.go;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TerrainChunkView {
  private float latitude = Float.NaN;
  private float longitude = Float.NaN;
  private List<String> data;
  @JsonCreator
  public TerrainChunkView(@JsonProperty("latitude") float latitude,
                          @JsonProperty("longitude") float longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
  public float getLatitude() {
    return latitude;
  }
  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }
  public float getLongitude() {
    return longitude;
  }
  public void setLongitude(float longitude) {
    this.longitude = longitude;
  }
  public List<String> getData() {
    return data;
  }
  public void setData(List<String> data) {
    this.data = data;
  }
} 
