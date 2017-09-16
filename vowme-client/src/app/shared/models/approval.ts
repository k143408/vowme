import { User } from './user';
export interface Approval {
  id: number;
  actionby: User;
  createdAt: number;
  description: string;
  isApproved: number;
  updatedAt: number;
  user: User;
}