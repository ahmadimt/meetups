import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import * as M from 'materialize-css';
import { Subscription } from 'rxjs';
import * as CanvasJS from '../canvas/canvasjs.min';
import { Stock } from '../model/stock';
import { StockFuncService } from './stock-func.service';

@Component({
  selector: 'app-stock-func',
  templateUrl: './stock-func.component.html',
  styleUrls: ['./stock-func.component.scss']
})
export class StockFuncComponent implements OnInit {

  stock: any = {};
  movieEventsObservable: Subscription;
  dateNumberObservable: Subscription;
  movieId: string;
  dataPoints = [];
  chart;
  priceSaveForm: FormGroup;
  priceUpdateForm: FormGroup;
  deleteStockForm: FormGroup;
  constructor(private cdr: ChangeDetectorRef,
    private stockService: StockFuncService) { }

  ngOnInit() {
    const dpsLength = 0;
    this.chart = new CanvasJS.Chart('chartContainer', {
      exportEnabled: true,
      title: {
        text: 'Meetup Stock price details (Functional)'
      },
      data: [{
        type: 'spline',
        dataPoints: this.dataPoints,
      }]
    });
    this.getDateNumberEvents();
    this.priceSaveForm = new FormGroup({
      time: new FormControl(''),
      price: new FormControl('')
    });
    this.priceUpdateForm = new FormGroup({
      time: new FormControl(''),
      price: new FormControl('')
    });
    this.deleteStockForm = new FormGroup({
      time: new FormControl(''),
      price: new FormControl('')
    });
  }

  updateChart(dateNumber: Stock) {
    this.dataPoints.push({
      x: dateNumber.time,
      y: dateNumber.price
    });

    // if (this.dataPoints.length >  20 ) {
    //   		this.dataPoints.shift();
    //   	}
    this.chart.render();
  }

  getDateNumberEvents() {
    if (this.dateNumberObservable && !this.dateNumberObservable.closed) {
      this.dateNumberObservable.unsubscribe();
    }
    this.dateNumberObservable = this.stockService.getStockDetails().subscribe(events => {
      // this.dateNumber = events;
      this.updateChart(events[events.length - 1]);
    });
  }

  closeObservable() {
    if (this.movieEventsObservable) {
      this.movieEventsObservable.unsubscribe();
    }
    if (this.dateNumberObservable) {
      this.dateNumberObservable.unsubscribe();
    }
  }
  saveStockDetails() {
    console.log('Saving stock details');
    this.stock['time'] = this.priceSaveForm.get('time').value;
    this.stock['price'] = this.priceSaveForm.get('price').value;
    this.stockService.saveStock(this.stock).subscribe(data => console.log(data),
      err => { console.log(err); },
      () => { M.toast({ html: 'Stock details saved successfully.', classes: 'rounded green', completeCallback: function () { } }); });
    console.log('price object', this.stock);
  }
  updateStockDetails() {
    console.log('Updating stock details');
    this.stock['time'] = this.priceUpdateForm.get('time').value;
    this.stock['price'] = this.priceUpdateForm.get('price').value;
    this.stockService.saveStock(this.stock).subscribe(data => console.log(data),
      err => { console.log(err); },
      () => { M.toast({ html: 'Stock details updated successfully.', classes: 'rounded green', completeCallback: function () { } }); });
    console.log('price object', this.stock);
  }

  deleteStockDetails() {
    console.log('Deleting stock details');
    this.stock['time'] = this.deleteStockForm.get('time').value;
    this.stockService.deleteStock(this.stock['time']).subscribe(data => console.log(data),
      err => { console.log(err); },
      () => { M.toast({ html: 'Stock deleted successfully.', classes: 'rounded red', completeCallback: function () { } }); });
  }
}
