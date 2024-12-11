package com.example.lab1java;

import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Shape implements IDrawable{
    @Override
    public void draw(GraphicsContext gc) {
    }
    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
    }
    public static Shape reafFromInput(InputStream input) throws IOException {
        return null;
    }
}
