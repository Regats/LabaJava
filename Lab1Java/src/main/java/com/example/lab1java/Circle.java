package com.example.lab1java;

import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.io.InputStream;
import javafx.scene.paint.Color;

public class Circle extends Shape {
    public static final int TYPE = 4;
    public Point center1;
    private double x, y, r;
    public Circle(Point center1, double r){
        this.center1 = center1;
        this.r = r;
    }
    public Circle(double x, double y, double r) {
        this.center1 = new Point(x, y);
        this.x = x;
        this.y = y;
        this.r = r;
    }
    @Override public void draw(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.strokeOval((int) (x - r), (int) (y - r), (int) (r * 2), (int) (r * 2));
    }
    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        outputStream.write(TYPE);
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(center1.getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(center1.getY()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(r).array());
    }
    public static Circle readFromInput(InputStream input) throws IOException {
        byte[] buffer = new byte[Double.BYTES];

        input.read(buffer);
        double x = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double y = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double r = ByteBuffer.wrap(buffer).getDouble();

        return new Circle(x, y, r);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Проверка на идентичность ссылок
        if (!(obj instanceof Circle other)) return false; // Проверка на тип
        return center1.equals(other.center1) &&
                Double.compare(r, other.r) == 0;
    }
    @Override
    public String toString() {
        return "Circle{" +
                "x=" + center1.getX() +
                ", y=" + center1.getY() +
                ", radius=" + r +
                '}';
    }
}

