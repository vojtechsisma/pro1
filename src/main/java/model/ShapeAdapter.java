package model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ShapeAdapter extends TypeAdapter<Shape> {

    @Override
    public void write(JsonWriter jsonWriter, Shape shape) throws IOException {

    }

    // TODO: Implement read method
    @Override
    public Shape read(JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldname = null;
        String type = null;
        Shape shape = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            System.out.println(token);
            if (token.equals(JsonToken.NAME)) {
                // get the current token
                fieldname = reader.nextName();
            }

            if ("shape".equals(fieldname)) {
                // move to next token
                token = reader.peek();
                type = reader.nextString();
            }

            System.out.println(type);

            switch (type) {
                case "RECTANGLE":
                    shape = (new Rectangle()).fromJson(reader.toString());
                case "OVAL":
                    shape = (new Oval()).fromJson(reader.toString());
                case "LINE":
                    shape = (new Line()).fromJson(reader.toString());
            }
        }

        reader.endObject();
        return shape;
    }
}
