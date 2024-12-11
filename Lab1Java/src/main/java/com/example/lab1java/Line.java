package com.example.lab1java;

import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javafx.scene.paint.Color;

public class Line extends Shape {
    public static final int TYPE = 1;
    private Point[] points = new Point[2];
    private double x1, y1, x2, y2;
    public Line(Point p1, Point p2){
        points[0] = p1;
        points[1] = p2;
    }
    public Line(double x1, double y1, double x2, double y2){
        points[0] = new Point(x1, y1);
        points[1] = new Point(x2, y2);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override public void draw(GraphicsContext gc){
        gc.setStroke(Color.BLACK); // Установите цвет линии
        gc.setLineWidth(4); // Установите ширину линии
        gc.strokeLine(points[0].getX(), points[0].getY(),
                points[1].getX(), points[1].getY()); // Рисуем линию
    }

    @Override public void writeToStream(OutputStream outputStream) throws IOException {
        outputStream.write(TYPE);
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[0].getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[0].getY()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[1].getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[1].getY()).array());
    }

    public static Line readFromInput(InputStream input) throws IOException {
        byte[] buffer = new byte[Double.BYTES];

        input.read(buffer);
        double x1 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double y1 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double x2 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double y2 = ByteBuffer.wrap(buffer).getDouble();

        return new Line(x1, y1, x2, y2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Проверка на идентичность ссылок
        if (!(obj instanceof Line other)) return false; // Проверка на тип
        return (points[0].equals(other.points[0]) &&
                points[1].equals(other.points[1]));
    }
    @Override
    public String toString() {
        return "Line{" +
                "x1=" + points[0].getX() +
                ", y1=" + points[0].getY() +
                ", x2=" + points[1].getX() +
                ", y2=" + points[1].getY() +
                '}';
    }
}
