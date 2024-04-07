package dev.corn.cornbackend.api.sprint.constants;

public final class SprintMappings {
    public static final String SPRINT_API_ENDPOINT = "/api/v1/sprint";

    public static final String GET_SPRINTS_ON_PAGE = "/getSprintsOnPage";

    public static final String GET_SPRINT_BY_ID = "/getSprintById";

    public static final String ADD_SPRINT = "/addSprint";

    public static final String UPDATE_SPRINTS_NAME = "/updateSprintsName";

    public static final String UPDATE_SPRINTS_DESCRIPTION = "/updateSprintsDescription";

    public static final String UPDATE_SPRINTS_START_DATE = "/updateSprintsStartDate";

    public static final String UPDATE_SPRINTS_END_DATE = "/updateSprintsEndDate";

    public static final String DELETE_SPRINT = "/deleteSprint";

    public static final String CURRENT_AND_FUTURE_SPRINTS = "/currentAndFuture";

    private SprintMappings() {
    }
}
