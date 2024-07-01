import { Component, OnInit } from '@angular/core';
import { CustomerService, Product } from '../../customer-service/customer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { RatingDialogComponent } from '../rating-dialog/rating-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-view-products-customer',
  templateUrl: './view-products-customer.component.html',
  styleUrl: './view-products-customer.component.css'
})
export class ViewProductsCustomerComponent implements OnInit {


  constructor(private customerService: CustomerService, private router: Router,
    private activatedRouter: ActivatedRoute, public dialog: MatDialog, private snackBar: MatSnackBar
  ){}

  categoryId: any = this.activatedRouter.snapshot.params['categoryId'];


  products: Product[] = [];

  averageRating: number = 0;

  originalProducts: Product[] = [];

ngOnInit(): void {
  this.getAllProductsBycategory();
}

noProductAvailable(){
  return this.originalProducts.length === 0;
}

getAllProductsBycategory() {

  this.customerService.getAllProductsByCategory(this.categoryId).subscribe(data => {
    this.originalProducts = data.map((element: any) => {
      element.returnedImg = 'data:image/jpeg;base64,' + element.returnedImg;
      return element;
    });
    this.products = [...this.originalProducts];
    this.products.forEach(product => {
      this.getAvgRating(product.id);
    });
  });
  
}

getAvgRating(productId: number) {
  this.customerService.getAverageRating(productId).subscribe(averageRate => {
    const product = this.products.find(p => p.id === productId);
      if (product) {
        product.averageRating = averageRate;
      }
  });
}


applyFilter(event: Event) {
  const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
  if (!filterValue) {
    this.products = [...this.originalProducts];
    return;
  }
  this.products = this.originalProducts.filter(product => {
    return product.name.toLowerCase().includes(filterValue);
  });
}

  openDialog(productId: number) {
    const dialogRef = this.dialog.open(RatingDialogComponent, {
      height: '33%',
      width: '40%',
      data: { productId }
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.snackBar.open('Thanks for your rating 😀', 'Close', {
        duration: 3000,
      });
      this.getAvgRating(productId);
    }
  });
}

onShowDetails(productId: number){
  this.router.navigate([`customer/products/${this.categoryId}/${productId}`])
}

}
