import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AiService } from '../../core/services/ai.service';
import { AuthService } from '../../core/services/auth.service';

interface Message {
  role: 'user' | 'assistant';
  content: string;
}

@Component({
  selector: 'app-ai-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ai-chat.component.html',
  styleUrl: './ai-chat.component.scss'
})
export class AiChatComponent {
  messages: Message[] = [
    {
      role: 'assistant',
      content: 'Hello! I am HeartFlow AI Assistant powered by Llama3. I can help you interpret cardiovascular health metrics and provide guidance. How can I help you today?'
    }
  ];
  userInput = '';
  loading = false;
  currentUser: any;

  constructor(
    private aiService: AiService,
    private authService: AuthService,
    private router: Router
  ) {
    this.currentUser = this.authService.getCurrentUser();
  }

  sendMessage() {
    if (!this.userInput.trim() || this.loading) return;

    const userMessage = this.userInput.trim();
    this.messages.push({ role: 'user', content: userMessage });
    this.userInput = '';
    this.loading = true;

    this.aiService.chat(userMessage).subscribe({
      next: (response) => {
        this.messages.push({
          role: 'assistant',
          content: response.response
        });
        this.loading = false;
      },
      error: () => {
        this.messages.push({
          role: 'assistant',
          content: 'Sorry, I could not connect to the AI service. Make sure Ollama is running.'
        });
        this.loading = false;
      }
    });
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
