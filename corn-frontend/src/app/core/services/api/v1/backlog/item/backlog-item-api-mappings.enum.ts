export enum BacklogItemApiMappings {
    ROOT = '/api/v1/backlog/item',
    ADD = ROOT + '/add',
    UPDATE = ROOT + '/update',
    DELETE= ROOT + '/delete',
    GET = ROOT + '/get',
    GET_BY_SPRINT = ROOT + '/getBySprint',
    GET_BY_PROJECT = ROOT + '/getByProject',
    GET_DETAILS = ROOT + '/getDetails',
    GET_ALL_WITHOUT_SPRINT = ROOT + '/getAllWithoutSprint',
    BACKLOG_ITEM_GET_ALL_BY_SPRINT_MAPPING = ROOT + '/getAllBySprint',
    PARTIAL_UPDATE = ROOT + '/partialUpdate',
}
