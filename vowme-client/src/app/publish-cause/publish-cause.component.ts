import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CauseService } from './../service/cause.service';
import { UserService } from './../service/user.service';
import { BaseComponent } from "app/shared/base.component";

import swal from 'sweetalert2';
import { Cause } from 'app/shared/models/cause';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { EMAIL_REGEX } from 'app/shared/utility';
import { LookupService } from 'app/service/lookup.service';
import { Skills } from 'app/shared/models/skills';
import { Skill } from 'app/shared/models/skill';
import { Causetype } from 'app/shared/models/causetype';
declare var $: any;

@Component({
  selector: 'app-publish-cause',
  templateUrl: './publish-cause.component.html',
  styleUrls: ['./publish-cause.component.css'],
  providers: [LookupService]
})
export class PublishCauseComponent extends BaseComponent implements OnInit {
  causeForm: FormGroup;
  lookupInterestList: Lookup[];
  lookupList: Skill[] = [];
  checkedlookupList: Skill[] = [];
  checkedlookupInterestList: Lookup[]= [];
  longtilat: string;
  formErrors = {
    'causeName': '',
    'email': '',
    'firstName': '',
    'lastName': '',
    'address': '',
    'city': '',
    'zipCode': '',
    'phoneNumber': '',
    'aboutMe': ''
  };

  constructor(private lookupService: LookupService, private fb: FormBuilder, private router: Router, private redirect: Router, protected causeService: CauseService, protected userSerivce: UserService) { super(causeService, userSerivce) }

  ngOnInit() {
    this.lookupCause("skills");
    this.lookupInterest("interests");
    this.getCurrentUser();
    this.createForm();
  }

  createForm(): void {
    this.causeForm = this.fb.group({
      id: '',
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.pattern(EMAIL_REGEX)]],
      registrationdate: ['', Validators.required],
      address: ['', [Validators.required]],
      city: ['', [Validators.required]],
      maxattendees: ['', Validators.required],
      phone: ['', Validators.required],
      registrationdeadline: ['', Validators.required],
      wwwaddress: ['', Validators.required],
      info: ['', Validators.required]
    });
    this.causeForm.valueChanges.debounceTime(100)
      .subscribe(data => {
        this.onValueChanged(data);
      });

    this.onValueChanged();
  }

  onValueChanged(data?: any) {
    if (!this.causeForm) {
      return;
    }
    const form = this.causeForm;

    for (const field in this.formErrors) {
      // clear previous error message (if any)
      this.formErrors[field] = '';
      const control = form.get(field);

      if (control && control.dirty && !control.valid) {
        for (const key in control.errors) {
          this.formErrors[field] = 'class-danger';
        }
      }
    }
  }

  onSubmit() {
    
    let data: Cause = Object.assign(new Cause(), JSON.parse(JSON.stringify(this.causeForm.value)));
    data.causeSkills = this.checkedlookupList.map(look => new Skills(look));
    data.causetypes = this.checkedlookupInterestList.map(look => new Causetype(look.name) );
    data.user = this.currentUser;
    data.visibilitystatus = 1;
    data.location = data.city;
    data.description = data.info;
    data.registrationdate = new Date(data.registrationdate).getTime() / 1000;
    data.registrationdeadline = new Date(data.registrationdeadline).getTime() / 1000;
    data.latlong = this.longtilat;
    data.longitude = Number(this.longtilat.split(",")[0]).toFixed(8).toString();
    data.latitude = Number(this.longtilat.split(",")[1]).toFixed(8).toString();
    this.causeService.addCause(data).subscribe(data=>{
      this.router.navigateByUrl('/dashboard');
    });
  }

  addlatLng(event: any) {
    this.longtilat = event;
  }

  lookupCause(type: string): any {
    this.lookupService.getLookupCauses(type).subscribe(data => {
      this.lookupList = data;
    });
  }

  lookupInterest(type: string): any {
    this.lookupService.getLookupCauses(type).subscribe(data => {
      this.lookupInterest = data;
    });
  }

  onChangeInterest(value: any, isChecked: any) {
    if (isChecked) {
      this.checkedlookupInterestList.push(value);
    } else {
      this.checkedlookupInterestList = this.checkedlookupInterestList.filter(skill => skill.id !== value.id)
    }
  }

  onChange(value: any, isChecked: any) {
    if (isChecked) {
      this.checkedlookupList.push(value);
    } else {
      this.checkedlookupList = this.checkedlookupList.filter(skill => skill.id !== value.id)
    }
  }
}