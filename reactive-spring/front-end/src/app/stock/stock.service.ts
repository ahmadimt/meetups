import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Stock } from '../model/stock';

@Injectable({
  providedIn: 'root'
})
export class StockService {
  private stocks: Stock[] = new Array();
  constructor(private http: HttpClient) { }

  saveStock(stock): Observable<any> {
    return this.http.post('http://localhost:8100/stock', stock);
  }
  deleteStock(stock): Observable<any> {
    return this.http.delete('http://localhost:8100/stock/' + stock);
  }

  getStockDetails(): Observable<any> {
    this.stocks = new Array();
    return Observable.create((observer) => {
      const eventSource = new EventSource('http://localhost:8100/stock');
      eventSource.onmessage = (event) => {
        const json = JSON.parse(event.data);
        this.stocks.push(new Stock(json['time'], json['price']));
        observer.next(this.stocks);
      };
      eventSource.onerror = error => observer.error('eventSource.onerror: ' + error);

      return () => eventSource.close();
    });
  }
}
