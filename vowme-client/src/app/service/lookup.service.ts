import { Observable } from 'rxjs/Observable';
import { HttpService } from './../shared/http.service';
import { Injectable } from '@angular/core';

@Injectable()
export class LookupService {

  constructor(private _http: HttpService) { }

  private _url = "/api/lookup/";

  getLookupCauses(type:string): Observable<any> {
    return this._http.get(this._url + type)
      .map((response: Response) => response.json());
  }

}
