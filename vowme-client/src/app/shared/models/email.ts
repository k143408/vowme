export interface Email {
  id: number;
  createdAt: number;
  message: string;
  recipientEmail: string;
  success: number;
  title: string;
  updatedAt: number;
}