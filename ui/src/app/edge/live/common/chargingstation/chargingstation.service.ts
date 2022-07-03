import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

const URL = 'http://192.168.0.183';
@Injectable({
  providedIn: 'root',
})
export class ChargingStationService {
  constructor(private http: HttpClient) {}

  public gets(url): Observable<any> {
    return this.http.get(URL + '/' + url).pipe(map((res) => res));
  }
}
