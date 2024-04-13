export enum ApiUrl {
    PROJECT_API_URL = '/api/v1/project',
    SPRINT_API_URL = '/api/v1/sprint',
    BACKLOG_ITEM_API_URL = '/api/v1/backlog/item',
    PROJECT_MEMBER_API_URL = '/api/v1/project/assignee',

    GET_PROJECTS_ON_PAGE = PROJECT_API_URL + '/getProjectsOnPage',

    GET_BACKLOG_ITEMS_BY_PROJECT_ID = BACKLOG_ITEM_API_URL + '/getByProject',
    GET_BACKLOG_ITEMS_BY_SPRINT_ID = BACKLOG_ITEM_API_URL + '/getBySprint',
    GET_BACKLOG_ITEMS_WITHOUT_SPRINT = BACKLOG_ITEM_API_URL + '/getAllWithoutSprint',
    CREATE_BACKLOG_ITEM = BACKLOG_ITEM_API_URL + '/add',
    UPDATE_BACKLOG_ITEM = BACKLOG_ITEM_API_URL + '/update',
    DELETE_BACKLOG_ITEM = BACKLOG_ITEM_API_URL + '/delete',

    GET_SPRINTS_ON_PAGE = SPRINT_API_URL + '/getSprintsOnPage',
    GET_CURRENT_AND_FUTURE_SPRINTS = SPRINT_API_URL + '/currentAndFuture',

    GET_PROJECT_MEMBERS = PROJECT_MEMBER_API_URL + '/getMembers'
}
