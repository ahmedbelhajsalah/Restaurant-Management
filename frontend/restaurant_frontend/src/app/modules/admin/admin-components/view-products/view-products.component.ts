import { Component } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../dashboard/dashboard.component';

export interface Product{
  id:string;
  name: string;
  returnedImg:string;
  description: string;
  price: number;
  categoryId: number
}

@Component({
  selector: 'app-view-products',
  templateUrl: './view-products.component.html',
  styleUrl: './view-products.component.css'
})
export class ViewProductsComponent {

  constructor(private adminService: AdminService, private router: Router,
    private activatedRouter: ActivatedRoute
  ){}

  categoryId: any = this.activatedRouter.snapshot.params['categoryId'];


  products: Product[] = [];


  originalProducts: Product[] = [];

ngOnInit(): void {
  this.getAllProductsBycategory();
}

getAllProductsBycategory() {
  this.adminService.getAllProductsByCategory(this.categoryId).subscribe(data => {
    this.originalProducts = data.map((element: any) => {
      element.returnedImg = 'data:image/jpeg;base64,' + element.returnedImg;
      return element;
    });
    this.products = [...this.originalProducts];
  });
}

applyFilter(event: Event) {
  const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
  if (!filterValue) {
    this.products = [...this.originalProducts]; // Reset to original categories when filter is empty
    return;
  }
  this.products = this.originalProducts.filter(product => {
    return product.name.toLowerCase().includes(filterValue);
  });
}

}
