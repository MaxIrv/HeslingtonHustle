package com.heshustle.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 *<p>Class that deals with:</p>
 * <ul>
 * <li>Obtaining maps from tsx files.</li>
 * <li>Rendering them.</li>
 * <li>Checking for collision with both triggers and collidable objects.</li>
 * </ul>
 */
public class Map {
  private OrthographicCamera camera;
  private final TiledMap map;
  private final OrthoCachedTiledMapRenderer mapRenderer;
  public final Vector2 startPosition = new Vector2();

  private TiledMapTileLayer foreground, background, waterLayer;
  private MapLayer collidablePolygons, triggers;

  /**
   * <p>Constructor for Map.</p>
   *
   * @param camera Camera that the map's being rendered to.
   * @param filePath String that's the relative path (within assets) to the .tmx file.
   */
  public Map(OrthographicCamera camera, String filePath){
    this.camera = camera;
    map = new TmxMapLoader().load(filePath);
    mapRenderer = new OrthoCachedTiledMapRenderer (map);
    mapRenderer.setBlending(true);

    foreground = (TiledMapTileLayer) map.getLayers().get("foreground");
    background = (TiledMapTileLayer) map.getLayers().get("background");
    waterLayer = (TiledMapTileLayer) map.getLayers().get("waterLayer");

    collidablePolygons = map.getLayers().get("collide");
    triggers = map.getLayers().get("trigger");

    // Get Objective positions
    MapLayer layer = map.getLayers().get("Objectives");
    if (layer != null) {
      for (MapObject object : layer.getObjects()) {
        if ("Start".equals(object.getName())) {
          if (object instanceof RectangleMapObject) {
            RectangleMapObject rectObject = (RectangleMapObject) object;
            startPosition.x = rectObject.getRectangle().x;
            startPosition.y = rectObject.getRectangle().y;
          }
          break;
        }
      }
    }
  }

  /**
   * <p>Renders either the foreground or the background of the map.</p>
   *
   * @param layer Layer that gets rendered, either foreground or background.
   * @throws IllegalArgumentException Thrown when layer is not a valid layer.
   */
  public void render(Layer layer) throws IllegalArgumentException {
    String layerString;
    int layerIndex;

    switch (layer){
      case foreground:
        layerString = "foreground";
        break;

      case background:
        layerString = "background";
        break;

      case waterLayer:
        layerString = "waterLayer";
        break;

      default:
        throw new IllegalArgumentException("Layer must be either foreground or background");
    }

    // Check if the layer exists
    layerIndex = map.getLayers().getIndex(layerString);
    if (layerIndex == -1) {
      throw new IllegalArgumentException("Layer " + layerString + " not found in the map.");
    }


    int[] renderLayers = { layerIndex };
    mapRenderer.setView(camera);
    mapRenderer.render(renderLayers);


  }

  public void updateCamera(OrthographicCamera camera) {
    this.camera = camera;
    mapRenderer.setView(camera);
  }
}