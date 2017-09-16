import { LocalDataSource } from 'ng2-smart-table';
import { Router } from '@angular/router';
import swal  from 'sweetalert2';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-causelist',
  templateUrl: './causelist.component.html',
  styleUrls: ['./causelist.component.scss']
})
export class CauselistComponent implements OnInit {

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
      name: {
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
      },
      email: {
        title: 'email',
        type: 'string'
      }
    }
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(private router: Router) { }

  ngOnInit() {
  }

  onSelect(event: any) {
    //    if (this.isEnabled) {
    let comp = this;
    swal({
      title: 'Are you sure?',
      text: "Do you want to visit " + event.firstname + " cause?",
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
      comp.router.navigate(['/volunteer-detail', event.id]);
    }, function (dismiss) {
      if (dismiss === 'cancel') {

      }
    });
    //}
    //    this.isEnabled = true;
  }
}
