import { Component, OnInit } from '@angular/core';

declare var $: any;

export interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}

export const ROUTES: RouteInfo[] = [
    { path: 'dashboard', title: 'Dashboard', icon: 'ti-panel', class: '' },
    { path: 'create-cause', title: 'Publish Cause', icon: 'ti-world', class: '' },
    { path: 'user', title: 'User Profile', icon: 'ti-user', class: '' },
    { path: 'volunteer-list', title: 'Volunteers', icon: 'ti-view-list-alt', class: '' },
    { path: 'cause-list', title: 'Causes', icon: 'ti-package', class: '' },
    { path: 'pending-list', title: 'Pending Volunteers', icon: 'ti-arrow-circle-left', class: '' }
];

@Component({
    moduleId: module.id,
    selector: 'sidebar-cmp',
    templateUrl: 'sidebar.component.html',
})

export class SidebarComponent implements OnInit {
    public menuItems: any[];
    ngOnInit() {
        this.menuItems = ROUTES.filter(menuItem => menuItem);
    }
    isNotMobileMenu() {
        if ($(window).width() > 991) {
            return false;
        }
        return true;
    }
}
