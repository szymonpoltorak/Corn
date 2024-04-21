import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogItemCommentComponent } from './backlog-item-comment.component';

describe('BacklogItemCommentComponent', () => {
  let component: BacklogItemCommentComponent;
  let fixture: ComponentFixture<BacklogItemCommentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogItemCommentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacklogItemCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
