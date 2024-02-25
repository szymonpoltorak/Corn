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

@Component({
    selector: 'columnset',
    standalone: true,
    imports: [
        ColumnLayout
    ],
    template: `
        <div class="flex gap-[24px]">
            <column>
                <ng-content select="[todo]"></ng-content>
            </column>
            <column>
                <ng-content select="[inprogress]"></ng-content>
            </column>
            <column>
                <ng-content select="[done]"></ng-content>
            </column>
        </div>
    `,
})
export class ColumnSetLayout { }