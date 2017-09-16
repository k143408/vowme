import { User } from './user';
export interface Feedback {
  id: number;
  createdAt: number;
  feedback: string;
  updatedAt: number;
  receiver: User;
  commenter: User;
}