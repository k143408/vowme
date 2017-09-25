import { UserService } from './../../service/user.service';
import { BaseComponent } from 'app/shared/base.component';
import { CauseService } from './../../service/cause.service';
import { LocalDataSource } from 'ng2-smart-table';
import { Router } from '@angular/router';
import swal from 'sweetalert2';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-causelist',
  templateUrl: './causelist.component.html',
  styleUrls: ['./causelist.component.scss']
})
export class CauselistComponent extends BaseComponent implements OnInit {

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
      causeName: {
        title: 'Name',
        type: 'string'
      },
      description: {
        title: 'Description',
        type: 'string'
      },
      registrationdate: {
        title: 'Registration Date',
        type: 'string'
      },
      registrationdeadline: {
        title: 'Registration Deadline',
        type: 'string'
      }
    }
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(protected causeService: CauseService, protected userSerivce: UserService, private router: Router) {
    super(causeService, userSerivce);
    this.causeService.getShortCause(this.userID).subscribe(data => {
      this.source.load(data.content);
    });
  }

  ngOnInit() {
  }

  onSelect(event: any) {
    //    if (this.isEnabled) {
    let comp = this;
    swal({
      title: 'Are you sure?',
      text: "Do you want to visit <strong>" + event.causeName + "</strong>?",
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
      comp.router.navigate(['/cause-detail', event.id]);
    }, function (dismiss) {
      if (dismiss === 'cancel') {

      }
    });
    //}
    //    this.isEnabled = true;
  }
}
