import { KeyValue } from './keyvalue';
import { Skills } from './skills';
import { Skill } from './skill';
import { UserInfo } from './userinfo';

export class User {
  constructor(){ this.userInfo = new UserInfo();}
  public id: number;
  public isActive: number;
  public cnic: string;
  public cnicVerified: number;
  public createdAt: number;
  public email: string;
  public emailVerified: number;
  public firstname: string;
  public lastname: string;
  public updatedAt: number;
  public username: string;
  public userInfo: UserInfo;
  userSkills: Skills[];
  info : KeyValue;
  
  isVerfied(): boolean{
    return  (this.emailVerified == 1 && this.cnicVerified == 1);
  }
}