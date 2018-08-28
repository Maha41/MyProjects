import { AppState } from './../../../interfaces';
import { Store } from '@ngrx/store';
import { getTotalCartValue, getTotalCartItems } from './../../reducers/selectors';
import { Observable } from 'rxjs/Observable';
import { Order } from './../../../core/models/order';
import { CheckoutService } from './../../../core/services/checkout.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-delivery-options',
  templateUrl: './delivery-options.component.html',
  styleUrls: ['./delivery-options.component.scss']
})
export class DeliveryOptionsComponent implements OnInit {

  @Input() orderNumber;
  order;
  selectedShippingRate;
  shippingRates = [];
  totalCartValue$: Observable<number>;
  totalCartItems$: Observable<number>;
//constructor
  constructor(private checkoutService: CheckoutService, private store: Store<AppState>) {
    this.totalCartValue$ = this.store.select(getTotalCartValue);
    this.totalCartItems$ = this.store.select(getTotalCartItems);
  }

  ngOnInit() {
    // this.setOrder();
  }
//set order methods
  private setOrder() {
    this.checkoutService.getOrder(this.orderNumber)
      .subscribe((order) => {
        this.order = order;
        this.setShippingRates();
      });
  }
//setting shipping rates fpr products
  private setShippingRates() {
    this.shippingRates = this.order.shipments[0].shipping_rates;
    this.selectedShippingRate = this.order.shipments[0].selected_shipping_rate;
  }

}
