import { Observable } from 'rxjs/Observable';
import { HttpService } from './../shared/http.service';
import { Injectable } from '@angular/core';



@Injectable()
export class NotificationService {

  private _url = "/api/notify/";

  constructor(private _http: HttpService) { }

  getNotify(email: string = 'jibrantk@hotmail.com'): Observable<any> {
    return this._http.get(this._url + '?email=' + email)
      .map((response: Response) => response.json());
  }

  makeItSeen(notifyId: any): Observable<any> {
    return this._http.get(this._url + 'seen?id=' + notifyId)
      .map((response: Response) => response.json());
  }

  joinIt(notify: any, userId: any): Observable<any> {
    return this._http.post(this._url + 'action?userid=2', notify)
      .map((response: Response) => response.json());
  }

}
