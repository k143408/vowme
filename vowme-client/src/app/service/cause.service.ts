import { Cause } from './../shared/models/cause';
import { Observable } from 'rxjs/Observable';
import { HttpService } from './../shared/http.service';
import { Injectable } from '@angular/core';

@Injectable()
export class CauseService {

      private _url = "/api/dashboard/cause/";


      private _url2 = "/api/cause/";


      constructor(private _http: HttpService) { }

      getCause(userId: number = 0): Observable<any> {
            return this._http.get(this._url + 'user/' + userId)
                  .map((response: Response) => response.json());
      }

      getShortCause(userId: number = 0): Observable<any> {
            return this._http.get(this._url2 + 'short/' + userId+'?size=100')
                  .map((response: Response) => response.json());
      }

      getCauseById(causeId: number = 0): Observable<any> {
            return this._http.get(this._url + causeId)
                  .map((response: Response) => response.json());
      }

      addCause(cause: Cause): any {
            return this._http.post(this._url2, cause)
                  .map((response: Response) => response.json());
      }
}
