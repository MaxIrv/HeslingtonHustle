package com.heshustle.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.heshustle.interaction.Interaction;
import com.heshustle.interaction.Interaction.Type;

/**
 *<p>Class that deals with:</p>
 * <ul>
 * <li>Obtaining maps from tsx files.</li>
 * <li>Rendering them.</li>
 * <li>Checking for collision with both triggers and collidable objects.</li>
 * </ul>
 */
public class GameMap {
  private OrthographicCamera camera;
  private final TiledMap map;
  private final OrthoCachedTiledMapRenderer mapRenderer;
  public final Vector2 startPosition = new Vector2();

  private TiledMapTileLayer foreground, background, waterLayer;
  private MapLayer collidablePolygons, triggers;

  // Map dimensions
  public final int mapWidth, mapHeight;
  public final float mapPixelWidth, mapPixelHeight;
  public final Array<Rectangle> collidableTiles = new Array<>();
  public final Array<Rectangle> waterTiles = new Array<>();
  public final Array<Rectangle> collidableWaterTiles = new Array<>();
  public final Array<Interaction> interactions = new Array<>();

  /**
   * <p>Constructor for Map.</p>
   *
   * @param camera Camera that the map's being rendered to.
   * @param filePath String that's the relative path (within assets) to the .tmx file.
   */
  public GameMap(OrthographicCamera camera, String filePath) throws ClassNotFoundException {
    this.camera = camera;
    map = new TmxMapLoader().load(filePath);
    mapRenderer = new OrthoCachedTiledMapRenderer (map);
    mapRenderer.setBlending(true);

    int tileWidth = (int)map.getProperties().get("tilewidth");
    int tileHeight = (int)map.getProperties().get("tileheight");
    this.mapWidth = (int)map.getProperties().get("width");
    this.mapHeight = (int)map.getProperties().get("height");

    this.mapPixelWidth = mapWidth * tileWidth;
    this.mapPixelHeight = mapHeight * tileHeight;

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
        } else if (object instanceof RectangleMapObject) {
          RectangleMapObject rectObject = (RectangleMapObject) object;
          Rectangle rect = rectObject.getRectangle();
          String objectiveType = (String) object.getProperties().get("objectiveType");
          Interaction.Type newType;
          switch (objectiveType) {
            case "Study":
              newType = Type.STUDY;
              break;
            case "Sleep":
              newType = Type.SLEEP;
              break;
            case "Hunger":
              newType = Type.EAT;
              break;
            case "Recreation":
              newType = Type.RECREATION;
              break;
            default:
              throw new ClassNotFoundException("Invalid objectiveType found in object");
          }

          String name = object.getName();
          int timeLength = object.getProperties().get("timeLength", Integer.class);

          Interaction newInteraction = new Interaction(rectObject, newType, name, timeLength);
          interactions.add(newInteraction);
        }
      }
    }

    // Get all 'blocked' tiles which should act as collisions
    for (int y = 0; y < foreground.getHeight(); y++) {
      for (int x = 0; x < foreground.getWidth(); x++) {
        TiledMapTileLayer.Cell cell = foreground.getCell(x, y);
        if (cell != null) {
          TiledMapTile tile = cell.getTile();
          if (tile != null && tile.getProperties().containsKey("blocked") && tile.getProperties()
              .get("blocked", Boolean.class)) {
            // This tile is collidable, add its rectangle to the list
            Rectangle rect = new Rectangle(x * foreground.getTileWidth(),
                y * foreground.getTileHeight(), foreground.getTileWidth(),
                foreground.getTileHeight());
            collidableTiles.add(rect);
          }
        }
      }
    }
    // TODO: This is problematic as water tiles are often not the whole tile, so how we handle this becomes very awkward.
    // Get all water tiles
//    for (int y = 0; y < waterLayer.getHeight(); y++) {
//      for (int x = 0; x < waterLayer.getWidth(); x++) {
//        TiledMapTileLayer.Cell cell = waterLayer.getCell(x, y);
//        if (cell != null) {
//          Rectangle rect = new Rectangle(x * waterLayer.getTileWidth(), y * waterLayer.getTileHeight(), waterLayer.getTileWidth(), waterLayer.getTileHeight());
//          waterTiles.add(rect);
//        }
//      }
//    }
//    // Populate tiles that are not covered by another layer (therefore collidiable)
//    for (Rectangle waterTile : waterTiles) {
//      boolean isCovered = false;
//      // Check higher layers, assuming foregroundLayer is the next layer up
//      TiledMapTileLayer.Cell cell = foreground.getCell((int)(waterTile.x / foreground.getTileWidth()), (int)(waterTile.y / foreground.getTileHeight()));
//      if (cell != null) {
//        isCovered = true;  // Found a tile in the foreground layer that covers this water tile
//      }
//
//      if (!isCovered) {
//        collidableTiles.add(waterTile);  // This water tile is not covered and should be considered for collisions
//      }
//    }
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