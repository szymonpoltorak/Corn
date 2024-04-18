import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogItemDetailsComponent } from './backlog-item-details.component';

describe('BacklogItemDetailsComponent', () => {
  let component: BacklogItemDetailsComponent;
  let fixture: ComponentFixture<BacklogItemDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogItemDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacklogItemDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
