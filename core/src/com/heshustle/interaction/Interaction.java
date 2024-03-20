package com.heshustle.interaction;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

public class Interaction {
    public enum Type {
        STUDY, EAT, RECREATION, SLEEP
    }

    private final Rectangle bounds;
    private final Type type;
    private final String name;
    private final int timeLength;

    public Interaction(RectangleMapObject rectObject, Type type, String name, int timeLength) {
        this.bounds = rectObject.getRectangle();
        this.type = type;
        this.name = name;
        this.timeLength = timeLength;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getTimeLength() { return timeLength; }
}
