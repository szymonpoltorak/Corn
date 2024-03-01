export interface Assignee {
    firstName: string,
    familyName: string,
    avatarUrl: string,
}

export const SAMPLE_ASSIGNEES = {
    JOHN_DOE: {
        firstName: "John",
        familyName: "Doe",
        avatarUrl: "/assets/assignee-avatars/john.png",
    },
    JANE_DOE: {
        firstName: "Jane",
        familyName: "Doe",
        avatarUrl: "/assets/assignee-avatars/jane.png",
    },
    ALICE_SMITH: {
        firstName: "Alice",
        familyName: "Smith",
        avatarUrl: "/assets/assignee-avatars/alice.png",
    },
    BOB_JOHNSON: {
        firstName: "Bob",
        familyName: "Johnson",
        avatarUrl: "/assets/assignee-avatars/bob.png",
    },
    CHARLIE_BROWN: {
        firstName: "Charlie",
        familyName: "Brown",
        avatarUrl: "/assets/assignee-avatars/charlie.png",
    },
    DIANA_MILLER: {
        firstName: "Diana",
        familyName: "Miller",
        avatarUrl: "/assets/assignee-avatars/diana.png",
    },
}

export interface Task {
    taskid: string;
    content: string;
    assignee: Assignee
}

export const SAMPLE_TASKS = {
    TODO: [
        {
            taskid: "DEV-1",
            content: "Buy groceries",
            assignee: SAMPLE_ASSIGNEES.JOHN_DOE,
        },
        {
            taskid: "DEV-2",
            content: "Read a book",
            assignee: SAMPLE_ASSIGNEES.JANE_DOE,
        },
        {
            taskid: "DEV-3",
            content: "Write code",
            assignee: SAMPLE_ASSIGNEES.ALICE_SMITH,
        },
        {
            taskid: "DEV-10",
            content: "Implement new feature",
            assignee: SAMPLE_ASSIGNEES.BOB_JOHNSON,
        },
        {
            taskid: "DEV-11",
            content: "Review and refactor code",
            assignee: SAMPLE_ASSIGNEES.JANE_DOE,
        },
    ],
    INPROGRESS: [
        {
            taskid: "DEV-4",
            content: "Design a website",
            assignee: SAMPLE_ASSIGNEES.BOB_JOHNSON,
        },
        {
            taskid: "DEV-7",
            content: "Clean the house",
            assignee: SAMPLE_ASSIGNEES.JOHN_DOE,
        },
        {
            taskid: "DEV-8",
            content: "Exercise for 30 minutes",
            assignee: SAMPLE_ASSIGNEES.JANE_DOE,
        },
        {
            taskid: "DEV-9",
            content: "Plan the week's schedule",
            assignee: SAMPLE_ASSIGNEES.ALICE_SMITH,
        },
    ],
    DONE: [
        {
            taskid: "DEV-5",
            content: "Finish project",
            assignee: SAMPLE_ASSIGNEES.CHARLIE_BROWN,
        },
        {
            taskid: "DEV-6",
            content: "Submit report",
            assignee: SAMPLE_ASSIGNEES.DIANA_MILLER,
        },
        {
            taskid: "DEV-12",
            content: "Publish blog post",
            assignee: SAMPLE_ASSIGNEES.JANE_DOE,
        },
        {
            taskid: "DEV-13",
            content: "Prepare presentation",
            assignee: SAMPLE_ASSIGNEES.DIANA_MILLER,
        },
    ],
}