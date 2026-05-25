import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AiService {
  private ollamaUrl = 'http://localhost:11434/api/generate';

  constructor(private http: HttpClient) {}

  chat(message: string, patientContext: string = ''): Observable<any> {
    const prompt = patientContext
      ? `You are a cardiovascular health assistant. Patient context: ${patientContext}. User question: ${message}`
      : `You are a cardiovascular health assistant. ${message}`;

    return this.http.post(this.ollamaUrl, {
      model: 'llama3.2:1b',
      prompt: prompt,
      stream: false
    });
  }
}
