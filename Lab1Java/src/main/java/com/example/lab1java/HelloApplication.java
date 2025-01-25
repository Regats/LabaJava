package com.example.lab1java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello JavaFX");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Генерация плоской группы
        ShapeGroup flatGroup = Generator.flatGroup();

        System.out.println("Flat Group contains:");
        for (Drawable drawable : flatGroup.getAllShapes()) {
            System.out.println(drawable); // Вывод информации о фигурах в группе
        }
        // Генерация вложенной группы
        ShapeGroup nestedGroup = Generator.nestedGroup();

        System.out.println("\nNested Group contains:");
        for (Drawable drawable : nestedGroup.getAllShapes()) {
            System.out.println(drawable);// Вывод информации о фигурах в группе
            if (drawable instanceof ShapeGroup) { // Проверка на вложенную группу
                System.out.println("Contains inner group with:");
                for (Drawable innerDrawable : ((ShapeGroup) drawable).getAllShapes()) {
                    System.out.println(innerDrawable);
                }
            }
        }


        try {
            // Тестирование одиночной фигуры
            Testing.testSingleDrawable(Generator.line());
            Testing.testSingleDrawable(Generator.circle());
            Testing.testSingleDrawable(Generator.ellipce());
            Testing.testSingleDrawable(Generator.triangle());
            Testing.testSingleDrawable(Generator.rectangle());

            // Тестирование списков фигур
            Testing.testDrawableList(Generator.drawableList1());
            Testing.testDrawableList(Generator.drawableList2());

            // Тестирование групп фигур
            Testing.testGroup(Generator.flatGroup());
            Testing.testGroup(Generator.nestedGroup());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (DrawableOutputStream out = new DrawableOutputStream(new FileOutputStream("shapes1.txt"))) {
            out.writeDrawables(Generator.flatGroup().getAllShapes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (DrawableInputStream in = new DrawableInputStream(new FileInputStream("shapes1.txt"))) {
            ShapeGroup readGroup = new ShapeGroup(); // Создаем новую группу для чтения
            List<Shape> readDrawables = in.readDrawables();
            for (Shape drawable : readDrawables) {
                readGroup.addShape(drawable); // Добавляем фигуры в группу
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

        // Создаем список фигур для дома
        /*List<Shape> shapes = new ArrayList<>();
        // Стены дома
        // Стены дома (прямоугольник)
        shapes.add(new Rect(300, 300, 200, 150)); // Основной прямоугольник

        // Крыша (треугольник)
        shapes.add(new Triangle(250, 300, 550, 300, 400, 200)); // Треугольник для крыши

        // Дверь (прямоугольник)
        shapes.add(new Rect(400, 380, 40, 70)); // Дверь

        // Окна (прямоугольники)
        shapes.add(new Rect(320, 340,40,40)); // Левое окно
        shapes.add(new Rect(460, 340, 40, 40)); // Правое окно
        shapes.add(new Rect(450, 200, 30, 60)); // Труба

        // Дым (круги)
        shapes.add(new Circle(465, 190, 10)); // Дым из трубы
        shapes.add(new Circle(475, 160, 10));
        shapes.add(new Circle(455, 150, 10));
        DrawableOutputStream out = new DrawableOutputStream(new FileOutputStream("shapes1.txt"));
        out.writeDrawables(shapes);*/

        launch(args);
    }
}