import { Assignee } from "@core/interfaces/boards/board/assignee.interface";

export type UsernameToAssigneeMapper = (username: string) => Assignee;