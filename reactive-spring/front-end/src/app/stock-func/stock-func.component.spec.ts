import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StockFuncComponent } from './stock-func.component';

describe('StockFuncComponent', () => {
  let component: StockFuncComponent;
  let fixture: ComponentFixture<StockFuncComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StockFuncComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StockFuncComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
