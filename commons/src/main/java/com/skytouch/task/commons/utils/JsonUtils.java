package com.skytouch.task.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Utility class with common JSON handling methods.
 *
 * @author Waldo Terry
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Marshalls an object into a JSON.
     *
     * @param src The object to marshall inmto Json. Cannot be null.
     * @return A <code>String</code> containing a valid JSON.
     * @throws JsonProcessingException If the object cannot be marshalled into JSON for any reason.
     * @throws IllegalArgumentException If the source object provided is <code>null</code>.
     */
    public static String getJsonFromObject(Object src) throws JsonProcessingException {
        if (src == null) {
            throw new IllegalArgumentException("Source object cannot be null.");
        }
        return MAPPER.writeValueAsString(src);
    }

    /**
     * Unmarshalls a valid JSON into an object.
     *
     * @param src A <code>String</code> containing the JSON source.
     * @param clazz The object class that this method will try to marshall the JSON data into.
     * @return The obtained object containing the unmarshalled data.
     * @throws IOException if the unmarshalling fails for any reason.
     */
    public static <T> T getObjectFromJson(String src, Class<T> clazz) throws IOException {
        return MAPPER.readValue(src, clazz);
    }

    /**
     * Parses a list of objects from a json known to contain a list of objects.
     * @param src The json string.
     * @param clazz The type of object to derive from the json source.
     * @return A List of the given objects found in the json source.
     * @throws IOException If the provided json is not valid or does not contain only a list of objects.
     */
    public static <T> List<T> getObjectListFromJson(String src, Class<T> clazz) throws IOException {
        return MAPPER.readValue(src, new TypeReference<List<T>>(){});
    }
}
