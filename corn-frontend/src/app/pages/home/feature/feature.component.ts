import { Component, Input } from '@angular/core';
import { MatTab } from "@angular/material/tabs";
import { Feature } from "@core/interfaces/home/feature.interface";
import { NgOptimizedImage } from "@angular/common";

@Component({
    selector: 'app-feature',
    standalone: true,
    imports: [
        MatTab,
        NgOptimizedImage
    ],
    templateUrl: './feature.component.html',
    styleUrl: './feature.component.scss'
})
export class FeatureComponent {
    @Input() feature !: Feature;
}
