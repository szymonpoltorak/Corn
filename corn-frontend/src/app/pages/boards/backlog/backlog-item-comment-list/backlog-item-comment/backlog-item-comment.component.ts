import { Component, Input, OnInit } from '@angular/core';
import { BacklogItemComment } from "@interfaces/boards/backlog/backlog-item-comment";
import { UserAvatarComponent } from "@pages/utils/user-avatar/user-avatar.component";
import { MatError, MatFormField, MatHint, MatSuffix } from "@angular/material/form-field";
import { CdkTextareaAutosize } from "@angular/cdk/text-field";
import { MatInput } from "@angular/material/input";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { CustomValidators } from "@core/validators/custom-validators";
import { MatButton, MatIconButton } from "@angular/material/button";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { matDelete, matDone, matEdit } from "@ng-icons/material-icons/baseline";
import { NgIf } from "@angular/common";
import { MatTooltip } from "@angular/material/tooltip";
import { MatMenu, MatMenuItem, MatMenuTrigger } from "@angular/material/menu";
import { MatIcon } from "@angular/material/icon";

@Component({
    selector: 'app-backlog-item-comment',
    standalone: true,
    imports: [
        UserAvatarComponent,
        MatFormField,
        CdkTextareaAutosize,
        MatInput,
        ReactiveFormsModule,
        MatHint,
        MatIconButton,
        MatSuffix,
        NgIcon,
        NgIf,
        MatTooltip,
        MatMenuTrigger,
        MatIcon,
        MatMenu,
        MatMenuItem,
        MatButton,
        MatError,
    ],
    templateUrl: './backlog-item-comment.component.html',
    styleUrl: './backlog-item-comment.component.scss',
    providers: [provideIcons({ matEdit, matDone, matDelete })],
})
export class BacklogItemCommentComponent implements OnInit {

    @Input() comment!: BacklogItemComment;

    isEditing: boolean = false;
    edited = false;


    commentControl!: FormControl;

    ngOnInit(): void {
        this.comment.commentTime = new Date(this.comment.commentTime);
        this.comment.lastEditTime = new Date(this.comment.lastEditTime);

        this.edited = this.comment.commentTime.getTime() !== this.comment.lastEditTime.getTime();

        this.commentControl =  new FormControl(this.comment.comment,
            [Validators.required, CustomValidators.notWhitespace()]);
    }

    canEdit(): boolean {
      //TODO acquire real information about user being owner of the comment or owner of the project
      return true;
    }

    deleteComment(): void {
        //TODO implement delete comment
    }

    formatDate(date: Date): string {
        if(!date) {
            return "";
        }

        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based in JavaScript
        const year = date.getFullYear();

        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        return `${day}-${month}-${year} ${hours}:${minutes}`;
    }
}