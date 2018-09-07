import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DateNumber } from '../model/date.number.event';

@Injectable({
  providedIn: 'root'
})
export class StockService {
  private dateNumber: DateNumber[] = new Array();
  constructor(private http: HttpClient) { }

  saveStock(stock): Observable<any> {
    return this.http.post('http://localhost:8100/stock', stock);
  }
  deleteStock(stock): Observable<any> {
    return this.http.delete('http://localhost:8100/stock/' + stock);
  }

  getDateAndNumber(): Observable<any> {
    this.dateNumber = new Array();
    return Observable.create((observer) => {
      const eventSource = new EventSource('http://localhost:8100/stock');
      eventSource.onmessage = (event) => {
        const json = JSON.parse(event.data);
        this.dateNumber.push(new DateNumber(json['time'], json['price']));
        observer.next(this.dateNumber);
      };
      eventSource.onerror = error => observer.error('eventSource.onerror: ' + error);

      return () => eventSource.close();
    });
  }
}
