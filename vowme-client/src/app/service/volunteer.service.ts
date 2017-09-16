import { Observable } from 'rxjs/Observable';
import { HttpService } from './../shared/http.service';
import { Injectable } from '@angular/core';

@Injectable()
export class VolunteerService {

  constructor(private _http: HttpService) { }

  private _url = "/api/volunteer/";

  getVolunteerByUserId(userId: number = 0, page: number = 0): Observable<any> {
    return this._http.get(this._url + userId + "?page=" + page)
      .map((response: Response) => response.json());
  }

  getCauseCount(userId: number = 0): Observable<any> {
    return this._http.get(this._url + 'cause/' + userId)
      .map((response: Response) => response.json());
  }

  getCauseName(userId: number = 0, page: number = 0): Observable<any> {
    return this._http.get(this._url + 'cause/name/' + userId + "?page=" + page)
      .map((response: Response) => response.json());
  }

  getBackoutCauseCount(userId: number = 0): Observable<any> {
    return this._http.get(this._url + 'cause/backout/' + userId)
      .map((response: Response) => response.json());
  }

  getDoingCauseCount(userId: number = 0): Observable<any> {
    return this._http.get(this._url + 'cause/doing/' + userId)
      .map((response: Response) => response.json());
  }

  getFeedback(userId: number = 0, page: number = 0): Observable<any> {
    return this._http.get(this._url + 'feedback/' + userId + "?page=" + page)
      .map((response: Response) => response.json());
  }

}
