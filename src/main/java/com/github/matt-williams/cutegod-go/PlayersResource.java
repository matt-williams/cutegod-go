package com.github.williams.matt.cutegod.go;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.kohsuke.randname.RandomNameGenerator;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Path("/players")
public class PlayersResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public PlayerView get(@Context HttpServletRequest httpServletRequest) {
    PlayerView playerView = null;
    if (httpServletRequest.getUserPrincipal() != null) {
      String emailAddress = httpServletRequest.getUserPrincipal().getName();
      Player player = ofy().load().type(Player.class).filter("emailAddress", emailAddress).first().now();
      if (player == null) {
        player = new Player();
        player.id = UUID.randomUUID().toString();
        player.emailAddress = emailAddress;
        player.displayName = new RandomNameGenerator().next();
        ofy().save().entity(player).now();
      }
      playerView = new PlayerView(player.id, player.displayName);
      if (player.latitude != null) {
        playerView.setLatitude(player.latitude);
      }
      if (player.longitude != null) {
        playerView.setLongitude(player.longitude);
      }
    }
    return playerView;
  }

  @PUT
  @Consumes({MediaType.APPLICATION_JSON})
  public Response put(PlayerView playerView, @Context HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getUserPrincipal() != null) {
      String emailAddress = httpServletRequest.getUserPrincipal().getName();
      Player player = ofy().load().type(Player.class).filter("emailAddress", emailAddress).first().now();
      if (player == null) {
        player = new Player();
        player.id = UUID.randomUUID().toString();
        player.emailAddress = emailAddress;
        player.displayName = new RandomNameGenerator().next();
      }
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
    return Response.created(uriInfo.getAbsolutePath()).build();
  }

  @Path("{id}")
  public PlayerResource getPlayer(@PathParam("id") String id) {
    return new PlayerResource(uriInfo, request, id);
  }
}
