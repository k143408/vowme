import { User } from './user';
export interface Backout {
  id: number;
  createdAt: number;
  description: string;
  updatedAt: number;
  user: User;
}
