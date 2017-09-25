import { Skills } from './skills';

import { Feedback } from './feedback';
import { Team } from './team';
import { Boardcast } from './boardcast';
import { Approval } from './approval';
import { Backout } from './backout';
import { Causetype } from './causetype';
import { Participate } from './participate';
import { User } from './user';

export class Cause {
  constructor() { }
  id: number;
  address: string;
  city: string;
  createdAt: number;
  description: string;
  email: string;
  enddate: number;
  endhour: number;
  endminute: number;
  guestbringalongs: number;
  guestsallowed: number;
  guestsinvitationallowed: number;
  info: string;
  location: string;
  maxattendees: number;
  name: string;
  phone: string;
  registrationdate: number;
  registrationdeadline: number;
  startdate: number;
  starthour: number;
  startminute: number;
  updatedAt: number;
  user: User = new User();
  visibilitystatus: number;
  wwwaddress: string;
  zipcode: number;
  latlong: string;
  participates: Participate[];
  causetypes: Causetype[];
  backouts: Backout[];
  approvals: Approval[];
  boardcasts: Boardcast[];
  teams: Team[];
  feedbacks: Feedback[];
  causeSkills: Skills[];

  setName(name) {
    this.name = name;
  }
}

