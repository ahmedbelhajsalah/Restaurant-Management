import { Component, OnInit } from '@angular/core';
import { Category } from '../../../admin/admin-components/dashboard/dashboard.component';
import { CustomerService } from '../../customer-service/customer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  

  constructor(private customerService: CustomerService, private router: Router){}


  categories: Category[] = [];


  originalCategories: Category[] = [];

ngOnInit(): void {
  this.getAllCategories();
}

getAllCategories() {
  this.customerService.getAllCategories().subscribe(data => {
    this.originalCategories = data.map((element: any) => {
      element.returnedImg = 'data:image/jpeg;base64,' + element.returnedImg;
      return element;
    });
    this.categories = [...this.originalCategories];
  });
}

applyFilter(event: Event) {
  const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
  if (!filterValue) {
    this.categories = [...this.originalCategories]; // Reset to original categories when filter is empty
    return;
  }
  this.categories = this.originalCategories.filter(category => {
    return category.name.toLowerCase().includes(filterValue);
  });
}

goToCustomerViewProducts(categoryId: string) {
  this.router.navigate([`admin/products/${categoryId}`]);
  }
}
