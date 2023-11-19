package dev.corn.cornbackend.entities.sprint.constants;

public final class SprintConstants {
    public static final String SPRINT_FIRST_DATE_GETTER_NAME = "getSprintStartDate";
    public static final String SPRINT_SECOND_DATE_GETTER_NAME = "getSprintEndDate";
    public static final String SPRINT_LATER_THAN_MSG = "Sprint end date must be later than start date";

    public static final String SPRINT_PROJECT_FIELD_NAME = "project";
    public static final String SPRINT_PROJECT_NULL_MSG = "Project cannot be null";

    public static final String SPRINT_NAME_FIELD_NAME = "sprintName";
    public static final String SPRINT_NAME_BLANK_MSG = "Name cannot be null and has to contain at least one non-whitespace character";
    public static final String SPRINT_NAME_WRONG_SIZE_MSG = "Name must consist of max 50 characters";
    public static final int SPRINT_NAME_MAX_SIZE = 50;

    public static final String SPRINT_DESCRIPTION_FIELD_NAME = "sprintDescription";
    public static final String SPRINT_DESCRIPTION_BLANK_MSG = "Description cannot be null and has to contain at least one non-whitespace character";
    public static final String SPRINT_DESCRIPTION_WRONG_SIZE_MSG = "Description must consist of max 500 characters";
    public static final int SPRINT_DESCRIPTION_MAX_SIZE = 500;

    public static final String SPRINT_START_DATE_FIELD_NAME = "sprintStartDate";
    public static final String SPRINT_START_DATE_NULL_MSG = "Sprint start Date cannot be null";
    public static final String SPRINT_START_DATE_FUTURE_OR_PRESENT_MSG = "Sprint start date has to be present or future date";

    public static final String SPRINT_END_DATE_FIELD_NAME = "sprintEndDate";
    public static final String SPRINT_END_DATE_NULL_MSG = "Sprint end date cannot be null";
    public static final String SPRINT_END_DATE_FUTURE_MSG = "Sprint end date has to be future date";

    private SprintConstants() {

    }
}
