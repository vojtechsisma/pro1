package model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ShapeDeserializer implements JsonDeserializer<Shape> {
    @Override
    public Shape deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String shapeType = jsonObject.get("shape").getAsString();

        return switch (shapeType) {
            case "OVAL" -> jsonDeserializationContext.deserialize(jsonObject, Oval.class);
            case "RECTANGLE" -> jsonDeserializationContext.deserialize(jsonObject, Rectangle.class);
            case "LINE" -> jsonDeserializationContext.deserialize(jsonObject, Line.class);
            default -> throw new JsonParseException("Unknown shape type: " + shapeType);
        };
    }
}
