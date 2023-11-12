package dev.corn.cornbackend.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.corn.cornbackend.utils.json.interfaces.Jsonable;

public final class JsonMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonMapper() {
    }

    public static String toJson(Jsonable jsonable) {
        try {
            return MAPPER
                    .writer()
                    .writeValueAsString(jsonable);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public static String toPrettyJson(Jsonable jsonable) {
        try {
            return MAPPER
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonable);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
