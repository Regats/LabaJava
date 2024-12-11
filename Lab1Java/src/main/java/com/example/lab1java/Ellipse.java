package com.example.lab1java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Ellipse extends Shape {
    public static final int TYPE = 5;
    public Point upLeft, size;
    public double x1, y1, width, height, rotate;
    public Ellipse(Point upLeft, Point size, double rotate){
        this.upLeft = upLeft;
        this.size = size;
        this.rotate = rotate;
    }
    public Ellipse(double x1, double y1, double width, double height, double rotate){
        this.upLeft = new Point(x1, y1);
        this.size = new Point(width, height);
        this.x1 = x1;
        this.y1 = y1;
        this.height = height;
        this.width = width;
        this.rotate = rotate;
    }

    @Override public void draw(GraphicsContext gc){
        gc.save();
        gc.setStroke(Color.BLACK);
        gc.translate(upLeft.getX(), upLeft.getY());
        gc.rotate(rotate);
        gc.strokeOval(0, 0, width, height);
        gc.restore();
    }
    public void writeToStream(OutputStream outputStream) throws IOException {
        outputStream.write(TYPE);
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(upLeft.getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(upLeft.getY()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(size.getX()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(size.getY()).array());
        outputStream.write(ByteBuffer.allocate(Double.BYTES).putDouble(rotate).array());
    }
    public static Ellipse readFromInput(InputStream input) throws IOException {
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
        double r = ByteBuffer.wrap(buffer).getDouble();

        return new Ellipse(x1, y1, x2, y2, r);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Проверка на идентичность ссылок
        if (!(obj instanceof Ellipse other)) return false; // Проверка на тип
        return upLeft.equals(other.upLeft) &&
                size.equals(other.size) &&
                Double.compare(rotate, other.rotate) == 0;
    }
    @Override
    public String toString() {
        return "Ellipse{" +
                "x1=" + upLeft.getX() +
                ", y1=" + upLeft.getY() +
                ", x2=" + size.getX() +
                ", y2=" + size.getY() +
                ", radius=" + rotate +
                '}';
    }
}
