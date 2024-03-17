import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogFormComponent } from './backlog-form.component';

describe('BacklogFormComponent', () => {
  let component: BacklogFormComponent;
  let fixture: ComponentFixture<BacklogFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacklogFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
