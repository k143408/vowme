export class Causetype {
  id: number;
  createdAt: number;
  type: string;
  updatedAt: number;

  constructor(type:string){
    this.type = type;
  }
}