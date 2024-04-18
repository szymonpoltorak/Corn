import { TestBed } from '@angular/core/testing';

import { BacklogItemCommentService } from './backlog-item-comment.service';

describe('BacklogItemCommentService', () => {
  let service: BacklogItemCommentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BacklogItemCommentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
