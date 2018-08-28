import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductDescriptionComponent } from './product-description.component';
//
describe('ProductDescriptionComponent', () => {
  let component: ProductDescriptionComponent;
  let fixture: ComponentFixture<ProductDescriptionComponent>;
//async call for products iteration
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductDescriptionComponent ]
    })
    .compileComponents();
  }));
//performing initial task for the product compoenent
  beforeEach(() => {
    fixture = TestBed.createComponent(ProductDescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
//Promise call  
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
