import swal from 'sweetalert2';
import { KeyValue } from './../shared/models/keyvalue';
import { Skills } from './../shared/models/skills';
import { Skill } from './../shared/models/skill';
import { Page } from './../shared/models/page';
import { CustomFeedback } from './../shared/models/custom.feedback';
import { Subscription } from 'rxjs/Subscription';
import { User } from './../shared/models/user';
import { ActivatedRoute } from '@angular/router';
import { VolunteerService } from './../service/volunteer.service';
import { CauseService } from './../service/cause.service';
import { UserService } from './../service/user.service';
import { Component, OnInit, Input } from '@angular/core';

import { BaseComponent } from './../shared/base.component';

@Component({
  selector: 'app-volunteer',
  templateUrl: './volunteer.component.html',
  styleUrls: ['./volunteer.component.css'],
  providers: [VolunteerService]
})
export class VolunteerComponent extends BaseComponent implements OnInit {
  @Input() private pendingTag = false;
  constructor(private router: ActivatedRoute, private volunteerService: VolunteerService, protected causeService: CauseService, protected userSerivce: UserService) { super(causeService, userSerivce) }
  viewUser: User = new User();
  totalCauseDoing: number = 0;
  totalCauseDone: number = 0;
  totalBackout: number = 0;
  feedbackList: CustomFeedback[];
  totalFeedbackCount: number = 0;
  pageFeedback: Page<CustomFeedback>;
  pageCauseName: Page<KeyValue>;
  causeNameList: KeyValue[];
  causeNameCount: number = 0;
  userSkill: Skills[];
  ngOnInit() {
    this.router.params.subscribe(params => {
      this.userSerivce.getUserDetails(params['id']).subscribe(user => {
        this.viewUser = user;
        this.userSkill = this.viewUser.userSkills;
      });
      this.volunteerService.getCauseCount(params['id']).subscribe(count => {
        this.totalCauseDone = count;
      });
      this.volunteerService.getBackoutCauseCount(params['id']).subscribe(count => {
        this.totalBackout = count;
      });
      this.volunteerService.getDoingCauseCount(params['id']).subscribe(count => {
        this.totalCauseDoing = count;
      });
      this.volunteerService.getFeedback(params['id']).subscribe(page => {
        this.pageFeedback = page;
        this.feedbackList = this.pageFeedback.content;
        this.totalFeedbackCount = this.pageFeedback.totalElements;
      });
      this.volunteerService.getCauseName(params['id']).subscribe(page => {
        this.pageCauseName = page;
        this.causeNameList = this.pageCauseName.content;
        this.causeNameCount = this.pageCauseName.totalElements;
      });
      this.userSerivce.getRanking(params['id']).subscribe(rank => {
        this.currentRate = rank;
      });
    });
  }

  rejection() {
    let com = this;
    swal({
      title: 'Reason For Rejecting',
      input: 'textarea',
      showCancelButton: true,
      confirmButtonText: 'Send',
      showLoaderOnConfirm: true,
      preConfirm: function (email) {
        return new Promise(function (resolve, reject) {
          setTimeout(function () {
            if (email === '') {
              reject('Please mention the reason for rejecting.')
            } else {
              resolve()
            }
          }, 2000)
        })
      },
      allowOutsideClick: false
    }).then(function (email) {
      swal({
        type: 'success',
        title: 'Reason has been saved!',
        html: 'Submitted email: ' + com.viewUser.email
      });
      com.pendingTag = false;
    })
  }
  approval() {
    let com = this;
    
    swal({
      title: 'Are you sure?',
      text: "Do you want to approve " + this.viewUser.firstname + "'s Profile?",
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
        swal({
        type: 'success',
        title: 'Approved!',
        html: 'Submitted email: ' + com.viewUser.email
      });
      com.pendingTag = false;
    }, function (dismiss) {
      if (dismiss === 'cancel') {

      }
    });
  }

}
