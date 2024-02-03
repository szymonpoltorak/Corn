import { Component, Input } from '@angular/core';
import { MatTab } from "@angular/material/tabs";
import { Feature } from "@core/interfaces/home/feature.interface";

@Component({
    selector: 'app-feature',
    standalone: true,
    imports: [
        MatTab
    ],
    templateUrl: './feature.component.html',
    styleUrl: './feature.component.scss'
})
export class FeatureComponent {
    @Input() feature !: Feature;
}
