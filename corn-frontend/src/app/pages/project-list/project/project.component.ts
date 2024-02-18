import {Component, Input} from '@angular/core';
import {MatRipple} from "@angular/material/core";
import {NgOptimizedImage, SlicePipe} from "@angular/common";
import {MatTooltip} from "@angular/material/tooltip";

@Component({
  selector: 'app-project',
  standalone: true,
  imports: [
    MatRipple,
    SlicePipe,
    NgOptimizedImage,
    MatTooltip
  ],
  templateUrl: './project.component.html',
  styleUrl: './project.component.scss'
})
export class ProjectComponent {

  @Input() title: string = '';
  @Input() members: string[] = [];

  getInitials(member: string): string {
    return member.split(' ').map(name => name[0]).join('');
  }
}
