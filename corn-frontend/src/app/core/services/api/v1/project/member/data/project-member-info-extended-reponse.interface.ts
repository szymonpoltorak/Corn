import { User } from "@core/interfaces/boards/user";

export interface ProjectMemberInfoExtendedResponse {
    user: User,
    projectId: number,
    projectMemberId: number,
}
