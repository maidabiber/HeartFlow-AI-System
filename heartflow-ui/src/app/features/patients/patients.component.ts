import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PatientService } from '../../core/services/patient.service';
import { AuthService } from '../../core/services/auth.service';
import { Patient, PatientRequest } from '../../core/models/patient.model';

@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './patients.component.html',
  styleUrl: './patients.component.scss'
})
export class PatientsComponent implements OnInit {
  patients: Patient[] = [];
  searchQuery = '';
  showForm = false;
  loading = false;
  currentUser: any;

  newPatient: PatientRequest = {
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: 'MALE',
    email: '',
    phone: '',
    address: ''
  };

  constructor(
    private patientService: PatientService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();
    this.loadPatients();
  }

  loadPatients() {
    this.patientService.getAllPatients().subscribe({
      next: (patients) => this.patients = patients,
      error: (err) => console.error(err)
    });
  }

  search() {
    if (this.searchQuery.trim()) {
      this.patientService.searchPatients(this.searchQuery).subscribe({
        next: (patients) => this.patients = patients
      });
    } else {
      this.loadPatients();
    }
  }

  addPatient() {
    this.loading = true;
    this.patientService.createPatient(this.newPatient).subscribe({
      next: () => {
        this.loadPatients();
        this.showForm = false;
        this.loading = false;
        this.resetForm();
      },
      error: () => this.loading = false
    });
  }

  resetForm() {
    this.newPatient = {
      firstName: '', lastName: '', dateOfBirth: '',
      gender: 'MALE', email: '', phone: '', address: ''
    };
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
