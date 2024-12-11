package com.example.lab1java;

import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IDrawable {
    void draw(GraphicsContext gc);
    void writeToStream(OutputStream outputStream) throws IOException;
}
