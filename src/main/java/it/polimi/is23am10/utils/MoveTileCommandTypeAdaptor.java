package it.polimi.is23am10.utils;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.util.HashMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Internal used class.
 * 
 */
class CustomKeySerializer implements JsonSerializer<Coordinates> {
  @Override
  public JsonElement serialize(Coordinates src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject obj = new JsonObject();
    obj.addProperty("row", src.getRow());
    obj.addProperty("col", src.getCol());
    return obj;
  }
}

/**
 * Internal used class.
 * 
 */
class CustomKeyDeserializer implements JsonDeserializer<Coordinates> {
  @Override
  public Coordinates deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject obj = json.getAsJsonObject();
    int row = obj.get("row").getAsInt();
    int col = obj.get("col").getAsInt();
    return new Coordinates(row, col);
  }
}
  
/**
 * Custom deserializer class definition for Gson usage.
 * This works {@link Map} with {@link Coordinates} as keys.
 * 
 */
public class MoveTileCommandTypeAdaptor implements JsonSerializer<Map<Coordinates, Coordinates>>, JsonDeserializer<Map<Coordinates, Coordinates>> {
  @Override
  public JsonElement serialize(Map<Coordinates, Coordinates> src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject obj = new JsonObject();
    for (Map.Entry<Coordinates, Coordinates> entry : src.entrySet()) {
      obj.add(context.serialize(entry.getKey()).toString(), context.serialize(entry.getValue()));
    }
    return obj;
  }

  @Override
  public Map<Coordinates, Coordinates> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject obj = json.getAsJsonObject();
    Map<Coordinates, Coordinates> map = new HashMap<>();
    for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
      String keyString = entry.getKey();
      Coordinates key = context.deserialize((new JsonParser()).parse(keyString), Coordinates.class);
      Coordinates value = context.deserialize(entry.getValue(), Coordinates.class);
      map.put(key, value);
    }
    return map;
  }
}
