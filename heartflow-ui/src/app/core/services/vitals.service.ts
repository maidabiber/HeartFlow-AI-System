import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RiskResponse, VitalSigns } from '../models/vitals.model';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class VitalsService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });
  }

  addVitalSigns(vitals: any): Observable<VitalSigns> {
    return this.http.post<VitalSigns>(`${this.apiUrl}/vitals`, vitals, { headers: this.getHeaders() });
  }

  getVitalsByPatient(patientId: number): Observable<VitalSigns[]> {
    return this.http.get<VitalSigns[]>(`${this.apiUrl}/vitals/patient/${patientId}`, { headers: this.getHeaders() });
  }

  getRiskByPatient(patientId: number): Observable<RiskResponse> {
    return this.http.get<RiskResponse>(`${this.apiUrl}/risk/patient/${patientId}`, { headers: this.getHeaders() });
  }
}
