import { Address } from './../../../core/models/address';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-delivery-address',
  templateUrl: './delivery-address.component.html',
  styleUrls: ['./delivery-address.component.scss']
})
export class DeliveryAddressComponent implements OnInit {
//fetching the values
  @Input() address: Address;

  constructor() { }

  ngOnInit() {
  }

}
