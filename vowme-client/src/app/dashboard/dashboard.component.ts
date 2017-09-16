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
  
  onCauseChange(value){
    this.onLoadChart();
  }

  onLoadChart() {
    this.chartService.getChartStatus(1).subscribe(map => {
      var dataSales = {
        labels: ['9:00AM', '12:00AM', '3:00PM', '6:00PM', '9:00PM', '12:00PM', '3:00AM', '6:00AM'],
        series: [
          map[0],
          map[1],
          map[2]
        ]
      };

      var optionsSales = {
        low: 20,
        high: 1000,
        showArea: true,
        height: "245px",
        axisX: {
          showGrid: true,
        },
        lineSmooth: Chartist.Interpolation.simple({
          divisor: 3
        }),
        showLine: true,
        showPoint: true,
      };

      var responsiveSales = [
        ['screen and (max-width: 640px)', {
          axisX: {
            labelInterpolationFnc: function (value) {
              return value[0];
            }
          }
        }]
      ];

      Chartist.Line('#chartHours', dataSales, optionsSales, responsiveSales);

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
    this.onLoadChart();
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

    Chartist.Pie('#chartPreferences', {
      labels: ['62%', '32%', '6%'],
      series: [62, 32, 6]
    });

    this.getCauses();
  }
}
