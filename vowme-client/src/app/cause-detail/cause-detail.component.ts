import swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router';
import { CauseService } from './../service/cause.service';
import { UserService } from './../service/user.service';
import { Component, OnInit } from '@angular/core';
import { BaseComponent } from "app/shared/base.component";

@Component({
  selector: 'app-cause-detail',
  templateUrl: './cause-detail.component.html',
  styleUrls: ['./cause-detail.component.css']
})
export class CauseDetailComponent extends BaseComponent implements OnInit {

  constructor(private router: ActivatedRoute, protected causeService: CauseService, protected userSerivce: UserService) { super(causeService, userSerivce) }


  ngOnInit() {
    this.router.params.subscribe(params => {
      this.getCause(params['id']);
    });
  }

  approval() {
    let com = this;
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
      swal({
        type: 'success',
        title: 'Created with same Team!',
      });
    }, function (dismiss) {
      if (dismiss === 'cancel') {
        swal({
          type: 'success',
          title: 'Created!',
        });
      }
    });
  }

}
