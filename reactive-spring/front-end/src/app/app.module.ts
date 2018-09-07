import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpModule} from '@angular/http';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { StockComponent } from './stock/stock.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { StockFuncComponent } from './stock-func/stock-func.component';

@NgModule({
  declarations: [
    AppComponent,
    StockComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    StockFuncComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    CommonModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
