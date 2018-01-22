export class Skill {
  id: number;
  createdAt?: any;
  name: string;
  updatedAt?: any;

  constructor(id:number, name:string ){
    this.id = id;
    this.name = name;
  }
}