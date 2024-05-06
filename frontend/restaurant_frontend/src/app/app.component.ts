import { Component } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  title = 'restaurant_frontend';

  constructor(private router: Router) {}

  goToLogin() {
    this.router.navigate(['/login']); 
  }

  goToSignup() {
    this.router.navigate(['/signup']); 
    }
}
