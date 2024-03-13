package com.heshustle.game.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.heshustle.game.map.Layer;

/**
 *<p>Class that deals with:</p>
 * <ul>
 * <li>Obtaining maps from tsx files.</li>
 * <li>Rendering them.</li>
 * <li>Checking for collision with both triggers and collidable objects.</li>
 * </ul>
 */
public class Map {
  private Camera camera;
  private final TiledMap map;
  private final MapRenderer mapRenderer;

  private TiledMapTileLayer foreground, background;
  private MapLayer collidablePolygons, triggers;

  /**
   * <p>Constructor for Map.</p>
   *
   * @param camera Camera that the map's being rendered to.
   * @param filePath String that's the relative path (within assets) to the .tmx file.
   */
  public Map(Camera camera, String filePath){
    this.camera = camera;
    map = new TmxMapLoader().load(filePath);
    mapRenderer = new OrthoCachedTiledMapRenderer(map);

    foreground = (TiledMapTileLayer) map.getLayers().get("foreground");
    background = (TiledMapTileLayer) map.getLayers().get("background");

    collidablePolygons = map.getLayers().get("collide");
    collidablePolygons = map.getLayers().get("trigger");
  }

  /**
   * <p>Renders either the foreground or the background of the map.</p>
   *
   * @param layer Layer that gets rendered, either foreground or background.
   * @throws IllegalArgumentException Thrown when layer is not a valid layer.
   */
  public void render(Layer layer) throws IllegalArgumentException{
    String layerString;

    switch (layer){
      case foreground:
        layerString = "foreground";
        break;

      case background:
        layerString = "background";
        break;

      default:
        throw new IllegalArgumentException("Layer must be either foreground or background");
    }

    int[] renderLayers = { 0 };

    renderLayers[0] = map.getLayers().getIndex(layerString);

    mapRenderer.render(renderLayers);
  }
}
