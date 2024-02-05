package dev.corn.cornbackend.utils.json.interfaces;

/**
 * Interface for classes that can be converted to JSON.
 */
public interface Jsonable {
    /**
     * Converts the object to JSON.
     *
     * @return JSON representation of the object.
     */
    String toJson();

    /**
     * Converts the object to pretty JSON.
     *
     * @return Pretty JSON representation of the object.
     */
    String toPrettyJson();
}
