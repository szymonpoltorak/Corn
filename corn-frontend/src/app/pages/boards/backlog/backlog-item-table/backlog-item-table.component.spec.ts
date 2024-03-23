import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogItemTableComponent } from './backlog-item-table.component';

describe('BacklogItemTableComponent', () => {
  let component: BacklogItemTableComponent;
  let fixture: ComponentFixture<BacklogItemTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogItemTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacklogItemTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
