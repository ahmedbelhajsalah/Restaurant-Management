import { Component, OnInit, ViewChild } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';
import { Router } from '@angular/router';

export interface Category {
  id: string;
  name: string;
  description: string;
  returnedImg: any;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {



  constructor(private adminService: AdminService, private router: Router){}


  categories: Category[] = [];


  originalCategories: Category[] = [];

ngOnInit(): void {
  this.getAllCategories();
}

getAllCategories() {
  this.adminService.getAllCategories().subscribe(data => {
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

goToAdminProduct(categoryId: string) {
  this.router.navigate([`admin/product/${categoryId}`]);
  }
goToAdminViewProducts(categoryId: string) {
  this.router.navigate([`admin/products/${categoryId}`]);
  }

}
