import { TestBed } from '@angular/core/testing';

import { BacklogItemService } from './backlog-item.service';

describe('BacklogItemService', () => {
  let service: BacklogItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BacklogItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
