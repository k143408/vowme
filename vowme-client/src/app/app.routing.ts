import { VolunteerPendingComponent } from './volunteer-pending/volunteer-pending.component';
import { PendingListComponent } from './table/pending-list/pending-list.component';
import { CauselistComponent } from './table/causelist/causelist.component';
import { CauseDetailComponent } from './cause-detail/cause-detail.component';
import { VolunteerComponent } from './volunteer/volunteer.component';
import { SmarttableComponent } from './table/smarttable/smarttable.component';
import { Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { UserComponent } from './user/user.component';
import { TableComponent } from './table/table.component';
import { TypographyComponent } from './typography/typography.component';
import { IconsComponent } from './icons/icons.component';
import { MapsComponent } from './maps/maps.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { LoginComponent } from 'app/login/login.component';


export const AppRoutes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard', 
        pathMatch: 'full',
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'dashboard',
        component: DashboardComponent
    },
    {
        path: 'user',
        component: UserComponent
    },
    {
        path: 'volunteer-detail/:id',
        component: VolunteerComponent
    },
    {
        path: 'volunteer-pending/:causeId/:id',
        component: VolunteerPendingComponent
    },
    {
        path: 'cause-detail/:id',
        component: CauseDetailComponent
    },
    {
        path: 'table',
        component: TableComponent
    },
    {
        path: 'volunteer-list',
        component: SmarttableComponent
    },
    {
        path: 'cause-list',
        component: CauselistComponent
    },
    {
        path: 'pending-list',
        component: PendingListComponent
    },
    {
        path: 'typography',
        component: TypographyComponent
    },
    {
        path: 'icons',
        component: IconsComponent
    },
    {
        path: 'maps',
        component: MapsComponent
    },
    {
        path: 'notifications',
        component: NotificationsComponent
    }

]
