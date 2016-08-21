package com.github.williams.matt.cutegod.go;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/terrain")
public class TerrainResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;

  @Path("{latitude},{longitude}")
  public TerrainChunkResource getTerrainChunk(@PathParam("latitude") String latitude,
                                              @PathParam("longitude") String longitude) {
    return new TerrainChunkResource(uriInfo, request, Float.valueOf(latitude), Float.valueOf(longitude));
  }
}
