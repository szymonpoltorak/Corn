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
            taskid: "t1",
            content: "Buy groceries",
            assignee: SAMPLE_ASSIGNEES.JOHN_DOE,
        },
        {
            taskid: "t2",
            content: "Read a book",
            assignee: SAMPLE_ASSIGNEES.JANE_DOE,
        },
        {
            taskid: "t3",
            content: "Write code",
            assignee: SAMPLE_ASSIGNEES.ALICE_SMITH,
        },
        {
            taskid: "t10",
            content: "Implement new feature",
            assignee: SAMPLE_ASSIGNEES.BOB_JOHNSON,
        },
        {
            taskid: "t11",
            content: "Review and refactor code",
            assignee: SAMPLE_ASSIGNEES.JANE_DOE,
        },
    ],
    INPROGRESS: [
        {
            taskid: "t4",
            content: "Design a website",
            assignee: SAMPLE_ASSIGNEES.BOB_JOHNSON,
        },
        {
            taskid: "t7",
            content: "Clean the house",
            assignee: SAMPLE_ASSIGNEES.JOHN_DOE,
        },
        {
            taskid: "t8",
            content: "Exercise for 30 minutes",
            assignee: SAMPLE_ASSIGNEES.JANE_DOE,
        },
        {
            taskid: "t9",
            content: "Plan the week's schedule",
            assignee: SAMPLE_ASSIGNEES.ALICE_SMITH,
        },
    ],
    DONE: [
        {
            taskid: "t5",
            content: "Finish project",
            assignee: SAMPLE_ASSIGNEES.CHARLIE_BROWN,
        },
        {
            taskid: "t6",
            content: "Submit report",
            assignee: SAMPLE_ASSIGNEES.DIANA_MILLER,
        },
        {
            taskid: "t12",
            content: "Publish blog post",
            assignee: SAMPLE_ASSIGNEES.JANE_DOE,
        },
        {
            taskid: "t13",
            content: "Prepare presentation",
            assignee: SAMPLE_ASSIGNEES.DIANA_MILLER,
        },
    ],
}