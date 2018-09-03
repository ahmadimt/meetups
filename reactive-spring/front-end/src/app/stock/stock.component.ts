import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { DataService } from '../data.service';
import { HttpClient } from '@angular/common/http';
import * as CanvasJS from '../canvas/canvasjs.min';
import { MovieEvent } from '../model/movie.event';
import { DateNumber } from '../model/date.number.event';
import { Subscription } from 'rxjs';
@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.scss']
})
export class StockComponent implements OnInit {
  movieEvents: MovieEvent[];
  dateNumber: DateNumber[];
  movieEventsObservable: Subscription;
  dateNumberObservable: Subscription;
  movieId: string;
  dataPoints = [];
  chart;
  constructor(private alertService: DataService, private cdr: ChangeDetectorRef,
    private http: HttpClient) { }

  ngOnInit() {

    /* this.http.get('http://localhost:8100/movies/randomnumber/time')
      .subscribe((data) => { console.log(data); }
        , err => {
          console.log(err);
        }, () => {
          console.log("Done");
        }); */
    const dpsLength = 0;
    this.chart = new CanvasJS.Chart('chartContainer', {
      exportEnabled: true,
      title: {
        text: 'Live Chart with Data-Points from External JSON'
      },
      data: [{
        type: 'spline',
        dataPoints: this.dataPoints,
      }]
    });
    this.getDateNumberEvents();
  }

  updateChart(dateNumber: DateNumber) {
    this.dataPoints.push({
      x: dateNumber.date,
      y: dateNumber.no
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
    this.dateNumberObservable = this.alertService.getDateAndNumber().subscribe(events => {
      // this.dateNumber = events;
      this.updateChart(events[events.length - 1]);
    });
  }

  getMovieEvents() {
    if (this.movieEventsObservable && !this.movieEventsObservable.closed) {
      this.movieEventsObservable.unsubscribe();
    }
    this.movieEventsObservable = this.alertService.getMovieEvents(this.movieId).subscribe(events => {
      this.movieEvents = events;
      this.cdr.detectChanges();
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
}
