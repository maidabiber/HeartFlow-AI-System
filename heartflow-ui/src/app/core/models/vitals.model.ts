export interface VitalSigns {
  id: number;
  patientId: number;
  heartRate: number;
  bpSystolic: number;
  bpDiastolic: number;
  hrv: number;
  oxygenSaturation: number;
  bodyTemperature: number;
  recordedAt: string;
  notes: string;
}

export interface RiskResponse {
  score: number;
  level: 'LOW' | 'MODERATE' | 'HIGH' | 'CRITICAL' | 'UNKNOWN';
  factors: string;
  patientId: number;
}
