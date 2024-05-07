import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from './auth-services/storage-service/storage.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  title = 'restaurant_frontend';

  constructor(private router: Router, private cdr: ChangeDetectorRef) {}
  isAdmin= StorageService.isAdminLoggedIn();
  isCustomer= StorageService.isCustomerLoggedIn();

  ngOnInit(): void {
    this.router.events.subscribe(event =>{
        this.isAdmin = StorageService.isAdminLoggedIn();
        this.isCustomer =StorageService.isCustomerLoggedIn();
        this.cdr.detectChanges();
    })
    console.log('adminnn',this.isAdmin);
    console.log(this.isCustomer);
  }

  goToLogin() {
    this.router.navigate(['/login']); 
  }

  goToSignup() {
    this.router.navigate(['/signup']); 
    }

  logout(){
    StorageService.logout();
    this.router.navigateByUrl("/login");
  }

  
}
