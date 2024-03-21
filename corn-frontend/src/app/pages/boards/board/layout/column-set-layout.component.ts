import { Component } from '@angular/core';
import { ColumnLayout } from './column-set.component';

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