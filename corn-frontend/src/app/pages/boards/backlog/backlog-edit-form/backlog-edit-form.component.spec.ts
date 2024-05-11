import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogEditFormComponent } from './backlog-edit-form.component';

describe('BacklogEditFormComponent', () => {
  let component: BacklogEditFormComponent;
  let fixture: ComponentFixture<BacklogEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogEditFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacklogEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
