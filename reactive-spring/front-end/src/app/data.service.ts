import { Injectable } from '@angular/core';
import * as EventSource from '../../node_modules/eventsource/lib/eventsource';
import { Observable } from 'rxjs';
import { DateNumber } from './model/date.number.event';
import { MovieEvent } from './model/movie.event';

@Injectable({
    providedIn: 'root'
})
export class DataService {
    private dateNumber: DateNumber[] = new Array();
    private movieEvents: MovieEvent[] = new Array();

    constructor() { }

    getMovieEvents(id): Observable<MovieEvent[]> {
        this.movieEvents = new Array();

        return Observable.create((observer) => {
            const eventSource = new EventSource('http://localhost:8100/movies/' + id + '/events');
            eventSource.onmessage = (event) => {
                const json = JSON.parse(event.data);
                this.movieEvents.push(new MovieEvent(json['movie']['name'], json['movie']['genre'], json['user'], json['date']));
                observer.next(this.movieEvents);
            };
            eventSource.onerror = error => observer.error('eventSource.onerror: ' + error);

            return () => eventSource.close();
        });
    }

    getDateAndNumber(): Observable<DateNumber[]> {
        this.dateNumber = new Array();

        return Observable.create((observer) => {
            const eventSource = new EventSource('http://localhost:8100/movies/randomnumber/time');
            eventSource.onmessage = (event) => {
                const json = JSON.parse(event.data);
                this.dateNumber.push(new DateNumber(json['time'], json['number']));
                observer.next(this.dateNumber);
            };
            eventSource.onerror = error => observer.error('eventSource.onerror: ' + error);

            return () => eventSource.close();
        });
    }
}
