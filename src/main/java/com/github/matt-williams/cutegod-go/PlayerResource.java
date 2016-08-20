package com.github.williams.matt.cutegod.go;

import java.util.ArrayList;
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

public class PlayerResource {
  private static final Logger log = Logger.getLogger(PlayerResource.class.getName());

  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String id;

  public PlayerResource(UriInfo uriInfo, Request request, String id) {
    this.uriInfo = uriInfo;
    this.request = request;
    this.id = id;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public PlayerView get() {
    Player player = ofy().load().type(Player.class).id(this.id).now();
    PlayerView playerView = null;
    if (player != null) {
      playerView = new PlayerView(this.id, player.displayName);
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
  @Consumes(MediaType.APPLICATION_JSON)
  public Response put(PlayerView playerView, @Context HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getUserPrincipal() != null) {
      String emailAddress = httpServletRequest.getUserPrincipal().getName();
      Player player = ofy().load().type(Player.class).id(this.id).now();
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

  @GET
  @Path("nearby")
  @Produces(MediaType.APPLICATION_JSON)
  public List<PlayerView> getNearby() {
    List<PlayerView> playerViews = new ArrayList<PlayerView>();
    Player player = ofy().load().type(Player.class).id(this.id).now();
    if (player != null) {
      if ((player.latitude != null) &&
          (player.longitude != null)) {
        // TODO: Fix wrap
        long southEast = MortonCoder.encode(player.latitude + 0.1f, player.longitude - 0.1f);
        long southWest = MortonCoder.encode(player.latitude + 0.1f, player.longitude + 0.1f);
        long northEast = MortonCoder.encode(player.latitude - 0.1f, player.longitude - 0.1f);
        long northWest = MortonCoder.encode(player.latitude - 0.1f, player.longitude + 0.1f);
        // TODO: Do this properly - should divide into 4 segments and search in each separately
        List<Player> players = ofy().load().type(Player.class).filter("mortonLocation >", northEast).filter("mortonLocation <", southWest).list();
        for (Player otherPlayer : players) {
          PlayerView playerView = new PlayerView(this.id, otherPlayer.displayName);
          playerView.setLatitude(otherPlayer.latitude);
          playerView.setLongitude(otherPlayer.longitude);
          playerViews.add(playerView);
        }
      }
    }
    return playerViews;
  }
}
