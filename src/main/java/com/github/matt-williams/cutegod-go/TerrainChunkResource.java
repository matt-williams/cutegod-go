package com.github.williams.matt.cutegod.go;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class TerrainChunkResource {
  private static final Logger log = Logger.getLogger(TerrainChunkResource.class.getName());

  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  float latitude;
  float longitude;

  public TerrainChunkResource(UriInfo uriInfo, Request request, float latitude, float longitude) {
    this.uriInfo = uriInfo;
    this.request = request;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public TerrainChunkView get() {
    long id = MortonCoder.encode(latitude, longitude);
    TerrainChunk terrainChunk = ofy().load().type(TerrainChunk.class).id(id).now();
    if (terrainChunk == null) {
      terrainChunk = new TerrainChunk();
      terrainChunk.mortonLocation = id;
      terrainChunk.latitude = latitude;
      terrainChunk.longitude = longitude;
      terrainChunk.data = TerrainChunkGenerator.generate();
      ofy().save().entity(terrainChunk).now();
    }
    TerrainChunkView terrainChunkView = new TerrainChunkView(latitude, longitude);
    terrainChunkView.setData(new ArrayList<String>(Arrays.asList(terrainChunk.data.split("\n"))));
    return terrainChunkView;
  }

/*
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response put(TerrainChunkView playerView, @Context HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getUserPrincipal() != null) {
      String emailAddress = httpServletRequest.getUserPrincipal().getName();
      TerrainChunk player = ofy().load().type(TerrainChunk.class).id(id).now();
      if ((player != null) &&
          (player.emailAddress.equals(emailAddress))) {
        if (playerView.getDisplayName() != null) {
          player.displayName = playerView.getDisplayName();
        }
        if (!Float.isNaN(playerView.getLatitude())) {
          player.latitude = playerView.getLatitude();
        }
        if (!Float.isNaN(playerView.getLongitude())) {
          player.longitude = playerView.getLongitude();
        }
        ofy().save().entity(player).now();
      }
    }
    return Response.created(uriInfo.getAbsolutePath()).build();
  }
*/
}
