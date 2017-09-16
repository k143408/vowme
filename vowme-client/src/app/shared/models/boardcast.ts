import { Email } from './email';
export interface Boardcast {
  id: number;
  createdAt: number;
  updatedAt: number;
  email: Email;
}