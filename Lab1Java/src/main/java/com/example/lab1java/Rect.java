package com.example.lab1java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Rect extends Shape {
    public static final int TYPE = 3;
    private Point[] points = new Point[2];
    private double x1, y1, width, height;
    public Rect(Point p1, Point p2){
        points[0] = p1;
        points[1] = p2;
    }
    public Rect(double x1, double y1, double width, double height) {
        points[0] = new Point(x1, y1);
        points[1] = new Point(width, height);
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.height = height;
    }
    @Override public void draw(GraphicsContext gc){
        gc.setStroke(Color.BLACK); // Цвет границы
        gc.strokeRect(x1, y1, width, height);
    }
    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        outputStream.write(TYPE);
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[0].getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[0].getY()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[1].getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[1].getY()).array());
    }

    public static Rect readFromInput(InputStream input) throws IOException {
        byte[] buffer = new byte[Double.BYTES];

        input.read(buffer);
        double x1 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double y1 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double x2 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double y2 = ByteBuffer.wrap(buffer).getDouble();

        return new Rect(x1, y1, x2, y2);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Проверка на идентичность ссылок
        if (!(obj instanceof Rect other)) return false; // Проверка на тип
        return (points[0].equals(other.points[0]) &&
                points[1].equals(other.points[1]));
    }
    @Override
    public String toString() {
        return "Rectangle{" +
                "x1=" + points[0].getX() +
                ", y1=" + points[0].getY() +
                ", x2=" + points[1].getX() +
                ", y2=" + points[1].getY() +
                '}';
    }

}

