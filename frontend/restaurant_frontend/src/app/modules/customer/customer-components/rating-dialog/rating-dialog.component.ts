import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CustomerService, Rating } from '../../customer-service/customer.service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../auth-services/auth-service/auth.service';
import { StorageService } from '../../../../auth-services/storage-service/storage.service';

@Component({
  selector: 'app-rating-dialog',
  templateUrl: './rating-dialog.component.html',
  styleUrl: './rating-dialog.component.css'
})
export class RatingDialogComponent {

  rating = 0;
  categoryId: any = this.activatedRouter.snapshot.params['categoryId'];


  constructor( private customerService: CustomerService, private activatedRouter: ActivatedRoute,
    @Inject(MAT_DIALOG_DATA) public data: { productId: number }
  ) {}

  onRatingChanged(newRating: number) {
    this.rating = newRating;
  }

  applyRating(){
    const ratingData: Rating = {
      userId: StorageService.getUserId(),
      productId: this.data.productId,
      rating: this.rating
    };
    console.log('ratingData: ', ratingData)
    this.customerService.postRating(ratingData).subscribe()
  }

}
