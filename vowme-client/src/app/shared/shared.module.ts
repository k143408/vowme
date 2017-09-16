import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { XHRBackend, RequestOptions } from '@angular/http';

import { HttpService } from './http.service';

import { AngularReduxRequestOptions } from './angular-redux-request.options';
import { LoaderService } from './loader/loader.service';
import { LoaderComponent } from './loader/loader.component';
import { httpServiceFactory } from './_factories/http-service.factory';
@NgModule({
    imports: [
        CommonModule
    ],
    exports: [
        LoaderComponent
    ],
    declarations: [
        LoaderComponent
    ],
    providers: [
        LoaderService,
        {
            provide: HttpService,
            useFactory: httpServiceFactory,
            deps: [XHRBackend, RequestOptions, LoaderService ]
        }
    ]
})

export class SharedModule { }
