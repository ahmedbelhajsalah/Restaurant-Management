import { Component } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../dashboard/dashboard.component';
import { Product } from '../../../customer/customer-service/customer.service';



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

noProductAvailable(){
  return this.originalProducts.length === 0;
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

onDeleteProduct(productId: number) {
  this.adminService.deleteProduct(productId).subscribe(() => {
    this.products = this.products.filter(product => product.id !== productId);
    this.originalProducts = this.products.filter(product => product.id !== productId);
  }, error => {
    console.error('Error deleting product:', error);
  });
}


}
