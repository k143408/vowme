import { Cause } from './../shared/models/cause';
import swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import { CauseService } from './../service/cause.service';
import { UserService } from './../service/user.service';
import { Component, OnInit } from '@angular/core';
import { BaseComponent } from "app/shared/base.component";

declare var $: any;
@Component({
  selector: 'app-cause-detail',
  templateUrl: './cause-detail.component.html',
  styleUrls: ['./cause-detail.component.css']
})
export class CauseDetailComponent extends BaseComponent implements OnInit {

  constructor(private router: ActivatedRoute, private redirect: Router, protected causeService: CauseService, protected userSerivce: UserService) { super(causeService, userSerivce) }


  ngOnInit() {
    this.router.params.subscribe(params => {
      this.getCause(params['id']);
    });
    this.getCurrentUser();
  }

  addTeamMember() {
    let com = this;
    swal.queue([{
      title: 'Please Enter Email of Team Member',
      input: 'email',
      confirmButtonText: 'Send Invite',
      showLoaderOnConfirm: true,
      preConfirm: function (email) {
        return new Promise(function (resolve) {
          com.userSerivce.addTeam(com.currentUser.id, com.currentCause.id, email).subscribe(data => {
            if (com.currentCause.boardcasts.length < 5) {
              com.currentCause.boardcasts = com.currentCause.boardcasts.concat(data);
            }
            resolve()
          })
        })
      }
    }])
  }

  approval() {
    let com = this;
    if (this.currentCause.teams.length == 0) {
      swal({
        title: '',
        text: "Do you want to create this type of cause?",
        type: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes!',
        cancelButtonText: 'No!',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger',
        buttonsStyling: false
      }).then(function () {
        com.swalForTeamCreation();//swalForSuccess('Created!');
      }, function (dismiss) {
        if (dismiss === 'cancel') {

        }
      });
    } else {
      swal({
        title: '',
        text: "Do you want to create with same team?",
        type: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes!',
        cancelButtonText: 'No!',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger',
        buttonsStyling: false
      }).then(function () {
        com.swalForTeamCreation(true);
        //  com.swalForSuccess('Created with same Team!');
      }, function (dismiss) {
        if (dismiss === 'cancel') {
          com.swalForTeamCreation();
        }
      });
    }
  }

  swalForSuccess(_title: string, message: string = '') {
    swal({
      type: 'success',
      title: _title,
      text: message
    });

  }

  swalForTeamCreation(withSameTeam: boolean = false) {
    let com = this;
    swal({
      title: 'Cause Details',
      html:
      '<input id="swal-input1" placeholder="Cause Name" class="swal2-input">' +
      '<input id="swal-input2" placeholder="Start Date" class="swal2-input">' +
      '<input id="swal-input3" placeholder="End Date" class="swal2-input">' +
      '<input id="swal-input4" placeholder="Email Address" class="swal2-input">',

      focusConfirm: false,
      preConfirm: function () {
        return new Promise(function (resolve, reject) {
          let input1 = /^[a-zA-Z ]+$/.test($('#swal-input1').val());
          let input2 = /^\d{2}[./-]\d{2}[./-]\d{4}$/.test($('#swal-input2').val());
          let input3 = /^\d{2}[./-]\d{2}[./-]\d{4}$/.test($('#swal-input3').val());
          let input4 = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test($('#swal-input4').val());

          if (input1 && input2 && input3 && input4) {
            let date1 = new Date($('#swal-input2').val());
            let date2 = new Date($('#swal-input3').val());
            let currentDate = new Date();
            let dateAfter3Months = Object.assign(new Date(date1.getFullYear(),date1.getMonth()+3,date1.getDate()));
            if ((date1.getTime() > currentDate.getTime() && date2.getTime() > currentDate.getTime()) && (date2.getMonth() < dateAfter3Months.getMonth() && date2.getFullYear() == dateAfter3Months.getFullYear())) {
              resolve([
                $('#swal-input1').val(),
                $('#swal-input2').val(),
                $('#swal-input3').val(),
                $('#swal-input4').val(),
              ])
            } else {
              reject('Date should not be lesser than current date or end date should be within in 3 months.');
            }
          } else {
            reject('Please provide all fields! <br/> Date format should be in <strong>dd mm yyyy e.g. 01/01/2018</strong>')
          }
        })
      }
    }).then(function (_result) {
      //let _result = JSON.stringify(result)
      let data = Object.assign(new Cause(), com.currentCause);
      data.approvals = [];
      data.backouts = [];
      data.boardcasts = [];
      data.createdAt = (new Date().getTime() / 1000);
      data.email = _result[3];
      data.feedbacks = [];
      data.participates = [];
      data.registrationdate = new Date((_result[1])).getTime() / 1000;
      data.registrationdeadline = new Date((_result[2])).getTime() / 1000;
      data.setName(_result[0]);
      data.user = com.currentUser;
      data.id = null;
      if (!withSameTeam) {
        data.teams = [];
      }
      com.causeService.addCause(data).subscribe(cause => {
        com.redirect.navigate(['/cause-detail', cause.id]);
      });

    }).catch(swal.noop)
  }
}