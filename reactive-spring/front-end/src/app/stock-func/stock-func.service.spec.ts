import { TestBed, inject } from '@angular/core/testing';

import { StockFuncService } from './stock-func.service';

describe('StockFuncService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StockFuncService]
    });
  });

  it('should be created', inject([StockFuncService], (service: StockFuncService) => {
    expect(service).toBeTruthy();
  }));
});
