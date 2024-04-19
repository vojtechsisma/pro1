package model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.awt.*;
import java.io.IOException;

public class ColorAdapter extends TypeAdapter<Color> {
    @Override
    public Color read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        String color = reader.nextString();
        return new Color(Integer.parseInt(color));
    }

    @Override
    public void write(JsonWriter writer, Color value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        int color = value.getRGB();
        writer.value(color);
    }
}
