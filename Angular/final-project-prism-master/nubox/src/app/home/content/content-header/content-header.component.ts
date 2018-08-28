import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-content-header',
  templateUrl: './content-header.component.html',
  styleUrls: ['./content-header.component.scss']
})

//Created an event emiter to emit the selected size and pass it to the parent component 
export class ContentHeaderComponent implements OnInit {
  @Output() toggleSize = new EventEmitter();
  selectedSize: string = 'COZY';

  constructor() { }

  ngOnInit() {
  }

  //selects the toogleview and emits it to the parent component
  toggleView(view) {
    this.selectedSize = view;
    this.toggleSize.emit({size: view});
  }
//funtions used in the html to return true for the sleected size
  isSmallSelected(): boolean {
    return this.selectedSize === 'COZY';
  }
//funtions used in the html to return true for the sleected size compact
  isBigSelected(): boolean {
    return this.selectedSize === 'COMPACT';
  }

}
