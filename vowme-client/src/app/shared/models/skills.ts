import { Skill } from './skill';

export class Skills {
    id: number;
    skill: Skill;

    constructor(look:Skill){
        this.skill = look;
    }
}