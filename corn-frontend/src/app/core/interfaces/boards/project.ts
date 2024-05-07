import { User } from "@interfaces/boards/user";

export interface Project {
    projectId: number
    name: string
    totalNumberOfUsers: number,
    membersInfo: User[],
    isOwner: boolean
}