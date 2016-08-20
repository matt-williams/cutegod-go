package com.github.williams.matt.cutegod.go;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerView {
  private String id;
  private String displayName;
  private float latitude = Float.NaN;
  private float longitude = Float.NaN;
  @JsonCreator
  public PlayerView(@JsonProperty("id") String id,
                    @JsonProperty("displayName") String displayName) {
    this.id = id;
    this.displayName = displayName;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getDisplayName() {
    return displayName;
  }
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
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
} 
