package com.heshustle.interaction;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

/**
 * Class that specifies interactions that can be imported from tilemaps, and their various stats.
 */
public class Interaction {

    /**
     * Types of interaction.
     */
    public enum Type {
        STUDY, EAT, RECREATION, SLEEP
    }

    private final Rectangle bounds;
    private final Type type;
    private final String name;
    private final int timeLength;

    /**
     * Constructor for Interaction.
     * @param rectObject Rect that defines the area in which it can be interacted with.
     * @param type Type of interaction that it is (there can be multiple instances of interactions
     *             with the same type).
     * @param name The name of this interaction, for rendering purposes.
     * @param timeLength How long this interaction takes in  in-game hours.
     */
    public Interaction(RectangleMapObject rectObject, Type type, String name, int timeLength) {
        this.bounds = rectObject.getRectangle();
        this.type = type;
        this.name = name;
        this.timeLength = timeLength;
    }

    /**
     * Returns the interaction's rect. This defines the area in which it can be interacted with.
     * @return The interaction's rect.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Returns the interaction's type. This specifies the type of interaction that can be had within
     * this instance's rect.
     * @return This interaction's type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns this interaction's name.
     * @return This interaction's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns how long this interaction takes (in in-game hours).
     * @return How long this interaction takes.
     */
    public int getTimeLength() { return timeLength; }
}
