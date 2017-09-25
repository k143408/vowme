import { ShortUserWithCauseName } from './../../shared/models/user-cause';
import { MultiObject } from './../../shared/models/mutliobject';
import { Component, OnInit } from '@angular/core';
import { UserService } from './../../service/user.service';
import { CauseService } from './../../service/cause.service';
import { BaseComponent } from 'app/shared/base.component';
import { Router } from '@angular/router';
import { User } from './../../shared/models/user';
import { VolunteerService } from './../../service/volunteer.service';
import { SmartTableService } from './../smart-table.service';
import { LocalDataSource } from 'ng2-smart-table';
import swal from 'sweetalert2';
@Component({
  selector: 'app-pending-list',
  templateUrl: './pending-list.component.html',
  styleUrls: ['./pending-list.component.scss']
})
export class PendingListComponent extends BaseComponent {
  private isEnabled = false;
  settings = {
    actions: {
      add: false,
      edit: false,
      delete: false,
    },
    columns: {
      id: {
        title: 'ID',
        type: 'number'
      },
      firstname: {
        title: 'First Name',
        type: 'string'
      },
      lastname: {
        title: 'Last Name',
        type: 'string'
      },
      username: {
        title: 'Username',
        type: 'string'
      },
      email: {
        title: 'E-mail',
        type: 'string'
      },
      cnic: {
        title: 'CNIC',
        type: 'number'
      },
      causeName: {
        title: 'Cause Name',
        type:'string'
      }
    }
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(protected service: VolunteerService, protected causeService: CauseService, protected userSerivce: UserService, private router: Router) {
    super(causeService, userSerivce);
    this.service.getPendingVolunteerByUserId(this.userID).subscribe(data => {
      this.source.load(data.content).then(data => {
        
      })
    });
  }

  onFilter($event: any) {
    this.isEnabled = false;
  }
  onSelect(event: ShortUserWithCauseName) {
    //    if (this.isEnabled) {
    let comp = this;
    swal({
      title: '',
      text: "Do you want to override system's action on " + event.firstname  + "'s Profile? Click No, to visit profile.",
      type: 'info',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes!',
      cancelButtonText: 'No!',
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      buttonsStyling: false
    }).then(function () {
        comp.router.navigate(['/volunteer-pending/',event.causeId, event.id]);
    }, function (dismiss) {
      if (dismiss === 'cancel') {
        comp.router.navigate(['/volunteer-detail', event.id]);
      }
    });
  }
}
