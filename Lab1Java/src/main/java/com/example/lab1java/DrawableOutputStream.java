package com.example.lab1java;
import java.io.OutputStream;
import java.io.IOException;
import java.util.List;
public class DrawableOutputStream extends OutputStream{
    private OutputStream original;

    public DrawableOutputStream(OutputStream original) {
        this.original = original;
    }
    public void writeDrawable(Shape Drawable) throws IOException {
        Drawable.writeToStream(original);
    }
    public void writeDrawables(List<Shape> Drawables) throws IOException {
        for (Shape Shape : Drawables) {
            writeDrawable(Shape);
        }
    }
    @Override
    public void write(int b) throws IOException {
        original.write(b);
    }
}
