import { Component } from '@angular/core';
import {
    CdkDragDrop,
    moveItemInArray,
    transferArrayItem,
    CdkDrag,
    CdkDropList,
} from '@angular/cdk/drag-drop';
import { TaskcardComponent } from './taskcard/taskcard.component';

@Component({
    selector: 'app-board',
    standalone: true,
    imports: [CdkDropList, CdkDrag, TaskcardComponent],
    templateUrl: './board.component.html',
})
export class BoardComponent {

    todo: Task[] = [
        {
            content: "Buy groceries",
            taskid: "t1",
            assigneeAvatar: "/assets/corn-icons/icon-72x72.png",
            assigneeName: "John Doe"
        },
        {
            content: "Read a book",
            taskid: "t2",
            assigneeAvatar: "/assets/corn-icons/icon-72x72.png",
            assigneeName: "Jane Doe"
        },
        {
            content: "Write code",
            taskid: "t3",
            assigneeAvatar: "/assets/corn-icons/icon-72x72.png",
            assigneeName: "Alice Smith"
        }
    ];
    
    inprogress: Task[] = [
        {
            content: "Design a website",
            taskid: "t4",
            assigneeAvatar: "/assets/corn-icons/icon-72x72.png",
            assigneeName: "Bob Johnson"
        }
    ];
    
    done: Task[] = [
        {
            content: "Finish project",
            taskid: "t5",
            assigneeAvatar: "/assets/corn-icons/icon-72x72.png",
            assigneeName: "Charlie Brown"
        },
        {
            content: "Submit report",
            taskid: "t6",
            assigneeAvatar: "/assets/corn-icons/icon-72x72.png",
            assigneeName: "Diana Miller"
        }
    ];

    drop2(event: CdkDragDrop<Task[]>) {
        if (event.previousContainer === event.container) {
            moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
        } else {
            transferArrayItem(
                event.previousContainer.data,
                event.container.data,
                event.previousIndex,
                event.currentIndex,
            );
        }
    }

}

interface Task {
    content: string;
    taskid: string;
    assigneeAvatar: string;
    assigneeName: string;
}