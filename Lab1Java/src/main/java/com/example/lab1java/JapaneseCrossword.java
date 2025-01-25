package com.example.lab1java;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.input.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JapaneseCrossword {
    private int[][] crosswordOriginal, crosswordClone;
    private static List<List<Integer>> sequencesOfOnesInColumns, sequencesOfOnesInRows;
    private static final int CELL_SIZE = 20; // Размер клетки

    public void loadCrosswordFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Считываем весь файл как одну строку
            StringBuilder stringBuilder = new StringBuilder();
            int character;

            // Чтение всего файла в одну строку
            while ((character = br.read()) != -1) {
                stringBuilder.append((char) character);
            }

            // Получаем содержимое как строку
            String content = stringBuilder.toString().trim(); // Убираем пробелы по краям
            String[] lines = content.split("\n"); // Разделяем по переносам строк

            // Проверяем, что у нас достаточно данных
            if (lines.length < 2) {
                throw new IllegalArgumentException("Недостаточно данных для определения размеров.");
            }

            // Первую строку используем для размеров
            String[] dimensions = lines[0].trim().split("\\s+");
            int rows = Integer.parseInt(dimensions[0].trim());
            int columns = Integer.parseInt(dimensions[1].trim());

            // Создаем массив нужного размера
            crosswordOriginal = new int[rows][columns];
            crosswordClone = new int[rows][columns];

            // Заполняем массив данными
            for (int i = 0; i < rows; i++) {
                if (i + 1 < lines.length) { // Проверяем, есть ли следующая строка
                    String[] values = lines[i + 1].trim().split("\\s+"); // Разделяем значения в строке
                    for (int j = 0; j < columns; j++) {
                        if (j < values.length) { // Проверяем, что значение существует
                            crosswordOriginal[i][j] = Integer.parseInt(values[j].trim());
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка формата числа: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public void drawField(GraphicsContext gc) {
        sequencesOfOnesInColumns = findLengthsOfSequences(crosswordOriginal);
        int[][] transposeMatrix = transposeMatrix(crosswordOriginal);
        sequencesOfOnesInRows = findLengthsOfSequences(transposeMatrix);
        int maxLengthinColumns = findMaxLength(sequencesOfOnesInColumns);
        int maxLengthInRows = findMaxLength(sequencesOfOnesInRows);

        Canvas canvas = gc.getCanvas();

        gc.setStroke(Color.RED);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //gc.fillRect(0, 0,canvas.getWidth() - crossword[0].length * cellSize, canvas.getHeight() - crossword.length * cellSize);

        for(int i = 0; i < maxLengthinColumns; i++){
            for (int j = 0; j < sequencesOfOnesInColumns.size(); j++){
                gc.setFill(Color.WHITE);
                gc.fillRect((canvas.getWidth() - crosswordOriginal[0].length * CELL_SIZE) + j * CELL_SIZE,
                        (canvas.getHeight() - crosswordOriginal.length * CELL_SIZE) - (i * CELL_SIZE) - CELL_SIZE, CELL_SIZE, CELL_SIZE);
                gc.setStroke(Color.BLACK);
                gc.strokeRect((canvas.getWidth() - crosswordOriginal[0].length * CELL_SIZE) + j * CELL_SIZE,
                        (canvas.getHeight() - crosswordOriginal.length * CELL_SIZE) - (i * CELL_SIZE) - CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // Проверяем, есть ли значение в текущей позиции
                if (i < sequencesOfOnesInColumns.get(j).size()) {
                    int sequenceLength = sequencesOfOnesInColumns.get(j).get(i);
                    // Устанавливаем шрифт и цвет текста
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font("Arial", 14)); // Установите нужный шрифт и размер

                    // Вычисляем координаты для размещения текста по центру клетки
                    String text = String.valueOf(sequenceLength);
                    double textWidth = gc.getFont().getSize() * text.length(); // Ширина текста
                    double textHeight = gc.getFont().getSize(); // Высота текста

                    // Рисуем текст по центру клетки
                    gc.fillText(text,
                            (canvas.getWidth() - crosswordOriginal[0].length * CELL_SIZE) + j * CELL_SIZE + (CELL_SIZE - textWidth) / 2 + 4,
                            (canvas.getHeight() - crosswordOriginal.length * CELL_SIZE) - (i * CELL_SIZE) - CELL_SIZE + (CELL_SIZE + textHeight / 2) / 2);
                }
            }
        }

        for (int i = 0; i < sequencesOfOnesInRows.size(); i++) {
            for (int j = 0; j < maxLengthInRows; j++) {
                gc.setFill(Color.WHITE);
                gc.fillRect(canvas.getWidth() - crosswordOriginal[0].length * CELL_SIZE - maxLengthInRows * CELL_SIZE + j * CELL_SIZE,
                        canvas.getHeight() - crosswordOriginal.length * CELL_SIZE + i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(canvas.getWidth() - crosswordOriginal[0].length * CELL_SIZE - maxLengthInRows * CELL_SIZE + j * CELL_SIZE,
                        (canvas.getHeight() - crosswordOriginal.length * CELL_SIZE + i * CELL_SIZE), CELL_SIZE, CELL_SIZE);

                // Проверяем, есть ли значение в текущей позиции
                if (j < sequencesOfOnesInRows.get(i).size()) {
                    int sequenceLength = sequencesOfOnesInRows.get(i).get(j); // Получаем длину последовательности

                    // Устанавливаем шрифт и цвет текста
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font("Arial", 14)); // Установите нужный шрифт и размер

                    // Вычисляем координаты для размещения текста по центру клетки
                    String text = String.valueOf(sequenceLength);
                    double textWidth = gc.getFont().getSize() * text.length(); // Ширина текста
                    double textHeight = gc.getFont().getSize(); // Высота текста

                    // Рисуем текст по центру клетки
                    gc.fillText(text,
                            canvas.getWidth() - crosswordOriginal[0].length * CELL_SIZE - maxLengthInRows * CELL_SIZE + j * CELL_SIZE + (CELL_SIZE - textWidth) / 2 + 4,
                            canvas.getHeight() - crosswordOriginal.length * CELL_SIZE + i * CELL_SIZE + (CELL_SIZE + textHeight / 2) / 2);
                }
            }
        }
        drawCells(gc);
    }

    private void drawCells (GraphicsContext gc){
        gc.save();
        gc.translate(gc.getCanvas().getWidth() - crosswordOriginal[0].length * CELL_SIZE, gc.getCanvas().getHeight() - crosswordOriginal.length * CELL_SIZE);
        System.out.println("Перерисовал");
        gc.clearRect(0, 0, crosswordOriginal[0].length * CELL_SIZE, crosswordOriginal.length * CELL_SIZE);
        for (int i = 0; i < crosswordClone.length; i++) {

            for (int j = 0; j < crosswordClone[i].length; j++) {

                if (crosswordClone[i][j] == 1) {

                    gc.setFill(javafx.scene.paint.Color.BLACK);
                    gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Черная клетка
                    gc.setStroke(Color.GRAY);
                    gc.strokeRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                else if(crosswordClone[i][j] == -1){

                    gc.setFill(Color.BLACK);
                    gc.fillOval(j * CELL_SIZE + 5, i * CELL_SIZE + 5,10, 10); // Рисуем круг
                    gc.strokeRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                else {
                    gc.setFill(javafx.scene.paint.Color.WHITE);
                    gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Белая клетка
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Рисуем границу белой клетки
                }
            }
        }

        for (int i = 0; i < crosswordClone.length; i+=5) {

            if ((i) % 5 == 0) {
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(3); // Увеличиваем толщину линии для горизонтальных границ
                gc.strokeLine(0, (i) * CELL_SIZE, crosswordClone.length * CELL_SIZE, (i) * CELL_SIZE);
                gc.strokeLine((i) * CELL_SIZE, 0, (i) * CELL_SIZE, crosswordClone.length * CELL_SIZE);
                gc.setLineWidth(1); // Возвращаем толщину линии к нормальной
            }
        }
        gc.restore();
    }

    public void setupMouseListener(Canvas canvas) {
        //canvas.setTranslateX(canvas.getWidth() - crosswordOriginal[0].length * CELL_SIZE);
        //canvas.setTranslateY(canvas.getHeight() - crosswordOriginal.length * CELL_SIZE);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int x = (int) event.getX() - (int)(canvas.getWidth() - crosswordOriginal[0].length * CELL_SIZE);
            int y = (int) event.getY() - (int)(canvas.getHeight() - crosswordOriginal.length * CELL_SIZE);

            // Определяем индекс клетки
            int cellX = x / CELL_SIZE;
            int cellY = y / CELL_SIZE;

            // Проверяем, находятся ли индексы в пределах массива
            if (cellX >= 0 && cellX < crosswordOriginal[0].length && cellY >= 0 && cellY < crosswordOriginal.length) {
                System.out.println("Клетка" + cellX + " " + cellY);
                handleCellClick(cellX, cellY, event.getButton());
                drawCells(canvas.getGraphicsContext2D()); // Перерисовываем поле
            }
        });
    }

    private void handleCellClick(int cellX, int cellY, MouseButton button) {
        if (button == MouseButton.PRIMARY) { // Левая кнопка мыши
            if (crosswordClone[cellY][cellX] != -1) { // Проверяем, не заблокирована ли клетка
                crosswordClone[cellY][cellX] = crosswordClone[cellY][cellX] == 1 ? 0 : 1; // Меняем состояние клетки
            }
        } else if (button == MouseButton.SECONDARY) { // Правая кнопка мыши
            crosswordClone[cellY][cellX] = crosswordClone[cellY][cellX] == -1 ? 0 : -1; // Блокируем/разблокируем клетку
        }
    }

    public static int[][] transposeMatrix(int [][] m){
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    private static List<List<Integer>> findLengthsOfSequences(int[][] binaryArray) {
        List<List<Integer>> allSequences = new ArrayList<>();

        for (int j = 0; j < binaryArray[0].length; j++) {
            List<Integer> currentColSequences = new ArrayList<>();
            int currentLength = 0; // Текущая длина последовательности

            for (int i = 0; i < binaryArray.length; i++) {
                if (binaryArray[i][j] == 1) {
                    currentLength++; // Увеличиваем длину последовательности
                } else {
                    if (currentLength > 0) {
                        currentColSequences.add(currentLength); // Добавляем длину текущей последовательности
                        currentLength = 0; // Сбрасываем текущую длину
                    }
                }
            }

            // Проверяем последнюю последовательность
            if (currentLength > 0) {
                currentColSequences.add(currentLength);
            }
            Collections.reverse(currentColSequences);

            allSequences.add(currentColSequences); // Добавляем все последовательности для текущего столбца
        }

        return allSequences;
    }
    /*private static List<List<Integer>> findLengthsOfSequencesOfOnesInRows(int[][] binaryArray) {
        List<List<Integer>> allSequences = new ArrayList<>();

        for (int i = 0; i < binaryArray.length; i++) {
            List<Integer> currentRowSequences = new ArrayList<>();
            int currentLength = 0; // Текущая длина последовательности

            for (int j = 0; j < binaryArray[i].length; j++) {
                if (binaryArray[i][j] == 1) {
                    currentLength++; // Увеличиваем длину последовательности
                } else {
                    if (currentLength > 0) {
                        currentRowSequences.add(currentLength); // Добавляем длину текущей последовательности
                        currentLength = 0; // Сбрасываем текущую длину
                    }
                }
            }

            // Проверяем последнюю последовательность
            if (currentLength > 0) {
                currentRowSequences.add(currentLength);
            }

            allSequences.add(currentRowSequences); // Добавляем все последовательности для текущей строки
        }

        return allSequences;
    }*/

    public static int findMaxLength(List<List<Integer>> arrayLists){
        int maxLength = 0;

        for (List<Integer> list : arrayLists) {
            maxLength = Math.max(list.size(), maxLength);
        }

        return maxLength;
    }

    public void printCrossword() {
        for (int[] row : crosswordOriginal) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

}
