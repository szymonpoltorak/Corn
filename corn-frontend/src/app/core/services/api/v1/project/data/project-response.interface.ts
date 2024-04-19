import { SprintResponse } from "../../sprint/data/sprint-response.interface";

export interface ProjectResponse {
    name: string;
    sprints: SprintResponse[];
}