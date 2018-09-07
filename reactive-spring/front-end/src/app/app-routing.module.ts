import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { StockComponent } from './stock/stock.component';
import { StockFuncComponent } from './stock-func/stock-func.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'stock', component: StockComponent },
  { path: 'stock-func', component: StockFuncComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
