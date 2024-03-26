import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogTypeComponent } from './backlog-type.component';

describe('BacklogTypeComponent', () => {
  let component: BacklogTypeComponent;
  let fixture: ComponentFixture<BacklogTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogTypeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacklogTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
