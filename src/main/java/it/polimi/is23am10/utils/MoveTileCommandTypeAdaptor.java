package it.polimi.is23am10.utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;

class CustomKeySerializer implements JsonSerializer<Coordinates> {
  @Override
  public JsonElement serialize(Coordinates src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject obj = new JsonObject();
    obj.addProperty("row", src.getRow());
    obj.addProperty("col", src.getCol());
    return obj;
  }
}
  
public class MoveTileCommandTypeAdaptor implements JsonSerializer<Map<Coordinates, Coordinates>> {
  @Override
  public JsonElement serialize(Map<Coordinates, Coordinates> src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject obj = new JsonObject();
    for (Map.Entry<Coordinates, Coordinates> entry : src.entrySet()) {
      obj.add(context.serialize(entry.getKey()).toString(), context.serialize(entry.getValue()));
    }
    return obj;
  }
}
