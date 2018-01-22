import { User } from './../shared/models/user';
import { VolunteerService } from './../service/volunteer.service';
import { ChartService } from './../service/chart.service';
import { UserService } from './../service/user.service';
import { CauseService } from './../service/cause.service';
import { Cause } from './../shared/models/cause';
import { Page } from './../shared/models/page';
import { Component, OnInit } from '@angular/core';
import * as Chartist from 'chartist';
import { BaseComponent } from './../shared/base.component';

declare var $: any;

@Component({
  selector: 'dashboard-cmp',
  moduleId: module.id,
  templateUrl: 'dashboard.component.html',
  providers: [ChartService, VolunteerService]
})

export class DashboardComponent extends BaseComponent implements OnInit {
  constructor(private volunteerService: VolunteerService, private chartService: ChartService, protected causeService: CauseService, protected userSerivce: UserService) { super(causeService, userSerivce) }
  
  onCauseChange(causeId){
    this.onLoadChart(causeId);
  }

  onLoadChart(causeId) {
    this.chartService.getChartStatus(causeId).subscribe(map=>{
      Chartist.Pie('#chartPreferences', {
        labels: [map[0]+'%', map[2]+'%', map[1]+'%'],
        series: [map[0], map[2], map[1]]
      });
    });
  }

  getVolunteers() {
    this.volunteerService.getVolunteerByUserId(this.userID).subscribe(page => {
      this.volunteerList = page.content;
    });
  }
  showDetails(){
    alert("Hello");
  }

  ngOnInit() {
    this.onLoadChart(0);
    this.getVolunteers();
    var data = {
      labels: ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
      series: [
        [542, 543, 520, 680, 653, 753, 326, 434, 568, 610, 756, 895],
        [230, 293, 380, 480, 503, 553, 600, 664, 698, 710, 736, 795]
      ]
    };

    var options = {
      seriesBarDistance: 10,
      axisX: {
        showGrid: false
      },
      height: "245px"
    };

    var responsiveOptions = [
      ['screen and (max-width: 640px)', {
        seriesBarDistance: 5,
        axisX: {
          labelInterpolationFnc: function (value) {
            return value[0];
          }
        }
      }]
    ];

    Chartist.Line('#chartActivity', data, options, responsiveOptions);

    var dataPreferences = {
      series: [
        [25, 30, 20, 25]
      ]
    };

    var optionsPreferences = {
      donut: true,
      donutWidth: 40,
      startAngle: 0,
      total: 100,
      showLabel: false,
      axisX: {
        showGrid: false
      }
    };

    Chartist.Pie('#chartPreferences', dataPreferences, optionsPreferences);

    

    this.getCauses();
  }
}
