import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryOptionsComponent } from './delivery-options.component';

describe('DeliveryOptionsComponent', () => {
  let component: DeliveryOptionsComponent;
  let fixture: ComponentFixture<DeliveryOptionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeliveryOptionsComponent ]
    })
    .compileComponents();
  }));
//iterating through delivery options component to create a component
  beforeEach(() => {
    fixture = TestBed.createComponent(DeliveryOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
