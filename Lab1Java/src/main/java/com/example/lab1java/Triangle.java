package com.example.lab1java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Triangle extends Shape {
    public static final int TYPE = 2;
    private Point[] points = new Point[3];
    private double x1, y1, x2, y2, x3, y3;
    private double a, b, c;
    private double degrees;
    public Triangle(Point p1, Point p2, Point p3){
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
    }
    /*public Triangle(Point start, double a, double b, double c, double degrees){
        this.start = start;
        this.a = a;
        this.b = b;
        this.c = c;
        this.degrees = degrees;
    }*/
    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        points[0] = new Point(x1, y1);
        points[1] = new Point(x2, y2);
        points[2] = new Point(x3, y3);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }
    @Override public void draw(GraphicsContext gc){
        gc.setStroke(Color.BLACK); // Цвет границы
        gc.strokePolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, 3);
    }
    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        outputStream.write(TYPE);
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[0].getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[0].getY()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[1].getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[1].getY()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[2].getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(points[2].getY()).array());
    }

    public static Triangle readFromInput(InputStream input) throws IOException {
        byte[] buffer = new byte[Double.BYTES];

        input.read(buffer);
        double x1 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double y1 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double x2 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double y2 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double x3 = ByteBuffer.wrap(buffer).getDouble();

        input.read(buffer);
        double y3 = ByteBuffer.wrap(buffer).getDouble();

        return new Triangle(x1, y1, x2, y2, x3, y3);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Проверка на идентичность ссылок
        if (!(obj instanceof Triangle other)) return false; // Проверка на тип
        // Приведение типа
        return (points[0].equals(other.points[0]) &&
                points[1].equals(other.points[1]) &&
                points[2].equals(other.points[2]));
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "x1=" + points[0].getX() +
                ", y1=" + points[0].getY() +
                ", x2=" + points[1].getX() +
                ", y2=" + points[1].getY() +
                ", x3=" + points[2].getX() +
                ", y3=" + points[2].getY() +
                '}';
    }
}

