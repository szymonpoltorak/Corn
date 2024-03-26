import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogDragComponent } from './backlog-drag.component';

describe('BacklogDragComponent', () => {
  let component: BacklogDragComponent;
  let fixture: ComponentFixture<BacklogDragComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogDragComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacklogDragComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
