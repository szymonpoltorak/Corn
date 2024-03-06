export const ASSIGNEES = {
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

export const TASKS = {
    TODO: [
        {
            taskid: "DEV-1",
            content: "Buy groceries",
            assignee: ASSIGNEES.JOHN_DOE,
        },
        {
            taskid: "DEV-2",
            content: "Read a book",
            assignee: ASSIGNEES.JANE_DOE,
        },
        {
            taskid: "DEV-3",
            content: "Write code",
            assignee: ASSIGNEES.ALICE_SMITH,
        },
        {
            taskid: "DEV-10",
            content: "Implement new feature",
            assignee: ASSIGNEES.BOB_JOHNSON,
        },
        {
            taskid: "DEV-11",
            content: "Review and refactor code",
            assignee: ASSIGNEES.JANE_DOE,
        },
    ],
    INPROGRESS: [
        {
            taskid: "DEV-4",
            content: "Design a website",
            assignee: ASSIGNEES.BOB_JOHNSON,
        },
        {
            taskid: "DEV-7",
            content: "Clean the house",
            assignee: ASSIGNEES.JOHN_DOE,
        },
        {
            taskid: "DEV-8",
            content: "Exercise for 30 minutes",
            assignee: ASSIGNEES.JANE_DOE,
        },
        {
            taskid: "DEV-9",
            content: "Plan the week's schedule",
            assignee: ASSIGNEES.ALICE_SMITH,
        },
    ],
    DONE: [
        {
            taskid: "DEV-5",
            content: "Finish project",
            assignee: ASSIGNEES.CHARLIE_BROWN,
        },
        {
            taskid: "DEV-6",
            content: "Submit report",
            assignee: ASSIGNEES.DIANA_MILLER,
        },
        {
            taskid: "DEV-12",
            content: "Publish blog post",
            assignee: ASSIGNEES.JANE_DOE,
        },
        {
            taskid: "DEV-13",
            content: "Prepare presentation",
            assignee: ASSIGNEES.DIANA_MILLER,
        },
    ],
}