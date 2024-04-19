package model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ShapeDeserializer implements JsonDeserializer<Shape> {
    @Override
    public Shape deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String shapeType = jsonObject.get("shape").getAsString();

        if (shapeType.equals("OVAL")) {
            return jsonDeserializationContext.deserialize(jsonObject, Oval.class);
        } else if (shapeType.equals("RECTANGLE")) {
            return jsonDeserializationContext.deserialize(jsonObject, Rectangle.class);
        } else if (shapeType.equals("LINE")) {
            return jsonDeserializationContext.deserialize(jsonObject, Line.class);
        } else {
            throw new JsonParseException("Unknown shape type: " + shapeType);
        }
    }
}
