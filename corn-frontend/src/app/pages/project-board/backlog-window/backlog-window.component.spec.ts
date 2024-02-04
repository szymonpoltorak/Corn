import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogWindowComponent } from './backlog-window.component';

describe('BacklogWindowComponent', () => {
  let component: BacklogWindowComponent;
  let fixture: ComponentFixture<BacklogWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogWindowComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacklogWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
