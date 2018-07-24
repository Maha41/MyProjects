import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { BestScoreManager } from './app.storage.service';
import { AppComponent } from './app.component';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [
    BestScoreManager
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
