import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import {NgIcon, provideIcons} from "@ng-icons/core";
import {heroUserCircleSolid} from "@ng-icons/heroicons/solid";
import {bootstrapArchiveFill} from "@ng-icons/bootstrap-icons";
import {octGoal, octGraph} from "@ng-icons/octicons";
import {akarClipboard} from "@ng-icons/akar-icons";
import {matError, matViewTimeline} from "@ng-icons/material-icons/baseline";

@Component({
    selector: 'sidebar-button',
    standalone: true,
    imports: [
        MatIcon,
        CommonModule,
        NgIcon
    ],
    templateUrl: './sidebar-button.component.html',
    viewProviders: [provideIcons({
        bootstrapArchiveFill,
        octGraph,
        octGoal,
        akarClipboard,
        matViewTimeline,
        matError})]
})
export class SidebarButton {
    @Input() iconName: string = '';
    @Input() label: string = '';
    @Input() click: () => void = () => { };
    @Input() selected: boolean = false;
}