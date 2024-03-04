import { Component } from '@angular/core';

@Component({
    selector: 'column',
    standalone: true,
    template: `
    <div class="
        grid
        w-[320px]
        border-solid border-[3px] rounded-md border-dark-color-settings-container-background
    "><ng-content></ng-content></div>
    `,
})
export class ColumnLayout { }
