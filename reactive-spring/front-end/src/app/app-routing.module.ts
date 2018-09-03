import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { StockComponent } from './stock/stock.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'stock', component: StockComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
