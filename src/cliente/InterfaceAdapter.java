package cliente;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import entidades.Convertible;

public class InterfaceAdapter<T extends Convertible> implements JsonDeserializer<T> {

    private static final String CLASSNAME = "className";

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext deserializationContext)
            throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        final String className = prim.getAsString();
        final Class<T> klass = getClassInstance(className);
        return deserializationContext.deserialize(jsonObject, klass);
    }

    @SuppressWarnings("unchecked")
    private Class<T> getClassInstance(String className2) {
        try {
            return (Class<T>) Class.forName(className2);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}
