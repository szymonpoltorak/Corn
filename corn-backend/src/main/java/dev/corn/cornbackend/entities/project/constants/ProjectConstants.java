package dev.corn.cornbackend.entities.project.constants;


public final class ProjectConstants {
    public static final String PROJECT_NAME_FIELD_NAME = "name";
    public static final String PROJECT_NAME_BLANK_MSG = "Name cannot be null and has to contain at least one non-whitespace character";
    public static final String PROJECT_NAME_WRONG_SIZE_MSG = "Name must consist of max 100 characters";
    public static final int PROJECT_NAME_MAX_SIZE = 100;

    public static final String PROJECT_SPRINTS_FIELD_NAME = "sprints";
    public static final String PROJECT_SPRINTS_NULL_ELEMENTS_MSG = "Sprints cannot be null nor contain null elements";

    public static final String PROJECT_OWNER_FIELD_NAME = "owner";
    public static final String PROJECT_OWNER_NULL_MSG = "Owner cannot be null";
    public static final int PROJECT_NAME_MIN_SIZE = 2;

    private ProjectConstants() {

    }
}
