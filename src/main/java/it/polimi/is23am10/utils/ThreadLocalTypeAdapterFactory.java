package it.polimi.is23am10.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class ThreadLocalTypeAdapterFactory implements TypeAdapterFactory {
  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    if (ThreadLocal.class.isAssignableFrom(type.getRawType())) {
      return new TypeAdapter<T>() {
        @Override
        public void write(JsonWriter out, T value) throws IOException {
          // Write the thread-local value as a string
          out.value(value.toString());
        }

        @Override
        public T read(JsonReader in) throws IOException {
          // Read the thread-local value as a string
          String value = in.nextString();

          // Create a new thread-local with the same value
          ThreadLocal<String> threadLocal = new ThreadLocal<>();
          threadLocal.set(value);

          @SuppressWarnings("unchecked")
          T result = (T) threadLocal;
          return result;
        }
      };
    }

    // For other types, use the default adapter
    return null;
  }
}
