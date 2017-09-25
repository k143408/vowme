import { UserNotification } from './../models/team.notify';
import { CauseService } from './../../service/cause.service';
import { UserService } from './../../service/user.service';
import { BaseComponent } from 'app/shared/base.component';
import { Observable } from 'rxjs/Observable';
import { NotificationService } from './../../service/notification.service';
import { Component, OnInit, Renderer, ViewChild, ElementRef } from '@angular/core';
import { ROUTES } from '../../sidebar/sidebar.component';
import { Router, ActivatedRoute } from '@angular/router';
import { Location, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { TimerObservable } from "rxjs/observable/TimerObservable";

declare var $: any;

@Component({
    moduleId: module.id,
    selector: 'navbar-cmp',
    templateUrl: 'navbar.component.html'
})

export class NavbarComponent extends BaseComponent implements OnInit {
    private listTitles: any[];
    location: Location;
    private nativeElement: Node;
    private toggleButton;
    private sidebarVisible: boolean;
    private userNotification: UserNotification[];

    @ViewChild("navbar-cmp") button;

    constructor(location: Location, private renderer: Renderer, private element: ElementRef, private notificationService: NotificationService, protected causeService: CauseService, protected userSerivce: UserService) {
        super(causeService, userSerivce);
        this.location = location;
        this.nativeElement = element.nativeElement;
        this.sidebarVisible = false;
    }

    ngOnInit() {
        this.listTitles = ROUTES.filter(listTitle => listTitle);
        var navbar: HTMLElement = this.element.nativeElement;
        this.toggleButton = navbar.getElementsByClassName('navbar-toggle')[0];
        this.getCurrentUser();
        this.getNotify();
    }

    getNotify() {
        let timer = TimerObservable.create(0, 5000);
        timer.subscribe(t => {
            this.notificationService.getNotify(this.currentUser.email)
                .subscribe(data => {
                    this.userNotification = [];
                    this.userNotification = data;
                    this.userNotification.forEach(notify => {
                        if (notify.notified == 0) {
                            this.notificationService.makeItSeen(notify.id)
                                .subscribe(data => {
                                    this.showNotification(notify.message);
                                });
                        }
                    });
                });
        });
    }

    showNotification(_message: string) {
        $.notify({
            icon: "ti-bell",
            message: _message
        }, {
                type: 'info',
                timer: 1000,
                offset: 50,
                newest_on_top: true,
                placement: {
                    from: 'bottom',
                    align: 'right'
                }
            });
    }

    makeItJoin(notify: UserNotification) {
        notify.status = 1;  
        this.notificationService.joinIt(notify,this.userID)
            .subscribe(data => {

            });
    }

    makeItReject(notify: UserNotification) {
        notify.status = 0;
        this.notificationService.joinIt(notify,this.userID)
            .subscribe(data => {
            });
    }

    getTitle() {
        var titlee = window.location.pathname;
        titlee = titlee.substring(1);
        for (var item = 0; item < this.listTitles.length; item++) {
            if (this.listTitles[item].path === titlee) {
                return this.listTitles[item].title;
            }
        }
        return 'Dashboard';
    }
    sidebarToggle() {
        var toggleButton = this.toggleButton;
        var body = document.getElementsByTagName('body')[0];

        if (this.sidebarVisible == false) {
            setTimeout(function () {
                toggleButton.classList.add('toggled');
            }, 500);
            body.classList.add('nav-open');
            this.sidebarVisible = true;
        } else {
            this.toggleButton.classList.remove('toggled');
            this.sidebarVisible = false;
            body.classList.remove('nav-open');
        }
    }
}
