import { FormGroup } from '@angular/forms';
import { UserService } from './../service/user.service';
import { User } from './models/user';
import { Cause } from './models/cause';
import { Page } from './models/page';
import { CauseService } from './../service/cause.service';

export abstract class BaseComponent {

    protected userID: number = 1;
    protected pageCause: Page<Cause>;
    protected totalCause: Number = 0;
    protected totalVolunteer: number = 0;
    protected totalBackouts: number = 0;
    protected totalBoardcasts: number = 0;
    protected teamMember: User[];
    protected currentUser: User;
    protected volunteerList: User[];
    protected causeList: Cause[];
    protected currentCause: Cause = new Cause();

    protected maxRateValue: number = 5;
    protected currentRate: number = 0;
    protected isRatingReadonly: boolean = true;
    protected overStar: number;
    protected ratingPercent: number;

    constructor(protected causeService: CauseService, protected userSerivce: UserService) { this.currentUser = new User(); }


    protected saveUser(data: string, isCurrentUser: boolean = false, userForm: FormGroup = null) {
        this.userSerivce.saveUser(data).subscribe(user => {
            if (isCurrentUser) {
                this.currentUser = user;
                this.fillForm(userForm);
            }
        });
    }

    protected getCause(causeId: number = 0) {
        this.causeService.getCauseById(causeId).subscribe(cause => {
            this.currentCause = cause;
        });
    }

    protected getCauses(userForm: FormGroup = null) {
        this.causeService.getCause(this.userID).subscribe(page => {
            this.pageCause = page;
            this.totalCause = this.pageCause.totalElements;
            if (this.pageCause.content) {
                this.causeList = this.pageCause.content;
                this.pageCause.content.forEach(cause => {
                    this.totalVolunteer = this.totalVolunteer + cause.participates.length;
                    this.totalBackouts = this.totalBackouts + cause.backouts.length;
                    this.totalBoardcasts = this.totalBoardcasts + cause.boardcasts.length;
                    this.currentUser = cause.user;
                    if (userForm) this.fillForm(userForm);
                });
            }
        });
    }

    fillForm(userForm: FormGroup): void {
        userForm.get("userId").setValue(this.currentUser.id);
        userForm.get("userName").setValue(this.currentUser.username);
        userForm.get("email").setValue(this.currentUser.email);
        userForm.get("firstName").setValue(this.currentUser.firstname);
        userForm.get("lastName").setValue(this.currentUser.lastname);
        userForm.get("address").setValue(this.currentUser.userInfo.address);
        userForm.get("city").setValue(this.currentUser.userInfo.city);
        userForm.get("zipCode").setValue(this.currentUser.userInfo.zipcode);
        userForm.get("phoneNumber").setValue(this.currentUser.userInfo.contactNumber1);
        userForm.get("aboutMe").setValue(this.currentUser.userInfo.aboutMe);
        // userForm.get("company").setValue(this.currentUser.userInfo.organizationName);
    }
    protected getTeams() {
        this.userSerivce.getTeams(this.userID).subscribe(user => {
            this.teamMember = user;
        });
    }
    protected getRanking(userId: number = 1) {
        this.userSerivce.getRanking(userId).subscribe(ranking => {
            this.currentRate = ranking;
        });
    }

    protected resetRatingStar() {
        this.overStar = null;
    }

    protected overStarDoSomething(value: number): void {
        this.overStar = value;
        this.ratingPercent = 100 * (value / this.maxRateValue);
    };

} 