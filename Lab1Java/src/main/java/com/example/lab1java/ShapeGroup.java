package com.example.lab1java;

import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShapeGroup extends Shape {
    public static final int TYPE = 0;
    private List<Shape> shapes;
    public ShapeGroup() {
        shapes = new ArrayList<>();
    }
    public void addShape(Shape shape) {
        shapes.add(shape);
    }
    /*public void addShapeGroup(ShapeGroup shape) {
        shapeGroups.add(shape);
    }*/
    public boolean removeShape(Shape shape) {
        return shapes.remove(shape);
    }
    public Shape getShape(int index) {
        if (index >= 0 && index < shapes.size()) {
            return shapes.get(index);
        }
        return null;
    }
    public Shape removeShapeAt(int index) {
        if (index >= 0 && index < shapes.size()) {
            return shapes.remove(index);
        }
        return null;
    }
    public List<Shape> getAllShapes() {
        return new ArrayList<>(shapes);
    }

    @Override public void draw(GraphicsContext gc) {
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ShapeGroup other)) return false;
        if (this.shapes.size() != other.shapes.size()) {
            return false;
        }
        for (int i = 0; i < this.shapes.size(); i++) {
            if (!this.shapes.get(i).equals(other.shapes.get(i))) {
                return false;
            }
        }
        return true;
    }
    public void writeToStream(OutputStream outputStream) throws IOException {
        outputStream.write(TYPE);
        outputStream.write(shapes.size());

        // Создаем экземпляр DrawableOutputStream для записи фигур
        try (DrawableOutputStream drawableOut = new DrawableOutputStream(outputStream)) {
            for (Shape drawable : shapes) {
                drawableOut.writeDrawable(drawable); // Записываем каждую фигуру в поток
            }
        }
    }

    public static ShapeGroup readFromInput(InputStream input) throws IOException {
        ShapeGroup group = new ShapeGroup();

        // Читаем количество фигур
        int size = input.read();
        try (DrawableInputStream drawableIn = new DrawableInputStream(input)) {
            for (int i = 0; i < size; i++) {
                Shape drawable = drawableIn.readDrawable(); // Читаем фигуру
                group.addShape(drawable); // Добавляем ее в группу
            }
        }
        return group;
    }

    @Override
    public String toString() {
        return "Group{" +
                "Shapes=" + shapes +
                '}';
    }

}
