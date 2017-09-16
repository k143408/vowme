import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pending-list',
  templateUrl: './pending-list.component.html',
  styleUrls: ['./pending-list.component.css']
})
export class PendingListComponent implements OnInit {

  private flag = true;
  constructor() { }

  ngOnInit() {
  }

}
