import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private http: HttpClient) { }

  saveStock(stock): Observable<any> {
    return this.http.post('http://localhost:8100/stock', stock);
  }
}
