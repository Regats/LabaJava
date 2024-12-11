package com.example.lab1java;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    // Метод для создания треугольника
    public static Shape line() {
        return new Line(1.2, 3.4, 5.6, 7.8);
    }
    public static Shape line1() {
        return new Line(0.0, 2.0, 1.5, 0.5);
    }
    public static Shape circle() {
        return new Circle(0.0, 0.0, 5);
    }
    public static Shape circle1() {
        return new Circle(2.5, 1.3, 2);
    }
    public static Shape ellipce() {
        return new Ellipse(0.0, 0.0, 1.0, 0.0, 2);
    }
    public static Shape ellipce1() {
        return new Ellipse(3.0, 4.0, 1.0, 0.0, 5);
    }
    public static Shape triangle() {
        return new Triangle(40.0, 35.0, 50.0, 100.0, 100, 50);
    }
    public static Shape triangle1() {
        return new Triangle(1.0, 1.0, 2.0, 1.0, 1.5, 2);
    }
    public static Shape rectangle() {
        return new Rect(0.0, 0.0, 2.0, 1.0);
    }
    public static Shape rectangle1() {
        return new Rect(1.0, 2.0, 3.0, 3.0);
    }

    // Метод для создания списка фигур (набор 1)
    public static List<Shape> drawableList1() {
        List<Shape> list = new ArrayList<>();
        list.add(triangle());
        list.add(rectangle());
        return list;
    }

    // Метод для создания списка фигур (набор 2)
    public static List<Shape> drawableList2() {
        List<Shape> list = new ArrayList<>();
        list.add(triangle1());
        list.add(rectangle1());
        return list;
    }

    // Метод для создания плоской группы с фигурами из первого списка
    public static ShapeGroup flatGroup() {
        ShapeGroup group = new ShapeGroup();
        for (Shape drawable : drawableList1()) {
            group.addShape(drawable);
        }
        return group;
    }

    // Метод для создания вложенной группы
    public static ShapeGroup nestedGroup() {
        ShapeGroup outerGroup = new ShapeGroup();

        // Внутренняя группа с фигурами из первого списка
        ShapeGroup innerGroup = new ShapeGroup();
        for (Shape drawable : drawableList1()) {
            innerGroup.addShape(drawable);
        }

        // Внешняя группа с фигурами из второго списка
        for (Shape drawable : drawableList2()) {
            outerGroup.addShape(drawable);
        }

        // Добавляем внутреннюю группу во внешнюю
        outerGroup.addShape(innerGroup); // Вложенная группа

        return outerGroup;
    }
}
