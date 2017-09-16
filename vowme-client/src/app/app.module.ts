import { Rating } from './rating/rating.component';
import { CauseService } from './service/cause.service';
import { UserService } from './service/user.service';
import { SharedModule } from './shared/shared.module';
import { SmarttableComponent } from './table/smarttable/smarttable.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AppRoutes } from './app.routing';
import { SidebarModule } from './sidebar/sidebar.module';
import { FooterModule } from './shared/footer/footer.module';
import { NavbarModule } from './shared/navbar/navbar.module';
import { FixedPluginModule } from './shared/fixedplugin/fixedplugin.module';
import { NguiMapModule } from '@ngui/map';
import { Ng2SmartTableModule } from 'ng2-smart-table';

import { DashboardComponent } from './dashboard/dashboard.component';
import { UserComponent } from './user/user.component';
import { TableComponent } from './table/table.component';
import { TypographyComponent } from './typography/typography.component';
import { IconsComponent } from './icons/icons.component';
import { MapsComponent } from './maps/maps.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { VolunteerComponent } from './volunteer/volunteer.component';
import { CauseDetailComponent } from './cause-detail/cause-detail.component';
import { CauselistComponent } from './table/causelist/causelist.component';
import { PendingListComponent } from './table/pending-list/pending-list.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    UserComponent,
    TableComponent,
    TypographyComponent,
    IconsComponent,
    MapsComponent,
    NotificationsComponent,
    SmarttableComponent,
    VolunteerComponent,
    CauseDetailComponent,
    Rating,
    CauselistComponent,
    PendingListComponent],
  imports: [
    BrowserModule,
    RouterModule.forRoot(AppRoutes),
    SidebarModule,
    NavbarModule,
    FooterModule,
    FixedPluginModule,
    NguiMapModule.forRoot({ apiUrl: 'https://maps.google.com/maps/api/js?key=AIzaSyBr-tgUtpm8cyjYVQDrjs8YpZH7zBNWPuY' }),
    Ng2SmartTableModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule

  ],
  providers: [UserService, CauseService],
  bootstrap: [AppComponent]
})
export class AppModule { }
