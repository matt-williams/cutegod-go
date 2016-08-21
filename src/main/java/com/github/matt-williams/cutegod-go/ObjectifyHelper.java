package com.github.williams.matt.cutegod.go;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

public class ObjectifyHelper implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event) {
    ObjectifyService.register(Player.class);
    ObjectifyService.register(TerrainChunk.class);
  }

  public void contextDestroyed(ServletContextEvent event) {
  }
}
