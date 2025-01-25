package com.example.lab1java;

import com.example.lab1java.DrawableInputStream;
import com.example.lab1java.Shape;
import com.example.lab1java.ShapeGroup;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class HelloController {
    @FXML
    public Button selectPaintButton, selectCrosswordButton; // Ссылка на кнопку
    @FXML
    private Canvas canvas; // Ссылка на Canvas из FXML

    private GraphicsContext gc; // Объявляем gc без инициализации

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
    }

    @FXML
    protected void handleFileSelection() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Получаем текущую стадию (Stage)
        Stage stage = (Stage) selectPaintButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {

            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try (FileInputStream fileInputStream = new FileInputStream(selectedFile);
                 DrawableInputStream in = new DrawableInputStream(fileInputStream)) {

                    ShapeGroup readGroup = new ShapeGroup(); // Создаем новую группу для чтения
                    List<Shape> readDrawables = in.readDrawables();

                    gc.setFill(Color.WHITE);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                    for (Shape drawable : readDrawables) {

                        readGroup.addShape(drawable); // Добавляем фигуры в группу
                    }
                    readGroup.draw(gc);

            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
                e.printStackTrace(); // Выводим стек вызовов для отладки
            }
        } else {
            System.out.println("Файл не был выбран.");
        }
    }
    @FXML
    protected void handleCrosswordSelection() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) selectPaintButton.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);

        JapaneseCrossword crossword = new JapaneseCrossword();

        if (selectedFile != null) {
            crossword.loadCrosswordFromFile(selectedFile);
        };
        crossword.printCrossword();
        crossword.drawField(gc);
        crossword.setupMouseListener(canvas);
    }
}