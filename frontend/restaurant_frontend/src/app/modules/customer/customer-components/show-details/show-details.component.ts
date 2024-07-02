import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from '../../customer-service/customer.service';
import { MatDialog } from '@angular/material/dialog';
import { BookDialogComponent } from '../../../common-components/book-dialog/book-dialog.component';

@Component({
  selector: 'app-show-details',
  templateUrl: './show-details.component.html',
  styleUrl: './show-details.component.css'
})
export class ShowDetailsComponent implements OnInit {

  readonly dialog = inject(MatDialog);
  price: number= 0;

  constructor(private activatedRouter: ActivatedRoute, private customerService: CustomerService){}
  ngOnInit(): void {
    this.getProdutById(this.productId);
  }

  productId: any = this.activatedRouter.snapshot.params['productId'];
  productName: string= '';
  productAdditionalImages: string[] = [];
  ImagesCount : number[] = [];
  productAdditionalDescription: string ='';

  getProdutById(productId: number) {
    this.customerService.getProductById(productId).subscribe(
      data => {
        this.productName = data.name;
        this.productAdditionalImages = data.returnedAdditionalImages.map((image: any) => 'data:image/jpeg;base64,' + image);
        this.ImagesCount = this.productAdditionalImages.map((_, index) => index);
        this.productAdditionalDescription = data.detailedDescription;
        this.price = data.price
      }
    );
  }
  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.dialog.open(BookDialogComponent, {
      width: '400px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: this.price,
      panelClass: "book-dialog-class"
    });
  }

}
