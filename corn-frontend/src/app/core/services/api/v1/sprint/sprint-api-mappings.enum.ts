export enum SprintApiMappings {
    ROOT = "/api/v1/sprint",
    GET_SPRINTS_ON_PAGE = ROOT + "/getSprintsOnPage",
    GET_SPRINT_BY_ID = ROOT + "/getSprintById",
    ADD_SPRINT = ROOT + "/addSprint",
    UPDATE_SPRINTS_NAME = ROOT + "/updateSprintsName",
    UPDATE_SPRINTS_DESCRIPTION = ROOT + "/updateSprintsDescription",
    UPDATE_SPRINTS_START_DATE = ROOT + "/updateSprintsStartDate",
    UPDATE_SPRINTS_END_DATE = ROOT + "/updateSprintsEndDate",
    DELETE_SPRINT = ROOT + "/deleteSprint",
    CURRENT_AND_FUTURE_SPRINTS = ROOT + "/currentAndFuture",
    SPRINTS_AFTER_SPRINT = ROOT + "/getSprintsAfterSprint",
    SPRINTS_BEFORE_SPRINT = ROOT + "/getSprintsBeforeSprint",
}