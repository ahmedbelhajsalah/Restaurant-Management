import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CustomerService } from '../../customer/customer-service/customer.service';

@Component({
  selector: 'app-book-dialog',
  templateUrl: './book-dialog.component.html',
  styleUrl: './book-dialog.component.css'
})
export class BookDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: { price: Number }, private customerService: CustomerService
  ) {}

  payPal(){
    this.customerService.postPaypal(Number(this.data)).subscribe(
      (response) => {
        if (response && response.approval_url) {
          window.location.href = response.approval_url;
        } else {
          console.error('Error creating payment:', response);
        }
      },
      (error) => {
        console.error('Error creating payment:', error);
      }
    );
  }
}
