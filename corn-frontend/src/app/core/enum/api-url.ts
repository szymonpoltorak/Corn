export enum ApiUrl {
    SPRINT_API_URL = '/api/v1/sprint',
    BACKLOG_ITEM_API_URL = '/api/v1/backlog/item',
    PROJECT_MEMBER_API_URL = '/api/v1/project/member',
    BACKLOG_ITEM_COMMENT_API_RUL = '/api/v1/backlog/comment',

    GET_BACKLOG_ITEMS_BY_SPRINT_ID = BACKLOG_ITEM_API_URL + '/getBySprint',
    GET_BACKLOG_ITEMS_WITHOUT_SPRINT = BACKLOG_ITEM_API_URL + '/getAllWithoutSprint',
    CREATE_BACKLOG_ITEM = BACKLOG_ITEM_API_URL + '/add',
    UPDATE_BACKLOG_ITEM = BACKLOG_ITEM_API_URL + '/update',
    DELETE_BACKLOG_ITEM = BACKLOG_ITEM_API_URL + '/delete',

    GET_SPRINTS_ON_PAGE = SPRINT_API_URL + '/getSprintsOnPage',
    GET_CURRENT_AND_FUTURE_SPRINTS = SPRINT_API_URL + '/currentAndFuture',
    GET_SPRINTS_BETWEEN_DATES = SPRINT_API_URL + '/getSprintsBetweenDates',
    DELETE_SPRINT = SPRINT_API_URL + '/deleteSprint',
    UPDATE_SPRINTS_NAME = SPRINT_API_URL + '/updateSprintsName',
    UPDATE_SPRINTS_START_DATE = SPRINT_API_URL + '/updateSprintsStartDate',
    UPDATE_SPRINTS_END_DATE = SPRINT_API_URL + '/updateSprintsEndDate',
    UPDATE_SPRINTS_DESCRIPTION = SPRINT_API_URL + '/updateSprintsDescription',
    CREATE_SPRINT = SPRINT_API_URL + '/addSprint',

    GET_PROJECT_MEMBERS = PROJECT_MEMBER_API_URL + '/getMembers',

    GET_COMMENTS_FOR_BACKLOG_ITEM = BACKLOG_ITEM_COMMENT_API_RUL + '/getForItem',
    UPDATE_COMMENT = BACKLOG_ITEM_COMMENT_API_RUL + '/update',
    CREATE_COMMENT = BACKLOG_ITEM_COMMENT_API_RUL + '/add',
    DELETE_COMMENT = BACKLOG_ITEM_COMMENT_API_RUL + '/delete',

}
