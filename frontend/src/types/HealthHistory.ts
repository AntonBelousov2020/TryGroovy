export interface HealthHistory {
  id: number;
  patientId: number;
  diagnosis: string;
  description: string;
  createdAt: string;
}

export interface HealthHistoryRequest {
  patientId: number;
  diagnosis: string;
  description: string;
}