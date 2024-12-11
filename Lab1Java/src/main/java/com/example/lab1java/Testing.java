package com.example.lab1java;

import java.io.*;
import java.util.List;

public class Testing {

    // Тестирование сериализации и десериализации одной фигуры
    public static void testSingleDrawable(Shape drawable) throws IOException {
        // Запись в файл
        try (DrawableOutputStream out = new DrawableOutputStream(new FileOutputStream("drawable.dat"))) {
            out.writeDrawable(drawable);
        }

        // Чтение из файла
        Shape readDrawable;
        try (DrawableInputStream in = new DrawableInputStream(new FileInputStream("drawable.dat"))) {
            readDrawable = in.readDrawable();
        }

        // Сравнение
        if (drawable.equals(readDrawable)) {
            System.out.println("Фигура успешно сериализована и десериализована: " + drawable);
        } else {
            System.out.println(drawable);
            System.out.println("Ошибка: фигура не совпадает после десериализации.");
            System.out.println(readDrawable);
        }
    }

    // Тестирование списка фигур
    public static void testDrawableList(List<Shape> drawables) throws IOException {
        // Запись в файл
        try (DrawableOutputStream out = new DrawableOutputStream(new FileOutputStream("drawables.dat"))) {
            out.writeDrawables(drawables);
        }

        // Чтение из файла
        List<Shape> readDrawables;
        try (DrawableInputStream in = new DrawableInputStream(new FileInputStream("drawables.dat"))) {
            readDrawables = in.readDrawables();
        }

        // Сравнение
        if (drawables.equals(readDrawables)) {
            System.out.println("Список фигур успешно сериализован и десериализован.");
        } else {
            System.out.println("Ошибка: список фигур не совпадает после десериализации.");
        }
    }

    // Тестирование группы фигур
    public static void testGroup(ShapeGroup group) throws IOException {
        // Запись в файл
        try (DrawableOutputStream out = new DrawableOutputStream(new FileOutputStream("group.dat"))) {
            out.writeDrawables(group.getAllShapes());
        }

        // Чтение из файла
        ShapeGroup readGroup;
        try (DrawableInputStream in = new DrawableInputStream(new FileInputStream("group.dat"))) {
            readGroup = new ShapeGroup(); // Создаем новую группу для чтения
            List<Shape> readDrawables = in.readDrawables();
            for (Shape drawable : readDrawables) {
                readGroup.addShape(drawable); // Добавляем фигуры в группу
            }
        }

        // Сравнение
        if (group.equals(readGroup)) {
            System.out.println("Группа фигур успешно сериализована и десериализована.");
        } else {
            System.out.println(group);
            System.out.println("Ошибка: группа фигур не совпадает после десериализации.");
            System.out.println(readGroup);
        }
    }
}