import { Observable } from 'rxjs/Observable';
import { HttpService } from './../shared/http.service';
import { Injectable } from '@angular/core';

@Injectable()
export class ChartService {

  private _url = "/api/chart/";
  
  constructor(private _http: HttpService) { }

   getChartStatus(causeId: number = 0): Observable<any> {
         return this._http.get(this._url +'status/' + causeId)
            .map((response: Response) => response.json());
    }

}
