import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerControl } from './customer-control';

describe('CustomerControl', () => {
  let component: CustomerControl;
  let fixture: ComponentFixture<CustomerControl>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerControl]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerControl);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
