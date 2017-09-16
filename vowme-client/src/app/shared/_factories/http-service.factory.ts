import { HttpService } from './../http.service';
import { AngularReduxRequestOptions } from './../angular-redux-request.options';
import { LoaderService } from './../loader/loader.service';
import { XHRBackend } from '@angular/http';


function httpServiceFactory(backend: XHRBackend, options: AngularReduxRequestOptions, loaderService: LoaderService ) {
    return new HttpService(backend, options, loaderService);
}

export { httpServiceFactory };
