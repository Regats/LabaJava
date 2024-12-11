package com.example.lab1java;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawableInputStream extends InputStream {
    private InputStream original;
    public DrawableInputStream(InputStream original) {
        this.original = original;
    }
    public Shape readDrawable() throws IOException {
        int type = original.read();
        return switch (type) {
            case ShapeGroup.TYPE -> ShapeGroup.readFromInput(original);
            case Line.TYPE -> Line.readFromInput(original);
            case Triangle.TYPE -> Triangle.readFromInput(original);
            case Rect.TYPE -> Rect.readFromInput(original);
            case Circle.TYPE -> Circle.readFromInput(original);
            case Ellipse.TYPE -> Ellipse.readFromInput(original);
            default -> throw new IOException("Unknown shape type: " + type);
        };
    }
    public List<Shape> readDrawables() throws IOException {
        List<Shape> drawables = new ArrayList<>();
        while (original.available() > 0) {
            drawables.add(readDrawable());
        }
        return drawables;
    }

    @Override
    public int read() throws IOException {
        return original.read();
    }
}