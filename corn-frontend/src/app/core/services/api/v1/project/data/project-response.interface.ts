import { SprintResponse } from "@core/services/api/v1/sprint/data/sprint-response.interface";

export interface ProjectResponse {
    name: string;
    sprints: SprintResponse[];
}