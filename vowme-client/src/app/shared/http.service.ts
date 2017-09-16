import  swal  from 'sweetalert2';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';
import {
    Http,
    RequestOptions,
    RequestOptionsArgs,
    Response,
    Request,
    Headers,
    BaseRequestOptions,
    XHRBackend
} from '@angular/http';

import { LoaderService } from './loader/loader.service';

@Injectable()
export class HttpService extends Http {

    constructor(
        backend: XHRBackend,
        defaultOptions: BaseRequestOptions,
        private loaderService: LoaderService
    ) {
        super(backend, defaultOptions);
    }

    get(url: string, options?: RequestOptionsArgs): Observable<any> {

        this.showLoader();

        // return super.get(this.getFullUrl(url), this.requestOptions(options))
        return super.get(this.getFullUrl(url), this.requestOptions(options))
            .catch(this.onCatch)
            .do((res: Response) => {
                this.onSuccess(res);
            }, (error: any) => {
                this.onError(error);
            })
            .finally(() => {
                this.onEnd();
            });

    }
    /**
 * Performs a request with `post` http method.
 * @param url
 * @param body
 * @param options
 * @returns {Observable<>}
 */
    post(url: string, body: any, options?: RequestOptionsArgs, loader?: boolean): Observable<any> {
        if (loader) { this.showLoader(); }
        return super.post(this.getFullUrl(url), body, this.requestOptions(options))
            .catch(this.onCatch.bind(this))
            .do((res: Response) => {
                this.onSuccess(res);
            }, (error: any) => {
                this.onError(error);
            })
            .finally(() => {
                this.onEnd();
            });
    }

    /**
     * Performs a request with `put` http method.
     * @param url
     * @param body
     * @param options
     * @returns {Observable<>}
     */
    put(url: string, body: any, options?: RequestOptionsArgs): Observable<any> {
        //this.showLoader();
        return super.put(this.getFullUrl(url), body, this.requestOptions(options))
            .catch(this.onCatch.bind(this))
            .do((res: Response) => {
                this.onSuccess(res);
            }, (error: any) => {
                this.onError(error);
            })
            .finally(() => {
                this.onEnd();
            });
    }

    /**
     * Performs a request with `delete` http method.
     * @param url
     * @param options
     * @returns {Observable<>}
     */
    delete(url: string, options?: RequestOptionsArgs): Observable<any> {
        //this.showLoader();
        return super.delete(this.getFullUrl(url), this.requestOptions(options))
            .catch(this.onCatch.bind(this))
            .do((res: Response) => {
                this.onSuccess(res);
            }, (error: any) => {
                this.onError(error);
            })
            .finally(() => {
                this.onEnd();
            });
    }

    private requestOptions(options?: RequestOptionsArgs): RequestOptionsArgs {

        if (options == null) {
            options = new RequestOptions();
            options.headers = new Headers({
                'Content-Type': 'application/json',
            });
        }

        const userID = localStorage.getItem("user");
        const userToken = localStorage.getItem("userToken");
        if (userToken && userID) {
            options.headers.append('X-Access-Token', userToken);
            options.headers.append('X-User-ID', userID);
        }

        return options;
    }

    private getFullUrl(url: string): string {
        return environment.API_ENDPOINT + url;
    }

    private onCatch(error: any, caught: Observable<any>): Observable<any> {
        return Observable.throw(error);
    }

    private onSuccess(res: Response): void {
        this.getUserChecker(res);
    }

    getUserChecker(res: Response) {
        const userAlive = res.headers.get("X-User-Alive");
        if (userAlive) {
            localStorage.setItem('isLoged', 'true');
        } else {
            localStorage.setItem('isLoged', 'false');
        }
    }

    private onError(res: Response): void {
        if (res.status == 0)
        swal("Opps", "There is some connection issue.","error");
        console.log('Error, status code: ' + res.status);
    }

    private onEnd(): void {
        this.hideLoader();
    }

    private showLoader(): void {
        this.loaderService.show();
    }

    private hideLoader(): void {
        this.loaderService.hide();
    }
}
