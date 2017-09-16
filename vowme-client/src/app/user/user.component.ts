import { EMAIL_REGEX } from './../shared/utility';
import { CauseService } from './../service/cause.service';
import { UserService } from './../service/user.service';
import { User } from './../shared/models/user';
import { Page } from './../shared/models/page';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

import { BaseComponent } from './../shared/base.component';

@Component({
    selector: 'user-cmp',
    moduleId: module.id,
    templateUrl: 'user.component.html'
})

export class UserComponent extends BaseComponent implements OnInit {

    userForm: FormGroup;

    formErrors = {
        'userName': '',
        'email': '',
        'firstName': '',
        'lastName': '',
        'address': '',
        'city': '',
        'zipCode': '',
        'phoneNumber': '',
        'aboutMe': ''
    };


    constructor(private fb: FormBuilder, protected causeService: CauseService, protected userSerivce: UserService) {
        super(causeService, userSerivce)
    }

    ngOnInit() {
        this.createForm();
        this.getCauses(this.userForm);
        this.getTeams();
    }

    onSubmit(){
        this.saveUser(JSON.stringify(this.userForm.value),true,this.userForm) ;
    }

    createForm(): void {
        this.userForm = this.fb.group({
            userId:'',
            userName: ['', Validators.required],
            email: ['', [Validators.required, Validators.pattern(EMAIL_REGEX)]],
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            address: ['', [Validators.required]],
            city: ['', [Validators.required]],
            zipCode: ['', Validators.required],
            phoneNumber: ['', Validators.required],
            aboutMe: ['', Validators.required]
        });
        this.userForm.valueChanges.debounceTime(1000)
            .subscribe(data => {
                this.onValueChanged(data);
            });

        this.onValueChanged();
    }

    onValueChanged(data?: any) {
        if (!this.userForm) {
            return;
        }
        const form = this.userForm;

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
}
