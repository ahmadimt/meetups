import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import * as M from 'materialize-css';
import { Subscription } from 'rxjs';
import * as CanvasJS from '../canvas/canvasjs.min';
import { DateNumber } from '../model/date.number.event';
import { MovieEvent } from '../model/movie.event';
import { Stock } from '../model/stock';
import { StockService } from './stock.service';
@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.scss']
})
export class StockComponent implements OnInit {
  stock: any = {};
  movieEvents: MovieEvent[];
  dateNumber: DateNumber[];
  movieEventsObservable: Subscription;
  dateNumberObservable: Subscription;
  movieId: string;
  dataPoints = [];
  chart;
  priceSaveForm: FormGroup;
  priceUpdateForm: FormGroup;
  deleteStockForm: FormGroup;
  constructor(private cdr: ChangeDetectorRef,
    private stockService: StockService) { }

  ngOnInit() {
    const dpsLength = 0;
    this.chart = new CanvasJS.Chart('chartContainer', {
      exportEnabled: true,
      title: {
        text: 'Meetup Stock price details'
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

  updateChart(stock: Stock) {
    this.dataPoints.push({
      x: stock.time,
      y: stock.price
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
