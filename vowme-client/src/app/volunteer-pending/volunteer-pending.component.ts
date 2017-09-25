import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-volunteer-pending',
  templateUrl: './volunteer-pending.component.html',
  styleUrls: ['./volunteer-pending.component.css']
})
export class VolunteerPendingComponent implements OnInit {

  private approvalEnabled: boolean = true;

  constructor() { }

  ngOnInit() {

    this.approvalEnabled = true;
  }

}
