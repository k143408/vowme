import { User } from './user';
export interface Team {
  id: number;
  user: User;
  isDisabled: number;
}