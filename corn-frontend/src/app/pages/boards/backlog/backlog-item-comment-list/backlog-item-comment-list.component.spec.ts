import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogItemCommentListComponent } from './backlog-item-comment-list.component';

describe('BacklogItemCommentListComponent', () => {
  let component: BacklogItemCommentListComponent;
  let fixture: ComponentFixture<BacklogItemCommentListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacklogItemCommentListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacklogItemCommentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
