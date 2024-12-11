package com.example.lab1java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.OutputStream;


public class Point extends Shape {

    private double x, y;
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    @Override public void draw(GraphicsContext gc){
        double pointSize = 5.0; // Размер точки
        gc.setFill(Color.BLUE); // Цвет точки
        gc.fillOval(x - pointSize / 2, y - pointSize / 2, pointSize, pointSize); // Рисуем круг
    }
    @Override public void writeToStream(OutputStream outputStream){
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Проверка на идентичность ссылок
        if (!(obj instanceof Point other)) return false; // Проверка на тип
        return Double.compare(x, other.x) == 0 &&
                Double.compare(y, other.y) == 0;
    }
}
