import { Observable } from 'rxjs/Observable';
import { HttpService } from './../shared/http.service';
import { Injectable } from '@angular/core';

@Injectable()
export class UserService {

  constructor(private _http: HttpService) { }

  private _url = "/api/user/";

  getTeams(userId: number = 0): Observable<any> {
    return this._http.get(this._url + 'team/' + userId)
      .map((response: Response) => response.json());
  }

  getRanking(userId: number = 0): Observable<any> {
    return this._http.get(this._url + 'rank/' + userId)
      .map((response: Response) => response.json());
  }


  getUserDetails(userId: number = 0): Observable<any> {
    return this._http.get(this._url + userId)
      .map((response: Response) => response.json());
  }

  saveUser(data: string): Observable<any> {
    return this._http.post(this._url, data)
      .map((response: Response) => response.json());
  }


}
